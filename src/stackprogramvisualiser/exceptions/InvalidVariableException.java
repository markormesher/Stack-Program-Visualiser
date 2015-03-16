package stackprogramvisualiser.exceptions;

public class InvalidVariableException extends Exception {

	private String variableName;

	public InvalidVariableException(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableName() {
		return variableName;
	}
}
