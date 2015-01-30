package stackprogramvisualiser.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class StackGui extends JPanel {

	private JPanel mainPanel;
	private Stack<Integer> data = null;

	// empty label
	private static JLabel emptyLabel = new JLabel("Empty Stack", SwingConstants.CENTER);

	static {
		emptyLabel.setFont(emptyLabel.getFont().deriveFont(emptyLabel.getFont().getStyle() & ~Font.BOLD));
	}

	public StackGui() {
		// set layout
		setLayout(new GridLayout(1, 1));

		// set main panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		add(new JScrollPane(mainPanel));

		// set default data
		mainPanel.add(emptyLabel);
	}

	public void setDataSource(Stack<Integer> data) {
		this.data = data;
	}

	public void redraw() {
		// clear it out, then start again
		mainPanel.removeAll();

		// empty?
		if (data == null || data.isEmpty()) {
			mainPanel.add(emptyLabel);
			return;
		}

		// make GBC for each cell
		GridBagConstraints cellGCB = new GridBagConstraints();
		cellGCB.anchor = GridBagConstraints.PAGE_START;
		cellGCB.gridx = 0;
		cellGCB.gridy = 0;
		cellGCB.weightx = 1;
		cellGCB.fill = GridBagConstraints.HORIZONTAL;
		cellGCB.insets = new Insets(2, 2, 2, 2);

		// fill stack, top to bottom
		for (int i = 0; i < data.size(); ++i) {
			cellGCB.gridy = i;
			mainPanel.add(new StackCell(data.get(data.size() - (i + 1))), cellGCB);
		}

		// fill remaining space
		GridBagConstraints glueGCB = new GridBagConstraints();
		glueGCB.anchor = GridBagConstraints.PAGE_START;
		glueGCB.gridx = 0;
		glueGCB.gridy = data.size();
		glueGCB.weightx = 1;
		glueGCB.weighty = 1;
		glueGCB.fill = GridBagConstraints.HORIZONTAL;
		glueGCB.insets = new Insets(2, 2, 2, 2);
		mainPanel.add(Box.createGlue(), glueGCB);
	}

}