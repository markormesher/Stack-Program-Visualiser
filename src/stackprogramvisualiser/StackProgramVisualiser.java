package stackprogramvisualiser;

import stackprogramvisualiser.exceptions.CodeFormatException;
import stackprogramvisualiser.exceptions.InvalidLabelException;
import stackprogramvisualiser.exceptions.InvalidVariableException;
import stackprogramvisualiser.exceptions.ProgramExitException;
import stackprogramvisualiser.gui.Gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

public class StackProgramVisualiser {

	public static Gui gui;

	private boolean stepMode = false;

	// these are public static so that the Instruction class can interact with them
	public static ParsedCode parsedCode;
	public int oldProgramCounter = -1;
	public static int programCounter = -1;
	public static Stack<Integer> dataStack = new Stack<>();
	public static HashMap<String, Integer> variableMap = new HashMap<>();

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init(String[] args) {
		gui = new Gui(this);
		gui.build();

		// default RHS view
		gui.setProgramCounter(null, null);
		gui.redrawStackGui();

		if (args.length == 1) {
			StringBuilder sb = new StringBuilder();
			try {
				try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			gui.setEditorContents(sb.toString());
		}
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
		gui.setProgramCounter(null, null);
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

	public void onFinishStepMode() {
		// update gui
		gui.finishStepMode();
	}

	public void onQuitStepMode() {
		// update gui
		gui.stopStepMode();
		gui.setEditorLock(false);
		gui.setProgramCounter(null, null);

		// settings
		stepMode = false;
	}

	public void onNextStep() {
		// run the next step
		boolean done = !runProgram();

		// update display
		gui.setProgramCounter(oldProgramCounter, parsedCode.getInstruction(oldProgramCounter).getLineNumber());
		gui.setStackDataSource(dataStack);
		gui.redrawStackGui();

		// done?
		if (done) {
			onFinishStepMode();
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
			if (e.getIie() != null) {
				gui.outputTerminalError("CodeFormatException on line " + e.getLineNumber() + "; invalid instruction name '" + e.getIie().getInstructionValue() + "'");
			} else {
				gui.outputTerminalError("CodeFormatException on line " + e.getLineNumber());
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
			// store old value
			oldProgramCounter = programCounter;

			// get instruction
			Instruction i = parsedCode.getInstruction(programCounter);

			// execute the instruction
			if (!executeInstruction(i)) break;

			// prevent looping in step mode
			if (stepMode && programCounter < parsedCode.getInstructionCount()) {
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
			gui.outputTerminalError("Missing parameter at line " + parsedCode.getInstruction(programCounter).getLineNumber());
			return false;
		} catch (EmptyStackException ese) {
			gui.outputTerminalError("Empty stack accessed at line " + parsedCode.getInstruction(programCounter).getLineNumber());
			return false;
		} catch (InvalidLabelException ile) {
			gui.outputTerminalError("Invalid label '" + ile.getLabelValue() + "'");
			return false;
		} catch (InvalidVariableException ive) {
			gui.outputTerminalError("Invalid variable '" + ive.getVariableName() + "'");
			return false;
		} catch (ProgramExitException pee) {
			return false;
		}
	}

	/* main method to start things off */

	public static void main(String[] args) {
		new StackProgramVisualiser().init(args);
	}

}
