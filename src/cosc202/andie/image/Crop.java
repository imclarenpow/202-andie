package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

/*
 * Select opens submenu
	Crop - pressed when area selected, will need edge handling for mouselistener
	     - check if area selected? 
	     - when selected, change icon from grayed out to clickable
	     - when clicked, pass points from Select to Crop
 */

public class Crop implements ImageOperation {
    private BufferedImage input;
    private Point start;
    private Point end;
    
    Crop(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public BufferedImage apply(BufferedImage input){
        this.input = input;
        // Calculate the rectangle to crop based on the start and end points
        int x = Math.min(start.x, end.x);
        int y = Math.min(start.y, end.y);
        int width = Math.abs(start.x - end.x);
        int height = Math.abs(start.y - end.y);
        // Crop the image using the rectangle
        BufferedImage cropped = input.getSubimage(x, y, width, height);
        return cropped; 
    }
}