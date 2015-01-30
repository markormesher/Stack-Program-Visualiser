package stackprogramvisualiser;

import stackprogramvisualiser.exceptions.InvalidInstructionException;

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
			this.command = Command.valueOf(command);
		} catch (IllegalArgumentException iae) {
			throw new InvalidInstructionException(command);
		}
	}

	public void execute() throws NullPointerException, EmptyStackException {
		// switch on the command we're executing
		switch (command) {
			case INT:
				if (intArg == null) throw new NullPointerException();
				push(intArg);
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

		// output
		PRINT
	}

}
