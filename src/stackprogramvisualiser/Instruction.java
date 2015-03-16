package stackprogramvisualiser;

import stackprogramvisualiser.exceptions.InvalidInstructionException;
import stackprogramvisualiser.exceptions.InvalidLabelException;
import stackprogramvisualiser.exceptions.InvalidVariableException;
import stackprogramvisualiser.exceptions.ProgramExitException;

import java.util.EmptyStackException;

public class Instruction {

	private Command command;
	private Integer intArg = null;
	private String strArg = null;
	private int lineNumber;

	public Instruction(String command, int lineNumber) throws InvalidInstructionException {
		setCommand(command);
		this.lineNumber = lineNumber;
	}

	public Instruction(String command, int intArg, int lineNumber) throws InvalidInstructionException {
		setCommand(command);
		this.intArg = intArg;
		this.lineNumber = lineNumber;
	}

	public Instruction(String command, String strArg, int lineNumber) throws InvalidInstructionException {
		setCommand(command);
		this.strArg = strArg;
		this.lineNumber = lineNumber;
	}

	private void setCommand(String command) throws InvalidInstructionException {
		try {
			this.command = Command.valueOf(command.toUpperCase());
		} catch (IllegalArgumentException iae) {
			throw new InvalidInstructionException(command);
		}
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void execute() throws NullPointerException, EmptyStackException, ProgramExitException, InvalidLabelException, InvalidVariableException {
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

			case CALL:
				push(pcGet() + 1);
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

			case RET:
				pcSet(pop());
				return;

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

			case VAR_LOOKUP:
				a1 = variableLookup(strArg);
				if (a1 == null) {
					throw new InvalidVariableException(strArg);
				}
				push(a1);
				break;

			case VAR_SET:
				a1 = peek();
				variableStore(strArg, a1);
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

	private int pcGet() {
		return StackProgramVisualiser.programCounter;
	}

	private void pcAdvance() {
		++StackProgramVisualiser.programCounter;
	}

	private void pcSet(int pc) {
		StackProgramVisualiser.programCounter = pc;
	}

	private void variableStore(String name, Integer value) {
		StackProgramVisualiser.variableMap.put(name, value);
	}

	private Integer variableLookup(String name) {
		return StackProgramVisualiser.variableMap.get(name);
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
		CALL,
		RET,
		EXIT,

		// stack manipulation
		SWAP,
		DUP,
		POP,

		// variable storage
		VAR_LOOKUP,
		VAR_SET,

		// output
		PRINT,
	}

}
