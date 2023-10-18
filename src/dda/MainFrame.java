package dda;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	private JPanel mainPanel;
	private DrawPanel drawPanel;
	private JButton deleteLinesButton;
	private JButton createLineButton;

	public MainFrame() throws HeadlessException {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setContentPane(mainPanel);
		pack();
		deleteLinesButton.addActionListener(e -> drawPanel.deleteLines());
		createLineButton.addActionListener(e -> drawPanel.createLine());
		drawPanel.setSize(800, 600);
	}
}
