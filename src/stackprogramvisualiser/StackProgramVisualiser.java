package stackprogramvisualiser;

import stackprogramvisualiser.exceptions.CodeFormatException;
import stackprogramvisualiser.exceptions.InvalidLabelException;
import stackprogramvisualiser.exceptions.ProgramExitException;
import stackprogramvisualiser.gui.Gui;

import java.util.EmptyStackException;
import java.util.Stack;

public class StackProgramVisualiser {

	public static Gui gui;

	private boolean stepMode = false;
	private boolean nextStep = false;

	// these are public static so that the Instruction class can interact with them
	public static ParsedCode parsedCode;
	public static int programCounter = -1;
	public static Stack<Integer> dataStack = new Stack<Integer>();

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init() {
		gui = new Gui(this);
		gui.build();

		// default RHS view
		gui.setProgramCounter(null);
		gui.redrawStackGui();
	}

	/* button action listeners */

	public void onRunProgram() {
		// update gui
		gui.startRunMode();
		gui.setEditorLock(true);

		// try to parse the program
		if (!parseProgram()) {
			onStop();
			return;
		}

		// run the program
		initProgram();
		runProgram();

		// update display
		gui.setProgramCounter(programCounter);
		gui.setStackDataSource(dataStack);
		gui.redrawStackGui();
		onStop();
	}

	public void onStop() {
		// update gui
		gui.stopRunMode();
		gui.setEditorLock(false);
	}

	public void onStartStepMode() {
		// update gui
		gui.startStepMode();
		gui.setEditorLock(true);

		// try to parse the program
		if (!parseProgram()) {
			onQuitStepMode();
			return;
		}

		// settings
		initProgram();
		stepMode = true;
	}

	public void onQuitStepMode() {
		// update gui
		gui.stopStepMode();
		gui.setEditorLock(false);

		// settings
		stepMode = false;
	}

	public void onNextStep() {
		// run the next step
		nextStep = true;
		boolean done = !runProgram();

		// update display
		gui.setProgramCounter(programCounter);
		gui.setStackDataSource(dataStack);
		gui.redrawStackGui();

		// done?
		if (done) {
			onQuitStepMode();
		}
	}

	/* program execution methods */

	private boolean parseProgram() {
		// get code
		String program = gui.getEditorContents().trim();

		// empty?
		if (program.length() == 0) {
			gui.outputTerminalError("No program to run");
			return false;
		}

		// parse
		Parser parser = new Parser(program);
		try {
			parsedCode = parser.parse();
			return true;
		} catch (CodeFormatException e) {
			gui.outputTerminalError("CodeFormatException on line " + e.getLineNumber());
			if (e.getIie() != null) {
				gui.outputTerminalError("    - Invalid instruction name '" + e.getIie().getInstructionValue() + "'");
			}
			return false;
		}
	}

	private void initProgram() {
		// get started
		programCounter = 0;
		dataStack.removeAllElements();
	}

	private boolean runProgram() {
		// loop
		while (programCounter < parsedCode.getInstructionCount()) {
			// get instruction
			Instruction i = parsedCode.getInstruction(programCounter);

			// execute the instruction
			if (!executeInstruction(i)) break;

			// prevent looping in step mode
			if (stepMode) {
				return true;
			}
		}

		return false;
	}

	private boolean executeInstruction(Instruction i) {
		try {
			i.execute();
			return true;
		} catch (NullPointerException npe) {
			gui.outputTerminalError("Missing parameter at line " + programCounter);
			return false;
		} catch (EmptyStackException ese) {
			gui.outputTerminalError("Empty stack accessed at line " + programCounter);
			return false;
		} catch (InvalidLabelException ile) {
			gui.outputTerminalError("Invalid label '" + ile.getLabelValue() + "'");
			return false;
		} catch (ProgramExitException pee) {
			return false;
		}
	}

	/* main method to start things off */

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

}
