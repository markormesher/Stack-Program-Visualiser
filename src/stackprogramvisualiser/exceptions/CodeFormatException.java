package stackprogramvisualiser.exceptions;

public class CodeFormatException extends Exception {

	private int lineNumber = -1;
	private InvalidInstructionException iie = null;

	public CodeFormatException(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public CodeFormatException(int lineNumber, InvalidInstructionException iie) {
		this.lineNumber = lineNumber;
		this.iie = iie;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public InvalidInstructionException getIie() {
		return iie;
	}
}
