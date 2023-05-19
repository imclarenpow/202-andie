package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.*;

public class Line implements ImageOperation {
    private Point start;
    private Point end; 
    boolean filled = true;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end; 
    }

    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
        // Calculate the rectangle to crop based on the start and end points
        int x1 = start.x;
        int x2 = end.x;
        int y1 = start.y;
        int y2 = end.y;
        g2d.drawLine(x1, y1, x2, y2);
        return input;
    }

}