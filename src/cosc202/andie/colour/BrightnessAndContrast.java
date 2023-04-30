package cosc202.andie.colour;

import java.awt.image.BufferedImage;

import cosc202.andie.image.*;
/**
 * <p> 
 * ImageOperation to adjust brightness and contrast of an image
 * </p>
 * 
 * <p>
 * This operation is applied to the entire image using a simple loop that performs the operation on all pixels. 
 * The adjustment is done using an equation that is run by a separate method.
 * </p>
 * 
 * <p>
 * The new brightness and contrast values are set in the constructor, and are applied to the input image when the 
 * apply method is called.
 * </p>
 * 
 * @see cosc202.andie.image.ImageOperation
 * @author Nic Scott - adapted from the COSC202 Lab Book
 * @version 1.0
 */

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable{

    private double brightness;
    private double contrast;

    /**
     * Creates a new BrightnessAndContrast object storing the inputted brightness and contrast values.
     * 
     * @param brightness the new brightness value, in the range [-100, 100]
     * @param contrast the new contrast value, in the range [-100, 100]
     */
    public BrightnessAndContrast(int brightness, int contrast){
        this.brightness = brightness;
        this.contrast = contrast;
    }
    
    /**
     * <p>
     * Applies a brightness and contrast adjustment to the input image.
     * </p>
     * 
     * <p>
     * This method loops over all pixels in the input image and applies a brightness and contrast adjustment
     * to each pixel using bitwise operations.
     * </p>
     * @param input the input image to adjust
     * @return the resulting image with brightness and contrast adjustments
     */
    public BufferedImage apply(BufferedImage input){
        
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {

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
        adjV = (int) Math.round((1+ contrast/100) * (v-127.5) + 127.5 * (1+ brightness/100));
        if(adjV < 0){
            adjV = 0;
        }else if (adjV > 255){
            adjV = 255;
        }
        return adjV; 
    }
    
}
