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
		gui.setProgramCounter(null);
		gui.redrawStackGui();
	}

	public void onRunProgram() {
		// update gui
		gui.startRunMode();
	}

	public void onStop() {
		// update gui
		gui.stopRunMode();
	}

	public void onStartStepMode() {
		// update gui
		gui.startStepMode();
	}

	public void onQuitStepMode() {
		// update gui
		gui.stopStepMode();
	}

	public void onNextStep() {

	}

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

}
