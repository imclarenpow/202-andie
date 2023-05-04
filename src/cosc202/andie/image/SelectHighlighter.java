package cosc202.andie.image;

import java.awt.image.BufferedImage;
import java.awt.Point;

public class SelectHighlighter implements ImageOperation, java.io.Serializable{
    private final int BRIGHTNESS = 50;
    private final int CONTRAST = 0;
    private int startX;
    private int startY;
    private int height; 
    private int width;
    private Point start; 
    private Point end;

    SelectHighlighter(Point start, Point end){
        this.start = start; 
        this.end = end;
    }


    public BufferedImage apply(BufferedImage input){
        //Math for Subimage fields
        startX = Math.min(start.x, end.x);
        startY = Math.min(start.y, end.y);
        width = Math.abs(start.x - end.x);
        height = Math.abs(start.y - end.y);
        for (int y = startY; y < startY + height; ++y) {
            for (int x = startX; x < startX + width; ++x) {

                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int adjR = adjustmentMath(r);
                int adjG = adjustmentMath(g);
                int adjB = adjustmentMath(b);

                argb = (a << 24) | (adjR << 16) | (adjG << 8) | adjB;
                input.setRGB(x, y, argb);
            }
        }
        return input;
    }
    /**
     * <p>
     * Adjusts the given value by the requested percentages.
     * </p>
     * 
     * <p>
     * The adjustment is done using the following equation:
     * </p>
     * 
     * <p>
     * adjV = (int) Math.round((1 + contrast / 100) * (v - 127.5) + 127.5 * (1 + brightness / 100))
     * </p>
     * 
     * <p>
     * If the adjusted value is less than 0 or greater than 255, it is corrected to the appropriate limit.
     * </p>
     * 
     * @param v the value to be adjusted, in the range [0, 255]
     * @return the adjusted value, in the range [0, 255]
     */
    private int adjustmentMath(int v){
        int adjV = 0;
        adjV = (int) Math.round((1+ CONTRAST/100) * (v-127.5) + 127.5 * (1+ BRIGHTNESS/100));
        if(adjV < 0){
            adjV = 0;
        }else if (adjV > 255){
            adjV = 255;
        }
        return adjV; 
    }
}
