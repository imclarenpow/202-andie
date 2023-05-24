package cosc202.andie.edit;

import java.awt.image.BufferedImage;
import cosc202.andie.image.*;

public class FlipVertical implements ImageOperation, java.io.Serializable {
        // String to set the direction of the flip
            /**
             * <p>
             * Construct a Flip Vertical object 
             * </p>
             * 
             * <p>
             * The direction is what way the image will be flipped Horizontally or Vertically
             * </p>
             * 
             * @param flipDirection The direction indicator of the ne Flip object
             */
            FlipVertical(){
            }
            
        
            /**
     * <p>
     * Apply a Flip Vertical to an image.
     * </p>
     * 
     * 
     * @param input The image to apply the Flip Vertical to.
     * @return The resulting Flip Vertical  image.
     */
            public BufferedImage apply (BufferedImage input){
                int width = input.getWidth();
        int height = input.getHeight();
                int pixelHeight = height -1;// sets a vaeriable to make sure the loop doesn't go out of bounds

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height/2; row++) {
               int first = input.getRGB(col, row);//store first pixels to swap
               int last = input.getRGB(col, pixelHeight - row);// store second pixel to swap
               input.setRGB(col, row, last);//set first pixel
               input.setRGB(col, pixelHeight - row, first);//set secon pixel
            }
        }
        return input;
                
            }
}
