package cosc202.andie;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 * <p>
 * ImageOperation to convert an image from colour to greyscale.
 * </p>
 * 
 * <p>
 * The images produced by this operation are still technically colour images,
 * in that they have red, green, and blue values, but each pixel has equal
 * values for red, green, and blue giving a shade of grey.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Pencil implements ImageOperation, java.io.Serializable {
    private BufferedImage input;
    private JPanel target;
    private int startX;
    private int startY;

    /**
     * <p>
     * Create a new CovertToGrey operation.
     * </p>
     */
    Pencil() {

    }

    public void setTarget(JPanel target) {
        this.target = target;
    }

    /**
     * <p>
     * Apply greyscale conversion to an image.
     * </p>
     * 
     * <p>
     * The conversion from red, green, and blue values to greyscale uses a 
     * weighted average that reflects the human visual system's sensitivity 
     * to different wavelengths -- we are most sensitive to green light and 
     * least to blue.
     * </p>
     * 
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {
        this.input = input;
        PencilMouseMotionListener pencilListener = new PencilMouseMotionListener();
        target.addMouseListener(pencilListener);
        target.addMouseMotionListener(pencilListener);

        return input;

        
    }

    // Adapted from https://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html
    private class PencilMouseMotionListener extends MouseInputAdapter {
        public void mouseDragged(MouseEvent e) { 

            // Adapted from https://www.oreilly.com/library/view/learning-java/1565927184/ch17s08.html
            int x = e.getX();
            int y = e.getY();
            Graphics2D g2 = input.createGraphics();
            g2.setColor(ColourSelectorButton.getColour());
            g2.drawLine(startX, startY, x, y);
           
            startX = x;
            startY = y;
            target.repaint();
        }

        public void mousePressed(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
        }
     }
    
}
