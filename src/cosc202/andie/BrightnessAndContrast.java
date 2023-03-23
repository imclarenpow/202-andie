package cosc202.andie;

import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class BrightnessAndContrast implements ImageOperation, java.io.Serializable{

    private double brightness;
    private double contrast;

    public BrightnessAndContrast(int brightness, int contrast){
        this.brightness = brightness;
        this.contrast = contrast;
    }
    
    /**
     * <p>
     *  Apply a Brightness and Contrast adjustment to an image
     * <p>
     *  As this adjustment is applied to the entire image, a simple loop performs the operation on all pixels, 
     *  using an equation run by a separate method
     * <p>
     * @param input
     * @return The resulting image with brightness and contrast adjustments
     * */
    public BufferedImage apply(BufferedImage input){

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                // if greyscale check??

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
     * Adjusts value by requested percentages
     * <p>
     * Equation used to apply brightness and contrast adjustments, with simple checks to keep values in the range 0-255
     * <p>
     * @param v - a value to be adjusted
     * @return The adjusted value
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
