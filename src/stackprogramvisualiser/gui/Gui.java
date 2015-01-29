package stackprogramvisualiser.gui;

import stackprogramvisualiser.StackProgramVisualiser;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

	private StackProgramVisualiser controller;

	private Insets paddingInsets = new Insets(6, 6, 6, 6);

	private JLabel programCounter;
	private StackGui stackGui;

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

		// LHS area
		JPanel leftPanel = new JPanel(new GridBagLayout());
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

		// RHS area
		JPanel rightPanel = new JPanel(new GridBagLayout());
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

		/* RHS items */

		// program counter
		programCounter = new JLabel("0");
		programCounter.setBorder(BorderFactory.createTitledBorder("Program Counter"));
		programCounter.setHorizontalAlignment(SwingConstants.CENTER);
		programCounter.setFont(programCounter.getFont().deriveFont(48f).deriveFont(programCounter.getFont().getStyle() & ~Font.BOLD));
		GridBagConstraints programCounterGBC = new GridBagConstraints();
		programCounterGBC.anchor = GridBagConstraints.PAGE_START;
		programCounterGBC.gridx = 0;
		programCounterGBC.gridy = 0;
		programCounterGBC.weightx = 1;
		programCounterGBC.gridwidth = 1;
		programCounterGBC.gridheight = 1;
		programCounterGBC.fill = GridBagConstraints.HORIZONTAL;
		programCounterGBC.insets = paddingInsets;
		rightPanel.add(programCounter, programCounterGBC);

		// stack visual
		stackGui = new StackGui(rightPanel);
		stackGui.setBorder(BorderFactory.createTitledBorder("Stack"));
		GridBagConstraints stackGuiGBC = new GridBagConstraints();
		stackGuiGBC.anchor = GridBagConstraints.PAGE_END;
		stackGuiGBC.gridx = 0;
		stackGuiGBC.gridy = 1;
		stackGuiGBC.weightx = 1;
		stackGuiGBC.weighty = 1;
		stackGuiGBC.gridwidth = 1;
		stackGuiGBC.gridheight = 1;
		stackGuiGBC.fill = GridBagConstraints.BOTH;
		stackGuiGBC.insets = paddingInsets;
		rightPanel.add(stackGui, stackGuiGBC);

		// finally... display!
		this.setVisible(true);
	}

}
