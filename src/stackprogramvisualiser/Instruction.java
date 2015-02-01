package stackprogramvisualiser;

import stackprogramvisualiser.exceptions.InvalidInstructionException;
import stackprogramvisualiser.exceptions.InvalidLabelException;
import stackprogramvisualiser.exceptions.ProgramExitException;

import java.util.EmptyStackException;

public class Instruction {

	private Command command;
	private Integer intArg = null;
	private String strArg = null;

	public Instruction(String command) throws InvalidInstructionException {
		setCommand(command);
	}

	public Instruction(String command, int intArg) throws InvalidInstructionException {
		setCommand(command);
		this.intArg = intArg;
	}

	public Instruction(String command, String strArg) throws InvalidInstructionException {
		setCommand(command);
		this.strArg = strArg;
	}

	private void setCommand(String command) throws InvalidInstructionException {
		try {
			this.command = Command.valueOf(command.toUpperCase());
		} catch (IllegalArgumentException iae) {
			throw new InvalidInstructionException(command);
		}
	}

	public void execute() throws NullPointerException, EmptyStackException, ProgramExitException, InvalidLabelException {
		// temporary holders
		Integer a1, a2;

		// switch on the command we're executing
		switch (command) {
			case INT:
				if (intArg == null) throw new NullPointerException();
				push(intArg);
				break;

			case ADD:
				a1 = pop();
				a2 = pop();
				push(a1 + a2);
				break;

			case SUB:
				a1 = pop();
				a2 = pop();
				push(a2 - a1);
				break;

			case JGE:
				a1 = peek();
				if (a1 >= 0) {
					// which arg to move to?
					if (intArg != null) {
						pcSet(intArg);
					} else {
						int newPC = StackProgramVisualiser.parsedCode.getPositionForLabel(strArg);
						if (newPC < 0) {
							throw new InvalidLabelException(strArg);
						}
						pcSet(newPC);
					}
					return;
				}
				break;

			case JEQ:
				a1 = peek();
				if (a1 == 0) {
					// which arg to move to?
					if (intArg != null) {
						pcSet(intArg);
					} else {
						int newPC = StackProgramVisualiser.parsedCode.getPositionForLabel(strArg);
						if (newPC < 0) {
							throw new InvalidLabelException(strArg);
						}
						pcSet(newPC);
					}
					return;
				}
				break;

			case EXIT:
				throw new ProgramExitException();

			case SWAP:
				a1 = pop();
				a2 = pop();
				push(a1);
				push(a2);
				break;

			case DUP:
				push(peek());
				break;

			case POP:
				pop();
				break;

			case PRINT:
				StackProgramVisualiser.gui.outputTerminalMessage(peek().toString());
				break;
		}

		// move on!
		pcAdvance();
	}

	/* shortcut methods */

	private void push(Integer i) {
		StackProgramVisualiser.dataStack.push(i);
	}

	private Integer pop() throws EmptyStackException {
		return StackProgramVisualiser.dataStack.pop();
	}

	private Integer peek() throws EmptyStackException {
		return StackProgramVisualiser.dataStack.peek();
	}

	private void pcAdvance() {
		StackProgramVisualiser.programCounter++;
	}

	private void pcSet(int pc) {
		StackProgramVisualiser.programCounter = pc;
	}

	public enum Command {
		// basic inputs
		INT,

		// arithmetic
		ADD,
		SUB,

		// control statements
		JGE,
		JEQ,
		EXIT,

		// stack manipulation
		SWAP,
		DUP,
		POP,

		// output
		PRINT,

		// not implemented yet
		CALL,
		RET,
	}

}
