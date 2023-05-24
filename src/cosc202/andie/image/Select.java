package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import cosc202.andie.ImagePanel;

/**
 * <p>
 * A class that applies a transperant rectangle to an area selected by the user
 * </p>
 * 
 * <p>
 * While heavily based on other implmentations of imageoperation, it does not extend the class, as it should not be serialised or added to the ops stack
 * Instead, its main role is providing the coordinates of the selected area to the Crop and Shapes features
 * Selections are applied and reverted using BufferedImage datafields, so it would not be sutaible for serialisation anyway
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class Select {
    //Data fields
    private BufferedImage input;
    private BufferedImage original;
    private ImagePanel target;
    private boolean SelectionApplied;

    private Point start;
    private Point end;

    /**
     * <p>
     * Creates a new Select operation, with SelectionApplied set to false by default
     * </p>
     */
    Select() {
        this.SelectionApplied = false;
    }

    /**
     * <p>
     * Sets the target panel for Select operations, to enable interoperability
     * Target is utilised by the SelectButton class for the Crop and Shape features
     * </p>
     * 
     * @param target the target panel for Select operations
     */
    public void setTarget(ImagePanel target) {
        this.target = target;
    }

    /**
     * <p>
     * Returns the current target panel for Select operations
     * </p>
     * 
     * @return the current target panel for Select operations
     */
    public ImagePanel getTarget(){
        return target;
    }

    /**
     * <p>
     * Sets the original image (@see Revert) to the input image, in the case where the user has cropped the image
     * </p>
     * @param original the original image
     */
    //only used in case of cropped image
    public void setOriginal(BufferedImage original){
        this.original = original;
    }

    /**
     * <p>
     * Returns whether a selection has been applied to the image
     * </p>
     * 
     * @return whether a selection has been applied to the image
     */
    public boolean isSelectionApplied(){
        return SelectionApplied;
    }

    /**
     * <p>
     * Applies a semi transperant blue rectangle to the area selected by the user (@see SelectButton)
     * </p>
     * 
     * <p>
     * Uses data fields to store the input and creates a copy of it, in the case where the user wants to revert their selection (@see Revert)
     * Creates the blue rectangle using the start and end points, with min and max methods to ensure it is of the correct orientation
     * </p>
     * 
     * @author Nic Scott with assistance from ChatGPT on transperancy
     * @param input the input image to apply the selection to
     * @return the input image with the selection applied
     */
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

    /**
     * <p>
     * Reverts the image to the original image, before the selection was applied
     * </p>
     * 
     * <p>
     * Uses the original image data field to restore the image to its original state
     * Then resets the co-ordinates, selection applied boolean, and datafields storing image states
     * </p>
     */
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

    /**
     * <p>
     * Returns the start point of the selection
     * </p>
     * @return the start point of the selection
     */
    public Point getStartPoint(){
        return start;
    }

    /**
     * <p>
     * Returns the end point of the selection
     * </p>
     * 
     * @return the end point of the selection
     */
    public Point getEndPoint(){
        return end;
    }

    /**
     * <p>
     * Sets the start point of the selection
     * </p>
     * 
     * @param p the start point of the selection
     */
    public void setStart(Point p) {
        start = p;
    }

    /**
     * <p>
     * Sets the end point of the selection
     * </p>
     * 
     * @param p the end point of the selection
     */
    public void setEnd(Point p) {
        end = p;
    }

    /**
     * <p>
     * Creates a deep copy of a BufferedImage, copied from the @see EditableImage Implemntation
     * Used by @see Revert
     * </p>
     * 
     * @param bi the BufferedImage to copy
     * @return a deep copy of the BufferedImage
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
    

