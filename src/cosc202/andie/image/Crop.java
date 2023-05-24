package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

/**
 * <p>
 * A class that handles the user cropping a selected area of an image
 * </p>
 * 
 * <p>
 * Uses coordinates to identify the area to crop, passed to the constructor from an instance of Select
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */

public class Crop implements ImageOperation, java.io.Serializable {
    //Data fields
    private Point start;
    private Point end;
    
    /**
     * <p>
     * Create a new Crop operation.
     * </p>
     * @param start a point representing the top left corner of the area to crop
     * @param end a point representing the bottom right corner of the area to crop
     */
    Crop(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    /**
     * <p>
     * Apply the crop operation to the input image
     * </p>
     * 
     * <p> 
     * Calculates the rectangle to crop based on the start and end points
     * Uses the min and max functions to ensure that the rectangle is always drawn correctly from the top left corner
     * Crops the image using the rectangle
     * </p>
     * 
     * @param input the image to crop
     * @return the cropped image
     */
    public BufferedImage apply(BufferedImage input){
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);

        input = input.getSubimage(x, y, width, height);
        return input; 
    }


    /**
     * <p>
     * Set the coordinates of the start point
     * 
     * @return the start point
     */
    public void setStart(Point start){
        this.start = start;
    }

    /**
     * <p>
     * Set the coordinates of the end point
     * </p>
     * 
     * @return the end point
     */
    public void setEnd(Point end){
        this.end = end;
    }
}