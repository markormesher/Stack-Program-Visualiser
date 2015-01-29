package stackprogramvisualiser.gui;

import stackprogramvisualiser.StackProgramVisualiser;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

	private StackProgramVisualiser controller;

	public GUI(StackProgramVisualiser controller) {
		this.controller = controller;
	}

	public void build() {
		// set basics
		setTitle("Stack Program Visualiser");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// at least we tried
		}

		// todo actually build a UI in this big blank box

		// finally... display!
		this.setVisible(true);
	}

}
