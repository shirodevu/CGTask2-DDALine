package dda;

import java.awt.*;

public class LineSegment {
    public static final int STROKE_RADIUS = 4;
    private final int startX;
    private final int endX;
    private final int startY;
    private final int endY;
    private final Color startColor;
	private final Color endColor;


    public LineSegment(int startX, int endX, int startY, int endY, Color startColor, Color endColor) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public void draw(Graphics2D g) {
        float x = (float) startX;
        float y = (float) startY;
        int dx = endX - startX, dy = endY - startY;
        int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx - 1) : Math.abs(dy - 1);
        float unitXDifference = (float) dx / steps;
        float unitYDifference = (float) dy / steps;


        g.fillRect(Math.round(x), Math.round(y), STROKE_RADIUS, STROKE_RADIUS);

        for (int i = 0; i < steps - 1; i++) {
            x += unitXDifference;
            y += unitYDifference;
            float t = (i + 1) / (float) steps;
            Color currentColor = interpolateColor(startColor, endColor, t);
            g.setColor(currentColor);
            g.fillRect(Math.round(x), Math.round(y), STROKE_RADIUS, STROKE_RADIUS);
        }
        g.fillRect(Math.round(x), Math.round(y), STROKE_RADIUS, STROKE_RADIUS);
    }

    private Color interpolateColor(Color start, Color end, float offsetFromStart) {
        int red = (int) (start.getRed() + offsetFromStart * (end.getRed() - start.getRed()));
        int green = (int) (start.getGreen() + offsetFromStart * (end.getGreen() - start.getGreen()));
        int blue = (int) (start.getBlue() + offsetFromStart * (end.getBlue() - start.getBlue()));
        return new Color(red, green, blue);
    }
}