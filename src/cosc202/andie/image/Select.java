package cosc202.andie.image;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Point;

import javax.swing.event.MouseInputAdapter;

import cosc202.andie.ImagePanel;

public class Select extends ImagePanel implements ImageOperation{
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
    }

    public BufferedImage apply(BufferedImage input) {
        this.input = input;
        this.original = input;
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
            setStart(e.getPoint());
        }

        /**
         * Identifies the final co-ordinates where the mouse is released
         */
        public void mouseReleased(MouseEvent e) {
            setEnd(e.getPoint());
            target.getImage().apply(new SelectHighlighter(start, end));
            target.repaint();
            target.getParent().revalidate();
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

    public void revert() {
    }
    
}
