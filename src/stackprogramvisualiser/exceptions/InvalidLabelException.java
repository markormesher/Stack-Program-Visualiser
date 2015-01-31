package stackprogramvisualiser.exceptions;

public class InvalidLabelException extends Exception {

	private String labelValue;

	public InvalidLabelException(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getLabelValue() {
		return labelValue;
	}
}
