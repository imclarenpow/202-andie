package cosc202.andie.image;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.event.MouseInputAdapter;

import cosc202.andie.ImagePanel;

public class Select implements ImageOperation{
    private BufferedImage input;
    private BufferedImage original;
    private ImagePanel target;
    private boolean isSelectionApplied;

    private Point start; 
    private Point end;

    Select() {
        this.isSelectionApplied = false;
    }

    public void setTarget(ImagePanel target) {
        this.target = target;
        SelectMouseMotionListener selectListener = new SelectMouseMotionListener(this);
        target.addMouseListener(selectListener);
        target.addMouseMotionListener(selectListener);
    }

    public BufferedImage apply(BufferedImage input) {
        this.input = input;
        this.original = deepCopy(input);
        // Create a graphics context from the input image
        Graphics2D g2d = input.createGraphics();

        // Set the drawing color to blue with an alpha value of 0.5
        Color blueSemiTransparent = new Color(0f, 0f, 1f, 0.25f);
        g2d.setColor(blueSemiTransparent);

        // Draw a rectangle using the start and end points
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        g2d.fillRect(x, y, width, height);

        // Set the composite to opaque to restore normal rendering
        g2d.setComposite(AlphaComposite.SrcOver);

        // Dispose of the graphics context to free up resources
        g2d.dispose();

        isSelectionApplied = true;
        return input;
    }

    public void revert() {
        if (isSelectionApplied) {
            // Copy the original image to the input image to restore it
            Graphics2D g2d = input.createGraphics();
            g2d.drawImage(original, 0, 0, null);
            g2d.dispose();
            
            // Reset selection variables
            start = null;
            end = null;
            isSelectionApplied = false;
    
            // Repaint the target panel
            target.repaint();
        }
    }

    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private class SelectMouseMotionListener extends MouseInputAdapter {
        private Select select;

        public SelectMouseMotionListener(Select select) {
            this.select = select;
        }
        /**
         * Identifies the initial coordinates when the mouse is pressed/dragged
         */
        public void mousePressed(MouseEvent e) {
            setStart(e.getPoint());
        }

        /**
         * Identifies the final co-ordinates where the mouse is released
         */
        public void mouseReleased(MouseEvent e) {
            if(isSelectionApplied){
                revert();
            }else{
                setEnd(e.getPoint());
                target.getImage().apply(select);
                target.repaint();
                target.getParent().revalidate();
            }
        }

        /**
         * Responds to mouse drags by drawing a rectangle onto the target image
         */
        public void mouseDragged(MouseEvent e) {
            setEnd(e.getPoint());
        }

        public void setStart(Point p) {
            start = p;
        }
    
        public void setEnd(Point p) {
            end = p;
        }

    }
    
}
