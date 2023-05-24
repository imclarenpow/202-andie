package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.*;
import java.awt.geom.*;

/**
 * <p>
 * A class that applies an ellipse to an image
 * </p>
 * 
 * <p>
 * Uses coordinates to identify the area to draw the ellipse, passed to the constructor from an instance of Select
 * Draws either a filled ellipse or an outline of ellipse, depending on the value of the filled data field
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class Ellipse implements ImageOperation, java.io.Serializable {
    //Data fields
    private Color drawingColour;
    private Point start;
    private Point end; 
    boolean filled = true;

    /**
     * <p>
     * Creates a new Ellipse operation
     * </p>
     * @param start the starting point of the ellipse
     * @param end the ending point of the ellipse
     * @param drawingColour the colour of the ellipse
     */
    public Ellipse(Point start, Point end, Color drawingColour){
        this.drawingColour = drawingColour;
        this.start = start;
        this.end = end; 
    }

    /**
     * <p>
     * Apply the ellipse operation to the input image
     * </p>
     * 
     * <p>
     * Calculates the rectangle to draw the ellipse based on the start and end points
     * Uses the min and max functions to ensure that the ellipse is always drawn correctly from the top left corner
     * Draws either a filled ellipse or an outline of ellipse, depending on the value of the filled data field
     * </p>
     * 
     * @param input the image to draw the ellipse on
     * @return the image with the ellipse drawn on it
     */
    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
        g2d.setColor(drawingColour);
        // Calculate the rectangle to draw based on the start and end points
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        //filled Rectangle
        if(getFilled()){
            g2d.fill(new Ellipse2D.Double(x, y, width, height));
        }else{
            g2d.draw(new Ellipse2D.Double(x, y, width, height));
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
     * @return the value of the filled data field
     */
    public boolean getFilled(){
        return filled; 
    }

}
