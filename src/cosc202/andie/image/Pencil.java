package cosc202.andie.image;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.*;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cosc202.andie.Andie;
import cosc202.andie.colour.ColourSelectorButton;

/**
 * <p>
 * A class that handles a user drawing on an image with a 'pencil'
 * </p>
 * 
 * <p>
 * Draws a series of small lines from the Graphics library onto an image as the mouse is dragged
 * Uses coordinates to identify where to draw the lines
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Niamh Avery
 * @version 1.0
 */
public class Pencil implements ImageOperation, java.io.Serializable {
    // Data fields
    private BufferedImage input;
    private JPanel target;
    private PencilMouseMotionListener pencilListener;
    private int startX;
    private int startY;
    private static boolean listening;
    public static enum WidthEnum {SMALL, MEDIUM, LARGE};
    private static WidthEnum width;

    /**
     * <p>
     * Create a new Pencil operation.
     * </p>
     */
    Pencil() {
        width = WidthEnum.MEDIUM; // Sets the default pencil width to medium
    }

    /**
     * <p>
     * Sets the target image onto which drawing occurs
     * </p>
     * @param target
     */
    public void setTarget(JPanel target) {
        this.target = target;
    }

    /**
     * <p>
     * Sets the width of the pencil
     * </p>
     * 
     * @param width a WidthEnum representing the pencil width to be set
     */
    public static void setWidth(WidthEnum widthValue) {
        width = widthValue;
    }

    public static void setListening(boolean state){
        listening = state;
    }

    /**
     * <p>
     * Apply pencil operation to an image.
     * </p>
     * 
     * <p>
     * Sets the input image and adds mouse listeners to track the mouse's movement
     * The mouse listeners enable mouse dragging to be identified so drawing can occur
     * </p>
     * 
     * @param input The image to be drawn on
     * @return The image with drawings applied
     */
    public BufferedImage apply(BufferedImage input) {
        this.input = input;
        this.pencilListener = new PencilMouseMotionListener();
        target.addMouseListener(pencilListener);
        target.addMouseMotionListener(pencilListener);
        return input; 
    }
    

    /**
     * <p>
     * A private class that responds to mouse dragging by drawing small lines of a given colour onto an image
     * Obtains mouse coordinates to identify where lines should be drawn
     * The lines are drawn using a new graphics object created on the target image
     * Adapted from https://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html
     * </p>
     */
    private class PencilMouseMotionListener extends MouseInputAdapter implements java.io.Serializable {
        /**
         * Responds to mouse drags by drawing onto the target image
         */
        public void mouseDragged(MouseEvent e) { 
            if(listening){
                // Adapted from https://www.oreilly.com/library/view/learning-java/1565927184/ch17s08.html
                int x = e.getX();
                int y = e.getY();
                Graphics2D g2 = input.createGraphics();
                g2.setColor(ColourSelectorButton.getColour());

                // Sets g2's stroke according to the current value of the pencil width
                // With help from https://stackoverflow.com/questions/16995308/can-you-increase-line-thickness-when-using-java-graphics-for-an-applet-i-dont
                switch (width) {
                    case SMALL:
                        g2.setStroke(new BasicStroke(1));
                        break;
                    case MEDIUM:
                        g2.setStroke(new BasicStroke(3));
                        break;
                    case LARGE:
                    default:
                        g2.setStroke(new BasicStroke(5));
                        break;
                }

                g2.drawLine(startX, startY, x, y);
            
                startX = x;
                startY = y;
                target.repaint();
            }
        }

        /**
         * Identifies the initial coordinates when the mouse is pressed/dragged
         */
        public void mousePressed(MouseEvent e) {
            if(listening){
                startX = e.getX();
                startY = e.getY();
            }
        }
     }
    
}
