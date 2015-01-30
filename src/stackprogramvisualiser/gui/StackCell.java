package stackprogramvisualiser.gui;

import javax.swing.*;
import java.awt.*;

public class StackCell extends JPanel {

	public StackCell(Integer value) {
		setBackground(Color.LIGHT_GRAY);
		add(new Label(value.toString()));
	}
}
