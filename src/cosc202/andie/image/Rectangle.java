package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.*;

public class Rectangle implements ImageOperation {
    private Point start;
    private Point end; 
    boolean filled = true;

    public Rectangle(Point start, Point end){
        this.start = start;
        this.end = end; 
    }

    public BufferedImage apply(BufferedImage input){
        Graphics2D g2d = input.createGraphics();
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

    public void setFilled(boolean filled){
        this.filled = filled;
    }

    public boolean getFilled(){
        return filled; 
    }

}