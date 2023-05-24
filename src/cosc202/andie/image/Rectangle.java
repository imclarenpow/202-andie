package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.*;

/**
 * <p>
 * A class that applies a rectangle to an image
 * </p>
 * 
 * <p>
 * Uses coordinates to identify the area to draw the rectangle, passed to the constructor from an instance of Select
 * Draws either a filled rectangle or an outline of rectangle, depending on the value of the filled data field
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class Rectangle implements ImageOperation, java.io.Serializable {
    //Data fields
    private Point start;
    private Point end; 
    private Color drawingColor;
    boolean filled = true;

    /**
     * <p>
     * Creates a new Rectangle operation
     * </p>
     * @param start the starting point of the rectangle
     * @param end the ending point of the rectangle
     * @param drawingColor the color of the rectangle
     */
    public Rectangle(Point start, Point end, Color drawingColor){
        this.start = start;
        this.end = end; 
        this.drawingColor = drawingColor;
    }

    /**
     * <p>
     * Apply the rectangle operation to the input image
     * </p>
     * 
     * <p>
     * Calculates the rectangle to draw the rectangle based on the start and end points
     * Uses the min and max functions to ensure that the rectangle is always drawn correctly from the top left corner
     * Draws either a filled rectangle or an outline of rectangle, depending on the value of the filled data field
     * </p>
     * 
     * @param input the image to draw the rectangle on
     * @return the image with the rectangle drawn on it
     */
    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
        g2d.setColor(drawingColor);
        // Calculate the rectangle to crop based on the start and end points
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        //filled Rectangle
        if(getFilled()){
            g2d.fillRect(x, y, width, height);
        }else{
            g2d.drawRect(x, y, width, height);
        }
        return input; 
    }

    /**
     * <p>
     * Sets the filled data field to the value of the parameter, passed by the user from a Yes/No dialog box
     * </p>
     * @param filled the value to set the filled data field to, where 0 is yes and 1 is no
     */
    public void setFilled(int filled){
        if(filled == 0){
            this.filled = true;
            return;
        }
        if(filled == 1){
            this.filled = false;
            return;
        }
    }

    /**
     * <p>
     * Returns the value of the filled data field
     * </p>
     * 
     * @return the value of the filled data field
     */
    public boolean getFilled(){
        return filled; 
    }

}
