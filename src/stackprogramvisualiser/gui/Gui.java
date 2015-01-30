package stackprogramvisualiser.gui;

import stackprogramvisualiser.StackProgramVisualiser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Gui extends JFrame {

	private StackProgramVisualiser controller;

	// view options
	private Insets paddingInsets = new Insets(6, 6, 6, 6);
	private Font editorTerminalFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);

	// views
	private JLabel programCounter;
	private StackGui stackGui;
	private JTextArea editArea;
	private JTextArea terminalArea;
	private JButton runButton;
	private JButton stopButton;
	private JButton startStepModeButton;
	private JButton nextStepButton;
	private JButton quitStepModeButton;

	public Gui(StackProgramVisualiser controller) {
		this.controller = controller;
	}

	public void build() {
		// set basics
		setTitle("Stack Program Visualiser");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize((int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().width * 0.8), (int) Math.round(Toolkit.getDefaultToolkit().getScreenSize().height * 0.8));
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// at least we tried
		}

		/* left/right areas */
		JPanel leftPanel = new JPanel(new GridBagLayout());
		JPanel rightPanel = new JPanel(new GridBagLayout());

		/* LHS items */

		// editing area
		editArea = new JTextArea();
		editArea.setFont(editorTerminalFont);
		editArea.setLineWrap(true);
		GridBagConstraints editAreaGBC = new GridBagConstraints();
		editAreaGBC.gridx = 0;
		editAreaGBC.gridy = 0;
		editAreaGBC.weightx = 1;
		editAreaGBC.weighty = 3;
		editAreaGBC.gridwidth = 1;
		editAreaGBC.gridheight = 1;
		editAreaGBC.fill = GridBagConstraints.BOTH;
		editAreaGBC.insets = paddingInsets;
		JPanel editAreaPanel = new JPanel(new GridLayout(1, 1));
		editAreaPanel.add(new JScrollPane(editArea));
		editAreaPanel.setBorder(BorderFactory.createTitledBorder("Program Editor"));
		leftPanel.add(editAreaPanel, editAreaGBC);

		// button bar
		runButton = new JButton("Run Program");
		stopButton = new JButton("Stop Program");
		startStepModeButton = new JButton("Start 'Step by Step' Mode");
		nextStepButton = new JButton("Next Step");
		quitStepModeButton = new JButton("Quit 'Step by Step' Mode");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(runButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(startStepModeButton);
		buttonPanel.add(nextStepButton);
		buttonPanel.add(quitStepModeButton);
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
		GridBagConstraints buttonPanelGBC = new GridBagConstraints();
		buttonPanelGBC.gridx = 0;
		buttonPanelGBC.gridy = 1;
		buttonPanelGBC.weightx = 1;
		buttonPanelGBC.gridwidth = 1;
		buttonPanelGBC.gridheight = 1;
		buttonPanelGBC.fill = GridBagConstraints.HORIZONTAL;
		buttonPanelGBC.insets = paddingInsets;
		leftPanel.add(buttonPanel, buttonPanelGBC);

		// by default, hide some buttons
		stopButton.setVisible(false);
		nextStepButton.setVisible(false);
		quitStepModeButton.setVisible(false);

		// pseudo terminal
		terminalArea = new JTextArea();
		terminalArea.setFont(editorTerminalFont);
		terminalArea.setLineWrap(true);
		GridBagConstraints terminalAreaGBC = new GridBagConstraints();
		terminalAreaGBC.gridx = 0;
		terminalAreaGBC.gridy = 2;
		terminalAreaGBC.weightx = 1;
		terminalAreaGBC.weighty = 2;
		terminalAreaGBC.gridwidth = 1;
		terminalAreaGBC.gridheight = 1;
		terminalAreaGBC.fill = GridBagConstraints.BOTH;
		terminalAreaGBC.insets = paddingInsets;
		JPanel terminalAreaPanel = new JPanel(new GridLayout(1, 1));
		terminalAreaPanel.add(new JScrollPane(terminalArea));
		terminalAreaPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));
		leftPanel.add(terminalAreaPanel, terminalAreaGBC);

		/* RHS items */

		// program counter
		programCounter = new JLabel("0");
		programCounter.setBorder(BorderFactory.createTitledBorder("Program Counter"));
		programCounter.setHorizontalAlignment(SwingConstants.CENTER);
		programCounter.setFont(programCounter.getFont().deriveFont(48f).deriveFont(programCounter.getFont().getStyle() & ~Font.BOLD));
		GridBagConstraints programCounterGBC = new GridBagConstraints();
		programCounterGBC.gridx = 0;
		programCounterGBC.gridy = 0;
		programCounterGBC.weightx = 1;
		programCounterGBC.gridwidth = 1;
		programCounterGBC.gridheight = 1;
		programCounterGBC.fill = GridBagConstraints.HORIZONTAL;
		programCounterGBC.insets = paddingInsets;
		rightPanel.add(programCounter, programCounterGBC);

		// stack visual
		stackGui = new StackGui();
		stackGui.setBorder(BorderFactory.createTitledBorder("Stack"));
		GridBagConstraints stackGuiGBC = new GridBagConstraints();
		stackGuiGBC.gridx = 0;
		stackGuiGBC.gridy = 1;
		stackGuiGBC.weightx = 1;
		stackGuiGBC.weighty = 1;
		stackGuiGBC.gridwidth = 1;
		stackGuiGBC.gridheight = 1;
		stackGuiGBC.fill = GridBagConstraints.BOTH;
		stackGuiGBC.insets = paddingInsets;
		rightPanel.add(stackGui, stackGuiGBC);

		/* set up left/right panels */
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation((int) Math.round(getWidth() * 0.8));
		this.setContentPane(splitPane);

		// finally... display!
		setVisible(true);

		// set up button clicks
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				controller.onRunProgram();
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				controller.onStop();
			}
		});
		startStepModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				controller.onStartStepMode();
			}
		});
		nextStepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				controller.onNextStep();
			}
		});
		quitStepModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				controller.onQuitStepMode();
			}
		});
	}

	public void setProgramCounter(Integer pc) {
		programCounter.setText(pc == null ? "--" : pc.toString());
	}

	public void setStackDataSource(Stack<Integer> data) {
		stackGui.setDataSource(data);
	}

	public void redrawStackGui() {
		stackGui.redraw();
	}

	public void startStepMode() {
		runButton.setVisible(false);
		stopButton.setVisible(false);
		startStepModeButton.setVisible(false);
		nextStepButton.setVisible(true);
		quitStepModeButton.setVisible(true);
	}

	public void stopStepMode() {
		runButton.setVisible(true);
		stopButton.setVisible(false);
		startStepModeButton.setVisible(true);
		nextStepButton.setVisible(false);
		quitStepModeButton.setVisible(false);
	}

	public void startRunMode() {
		runButton.setVisible(false);
		stopButton.setVisible(true);
		startStepModeButton.setVisible(false);
		nextStepButton.setVisible(false);
		quitStepModeButton.setVisible(false);
	}

	public void stopRunMode() {
		runButton.setVisible(true);
		stopButton.setVisible(false);
		startStepModeButton.setVisible(true);
		nextStepButton.setVisible(false);
		quitStepModeButton.setVisible(false);
	}

	public void setEditorLock(boolean editorLock) {
		editArea.setEditable(!editorLock);
	}

	public String getEditorContents() {
		return editArea.getText();
	}

	public void outputTerminalMessage(String msg) {
		terminalArea.setText(terminalArea.getText() + "\n" + msg);
	}

	public void outputTerminalError(String msg) {
		terminalArea.setText(terminalArea.getText() + "\n" + msg);
	}

}
