package cosc202.andie.image;

import java.awt.*;
import java.awt.geom.*;

public class ShapesDrawer {
    private Graphics2D g2d;

    public ShapesDrawer(Graphics2D g2d) {
        this.g2d = g2d;
    }
    
    public void setColor(Color color) {
        g2d.setColor(color);
    }

    public void drawFilledRectangle(int x, int y, int width, int height) {
        g2d.fillRect(x, y, width, height);
    }

    public void drawOutlinedRectangle(int x, int y, int width, int height) {
        g2d.drawRect(x, y, width, height);
    }

    public void drawFilledEllipse(int x, int y, int width, int height) {
        g2d.fill(new Ellipse2D.Double(x, y, width, height));
    }

    public void drawOutlinedEllipse(int x, int y, int width, int height) {
        g2d.draw(new Ellipse2D.Double(x, y, width, height));
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        g2d.drawLine(x1, y1, x2, y2);
    }
}