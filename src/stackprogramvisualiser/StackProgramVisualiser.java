package stackprogramvisualiser;

import stackprogramvisualiser.gui.Gui;

public class StackProgramVisualiser {

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init() {
		Gui gui = new Gui(this);
		gui.build();
	}

}
