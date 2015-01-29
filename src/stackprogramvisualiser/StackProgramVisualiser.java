package stackprogramvisualiser;

import stackprogramvisualiser.gui.GUI;

public class StackProgramVisualiser {

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init() {
		GUI gui = new GUI(this);
		gui.build();
	}

}
