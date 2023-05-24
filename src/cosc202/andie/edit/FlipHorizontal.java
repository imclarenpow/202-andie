package cosc202.andie.edit;

import java.awt.image.BufferedImage;
import cosc202.andie.image.*;

public class FlipHorizontal implements ImageOperation, java.io.Serializable {
    /**
     * <p>
     * Construct a Flip Horizontal object 
     * </p>
     * 
     * <p>
     * The direction is what way the image will be flipped Horizontally or Vertically
     * </p>
     * 
     * @param flipDirection The direction indicator of the ne Flip object
     */
    FlipHorizontal(){
    }
    

    /**
     * <p>
     * Apply a Flip Horizontal  to an image.
     * </p>
     * 
     * 
     * @param input The image to apply the Flip Horizontal   to.
     * @return The resulting Flip Horizontal  image.
     */
    public BufferedImage apply (BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
        
        int pixelWidth = width - 1;//sets a variable to keep loop through thr rows and stop it going out of bounds

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width/2; col++) {
               int first = input.getRGB(col, row);//store first pixels to swap
               int last = input.getRGB(pixelWidth - col, row);// store second pixel to swap
               input.setRGB(col, row, last);//set first pixel
               input.setRGB(pixelWidth-col, row, first);//set secon pixel
            }
        }
        return input;
        
    }
    
}
