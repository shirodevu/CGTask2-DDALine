package dda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class DrawPanel extends JPanel {
	private final List<LineSegment> segments;
	private int clickCount;
	private Integer clickX1, clickY1, clickX2, clickY2;


	public DrawPanel() {
		segments = new ArrayList<>();
		//  b = new DDA(50, 200, 50, 200, Color.RED, Color.BLACK);
		setPreferredSize(new Dimension(800, 600));
		clickCount = 0;

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int clicksEffective = clickCount % 2;
				if (clicksEffective == 0) {
					unsetAllPointsClicked();
					clickX1 = e.getX();
					clickY1 = e.getY();
					clickX2 = null;
					clickY2 = null;
					repaint();
					clickCount++;
					return;
				}
				clickX2 = e.getX();
				clickY2 = e.getY();
				repaint();
				//createLine();
				//unsetAllPointsClicked();
				//repaint();
				clickCount++;
			}
		});
	}

	private void unsetAllPointsClicked() {
		clickX1 = null;
		clickY1 = null;
		clickX2 = null;
		clickY2 = null;
	}

	public void paint(Graphics gr) {
		super.paint(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (LineSegment segment : segments) {
			segment.draw(g);
		}

		if (clickX1 != null && clickY1 != null) {
			drawPointWithText(g, clickX1, clickY1, "Начальная точка");
		}

		if (clickX2 != null && clickY2 != null) {
			drawPointWithText(g, clickX2, clickY2, "Конечная точка");
		}

	}

	private void drawPointWithText(Graphics2D g, int x, int y, String text){
		Color prevColor = g.getColor();
		g.setColor(Color.BLACK);
		int pointR = 6;
		g.fillOval(x - pointR/2, y - pointR/2, pointR, pointR);
		g.drawString(text, x - 10, y - 10);
		g.setColor(prevColor);
	}

	public void deleteLines() {
		segments.clear();
		unsetAllPointsClicked();
		repaint();
	}

	public void createLine() {
		if (clickX1 == null || clickY1 == null || clickX2 == null || clickY2 == null) {
			JOptionPane.showMessageDialog(this, "Пожалуйста, выберите две точки");
			return;
		}
		List<Color> colorsList = new ArrayList<>();

		BooleanContainer continueAsking = new BooleanContainer(true);
		while (colorsList.size() < 2 && continueAsking.get()) {
			new SelectColorDialog(colorsList, () -> continueAsking.set(false)).setVisible(true);
		}

		if (!continueAsking.get()) return;

		segments.add(new LineSegment(
				clickX1, clickX2,
				clickY1, clickY2,
				colorsList.get(0),
				colorsList.get(1)));

		repaint();
		unsetAllPointsClicked();
	}

	private static class BooleanContainer {
		private boolean bool;

		public BooleanContainer(boolean initialValue) {
			this.bool = initialValue;
		}

		public boolean get() {
			return bool;
		}

		public void set(boolean bool) {
			this.bool = bool;
		}
	}
}