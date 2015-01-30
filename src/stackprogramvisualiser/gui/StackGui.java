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
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
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

		// fill stack, top to bottom
		for (Integer i : data) {
			mainPanel.add(new JLabel(i.toString()));
		}
	}

}