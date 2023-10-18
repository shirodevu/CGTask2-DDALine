package dda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class SelectColorDialog extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JColorChooser colorChooser;

	public SelectColorDialog(List<Color> colors, Runnable stop) {
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonOK);
		setSize(new Dimension(800, 600));


		buttonOK.addActionListener(e -> {
			colors.add(colorChooser.getColor());
			dispose();
		});

		buttonCancel.addActionListener(e -> {
			stop.run();
			dispose();
		});

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop.run();
				dispose();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> {
			stop.run();
			dispose();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
}
