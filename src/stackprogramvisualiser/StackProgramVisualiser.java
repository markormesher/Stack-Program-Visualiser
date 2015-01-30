package stackprogramvisualiser;

import stackprogramvisualiser.gui.Gui;

public class StackProgramVisualiser {

	private Gui gui;

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init() {
		gui = new Gui(this);
		gui.build();

		// default RHS view
		gui.setProgramCounter(0);
		gui.redrawStackGui();
	}

	public void onRunProgram() {

	}

	public void onStartStepMode() {
		// update gui
		gui.setStepMode(true);
	}

	public void onQuitStepMode() {
		// update gui
		gui.setStepMode(false);
	}

	public void onNextStep() {

	}

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

}
