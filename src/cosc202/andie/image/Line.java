package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.*;

/**
 * <p>
 * A class that applies a line to an image
 * </p>
 * 
 * <p>
 * Uses coordinates to identify the area to draw the line, passed to the constructor from an instance of Select
 * Draws the line between the start and end points
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class Line implements ImageOperation {
    //Data fields
    private Point start;
    private Point end; 
    private Color drawingColour;
    boolean filled = true;

    /**
     * <p>
     * Creates a new Line operation
     * </p>
     * @param start the starting point of the line
     * @param end the ending point of the line
     * @param drawingColour the colour of the line
     */
    public Line(Point start, Point end, Color drawingColour){
        this.start = start;
        this.end = end;
        this.drawingColour = drawingColour;
    }

    /**
     * <p>
     * Apply the line operation to the input image
     * </p>
     * 
     * <p>
     * Calculates the line to draw the line based on the start and end points
     * </p>
     * 
     * @param input the image to draw the line on
     * @return the image with the line drawn on it
     */
    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
        g2d.setColor(drawingColour);
        // Calculate the rectangle to crop based on the start and end points
        int x1 = start.x;
        int x2 = end.x;
        int y1 = start.y;
        int y2 = end.y;
        g2d.drawLine(x1, y1, x2, y2);
        return input;
    }

}
