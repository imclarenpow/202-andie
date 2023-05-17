package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import cosc202.andie.ImagePanel;

public class Select implements ImageOperation{
    private BufferedImage input;
    private BufferedImage original;
    private ImagePanel target;
    private boolean SelectionApplied;

    //private SelectMouseMotionListener selectListener;

    private Point start;
    private Point end;

    //Still need to handle case where user doesnt deselect Select Button

    Select() {
        this.SelectionApplied = false;
    }

    public void setTarget(ImagePanel target) {
        this.target = target;
    }

    public ImagePanel getTarget(){
        return target;
    }

    //only used in case of cropped image
    public void setOriginal(BufferedImage original){
        this.original = original;
    }

    public boolean isSelectionApplied(){
        return SelectionApplied;
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

        SelectionApplied = true;
        return input;
    }

    public void revert() {
        if (SelectionApplied) {
            // Copy the original image to the input image to restore it
            Graphics2D g2d = input.createGraphics();
            g2d.drawImage(original, 0, 0, null);
            g2d.dispose();
            
            // Reset selection variables
            start = null;
            end = null;
            original = null; 
            SelectionApplied = false;
    
            // Repaint the target panel
            target.repaint();
        }
    }

    public Point getStartPoint(){
        return start;
    }

    public Point getEndPoint(){
        return end;
    }
    public void setStart(Point p) {
        start = p;
    }

    public void setEnd(Point p) {
        end = p;
    }

    /**
     * Creates a deep copy of a BufferedImage, copied from EditableImage implementation
     * Used by revert method
     * @param bi
     * @return
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();

        int width = bi.getWidth();
        int height = bi.getHeight();

        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        bi.copyData(raster);

        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    

}
    

