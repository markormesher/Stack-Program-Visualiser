package stackprogramvisualiser.exceptions;

public class InvalidInstructionException extends Exception {

	private String instructionValue;

	public InvalidInstructionException(String instructionValue) {
		this.instructionValue = instructionValue;
	}

	public String getInstructionValue() {
		return instructionValue;
	}
}
