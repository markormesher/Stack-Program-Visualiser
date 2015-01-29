package stackprogramvisualiser.gui;

import stackprogramvisualiser.StackProgramVisualiser;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

	private StackProgramVisualiser controller;

	private JLabel programCounter;

	public Gui(StackProgramVisualiser controller) {
		this.controller = controller;
	}

	public void build() {
		// set basics
		setTitle("Stack Program Visualiser");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().width * 0.8), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().height * 0.8));
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// at least we tried
		}

		// main layout
		JPanel mainPanel = new JPanel(new GridBagLayout());
		setContentPane(mainPanel);

		// LHS working area
		JPanel leftPanel = new JPanel(new GridBagLayout());
		leftPanel.setBackground(Color.BLUE);
		leftPanel.add(new JLabel("test"));

		GridBagConstraints leftPanelGBC = new GridBagConstraints();
		leftPanelGBC.anchor = GridBagConstraints.LINE_START;
		leftPanelGBC.gridx = 0;
		leftPanelGBC.gridy = 0;
		leftPanelGBC.weightx = 6;
		leftPanelGBC.weighty = 1;
		leftPanelGBC.gridwidth = 1;
		leftPanelGBC.gridheight = 1;
		leftPanelGBC.fill = GridBagConstraints.BOTH;

		mainPanel.add(leftPanel, leftPanelGBC);

		// todo: spacer

		// RHS list of boxes
		JPanel rightPanel = new JPanel(new GridBagLayout());
		rightPanel.setBackground(Color.RED);
		rightPanel.add(new JLabel("test"));

		GridBagConstraints rightPanelGBC = new GridBagConstraints();
		rightPanelGBC.anchor = GridBagConstraints.LINE_END;
		rightPanelGBC.gridx = 1;
		rightPanelGBC.gridy = 0;
		rightPanelGBC.weightx = 1;
		rightPanelGBC.weighty = 1;
		rightPanelGBC.gridwidth = 1;
		rightPanelGBC.gridheight = 1;
		rightPanelGBC.fill = GridBagConstraints.BOTH;

		mainPanel.add(rightPanel, rightPanelGBC);

		// LHS set of boxes
		//programCounter = new JLabel("0");
		//programCounter.setBorder(BorderFactory.createTitledBorder("Program Counter"));
		//rightPanel.add(programCounter, BorderLayout.NORTH);

		// finally... display!
		this.setVisible(true);
	}

}
