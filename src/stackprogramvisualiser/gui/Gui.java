package stackprogramvisualiser.gui;

import stackprogramvisualiser.StackProgramVisualiser;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Gui extends JFrame {

	private StackProgramVisualiser controller;

	// view options
	private Insets paddingInsets = new Insets(6, 6, 6, 6);
	private Font editorTerminalFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	private static StyleContext styleContext = new StyleContext();
	private static Style terminalNormalStyle = styleContext.addStyle("normal", null);
	private static Style terminalErrorStyle = styleContext.addStyle("error", null);

	static {
		StyleConstants.setForeground(terminalNormalStyle, Color.WHITE);
		StyleConstants.setForeground(terminalErrorStyle, Color.ORANGE);
		StyleConstants.setBold(terminalErrorStyle, true);
	}

	// views
	private JLabel programCounter;
	private StackGui stackGui;
	private JTextArea editArea;
	private StyledDocument terminalDoc;
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
		setSize(1000, 700);
		setResizable(true);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// at least we tried
		}

		/* left/right areas */
		JSplitPane leftPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		leftPanel.setResizeWeight(0.8);
		JPanel rightPanel = new JPanel(new GridBagLayout());

		/* LHS items */

		// editing area
		editArea = new JTextArea();
		editArea.setFont(editorTerminalFont);
		editArea.setLineWrap(true);
		JPanel editAreaPanel = new JPanel(new GridLayout(1, 1));
		editAreaPanel.add(new JScrollPane(editArea));
		editAreaPanel.setBorder(BorderFactory.createTitledBorder("Program Editor"));
		leftPanel.add(editAreaPanel);

		// lower left panel
		JPanel lowerLeftPanel = new JPanel(new GridBagLayout());

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
		buttonPanelGBC.gridy = 0;
		buttonPanelGBC.weightx = 1;
		buttonPanelGBC.gridwidth = 1;
		buttonPanelGBC.gridheight = 1;
		buttonPanelGBC.fill = GridBagConstraints.HORIZONTAL;
		buttonPanelGBC.insets = paddingInsets;
		lowerLeftPanel.add(buttonPanel, buttonPanelGBC);

		// by default, hide some buttons
		stopButton.setVisible(false);
		nextStepButton.setVisible(false);
		quitStepModeButton.setVisible(false);

		// pseudo terminal
		terminalDoc = new DefaultStyledDocument();
		JTextPane terminalArea = new JTextPane(terminalDoc);
		terminalArea.setFont(editorTerminalFont);
		terminalArea.setBackground(Color.BLACK);
		terminalArea.setForeground(Color.WHITE);
		terminalArea.setEditable(false);
		GridBagConstraints terminalAreaGBC = new GridBagConstraints();
		terminalAreaGBC.gridx = 0;
		terminalAreaGBC.gridy = 1;
		terminalAreaGBC.weightx = 1;
		terminalAreaGBC.weighty = 1;
		terminalAreaGBC.gridwidth = 1;
		terminalAreaGBC.gridheight = 1;
		terminalAreaGBC.fill = GridBagConstraints.BOTH;
		terminalAreaGBC.insets = paddingInsets;
		JPanel terminalAreaPanel = new JPanel(new GridLayout(1, 1));
		terminalAreaPanel.add(new JScrollPane(terminalArea));
		terminalAreaPanel.setBorder(BorderFactory.createTitledBorder("Terminal"));
		lowerLeftPanel.add(terminalAreaPanel, terminalAreaGBC);

		// add lower left panel
		leftPanel.add(lowerLeftPanel);

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
		splitPane.setResizeWeight(0.65);
		this.setContentPane(splitPane);

		// finally... display!
		setLocationRelativeTo(null);
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

	public void setProgramCounter(Integer pc, Integer line) {
		// set text
		programCounter.setText(pc == null ? "--" : pc.toString());

		// clear highlight
		editArea.getHighlighter().removeAllHighlights();

		// highlight the current line
		if (line != null) {
			Highlighter.HighlightPainter highlight = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
			try {
				editArea.getHighlighter().addHighlight(editArea.getLineStartOffset(line), editArea.getLineEndOffset(line), highlight);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
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

	public void finishStepMode() {
		nextStepButton.setEnabled(false);
	}

	public void stopStepMode() {
		runButton.setVisible(true);
		stopButton.setVisible(false);
		startStepModeButton.setVisible(true);
		nextStepButton.setVisible(false);
		nextStepButton.setEnabled(true);
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

	public void setEditorContents(String p) {
		editArea.setText(p);
	}

	public void outputTerminalMessage(String msg) {
		try {
			terminalDoc.insertString(terminalDoc.getLength(), (terminalDoc.getLength() == 0 ? "" : "\n") + msg, terminalNormalStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void outputTerminalError(String msg) {
		try {
			terminalDoc.insertString(terminalDoc.getLength(), (terminalDoc.getLength() == 0 ? "" : "\n") + msg, terminalErrorStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
