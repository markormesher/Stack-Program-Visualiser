package stackprogramvisualiser;

import stackprogramvisualiser.gui.Gui;

import java.util.Stack;

public class StackProgramVisualiser {

	public static void main(String[] args) {
		new StackProgramVisualiser().init();
	}

	public StackProgramVisualiser() {
	}

	// let's get the party started
	public void init() {
		// dummy data
		Stack<Integer> dd = new Stack<Integer>();
		dd.push(12);
		dd.push(5);
		dd.push(19);
		dd.push(0);

		Gui gui = new Gui(this);
		gui.build();
		gui.setProgramCounter(13);
		gui.setStackDataSource(dd);
		gui.redrawStackGui();
	}

}
