package cosc202.andie.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class Select extends JPanel implements ImageOperation, java.io.Serializable {
    private BufferedImage input;
    private JPanel target;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    Select() {
    }

    public void setTarget(JPanel target) {
        this.target = target;
    }

    public BufferedImage apply(BufferedImage input) {
        this.input = input;
        SelectMouseMotionListener selectListener = new SelectMouseMotionListener();
        target.addMouseListener(selectListener);
        target.addMouseMotionListener(selectListener);
        return input;
    }

    private class SelectMouseMotionListener extends MouseInputAdapter {
        /**
         * Identifies the initial coordinates when the mouse is pressed/dragged
         */
        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            target.repaint();
        }

        /**
         * Identifies the final co-ordinates where the mouse is released
         */
        public void mouseReleased(MouseEvent e) {
            endX = e.getX();
            endY = e.getY();
            target.repaint();
        }

        /**
         * Responds to mouse drags by drawing a rectangle onto the target image
         */
        public void mouseDragged(MouseEvent e) {
            // Adapted from https://www.oreilly.com/library/view/learning-java/1565927184/ch17s08.html
            endX = e.getX();
            endY = e.getY();
            target.repaint();
        }

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startX < endX) {
            int temp = startX;
            startX = endX;
            endX = temp;
        }
        if (startY < endY) {
            int temp = startY;
            startY = endY;
            endY = temp;
        }
        g.setColor(new Color(0, 0, 255, 150)); // semi-transparent blue
        g.fillRect(endX, endY, startX - endX, startY - endY);
        g.setColor(Color.BLUE);
        g.drawRect(endX, endY, startX - endX, startY - endY);
    }
}
