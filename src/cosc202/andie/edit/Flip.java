package cosc202.andie.edit;

import java.awt.image.BufferedImage;

import cosc202.andie.image.*;
/**
 * <p>
 * ImageOperation to flip the image being displayed 
 * </p>
 * 
 * <p>
 * A flip filter that takes flips the pixels in place and returns the new image.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Rochelle Cole
 */
public class Flip implements ImageOperation {

    private String flipDirection;// String to set the direction of the flip
    /**
     * <p>
     * Construct a Flip object 
     * </p>
     * 
     * <p>
     * The direction is what way the image will be flipped Horizontally or Vertically
     * </p>
     * 
     * @param flipDirection The direction indicator of the ne Flip object
     */
    Flip(String direction){
        this.flipDirection = direction;
    }
    

    /**
     * <p>
     * Apply a Flip object to an image.
     * </p>
     * 
     * <p>
     * The Flip object uses flipDirection to determine direction of the flip
     * The apply method calls applyFlipHorizontal and applyFlipVertical on the
     * image accordingly.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the Flip object  to.
     * @return The flipped image.
     */
    public BufferedImage apply (BufferedImage input){
        int w = input.getWidth();
        int h = input.getHeight();
        
        if (flipDirection == "Horizontal") {
            applyFlipHorizontal(input, w, h);
        }
        else if (flipDirection == "Vertical") {
            applyFlipVertical(input, w, h);
        } 
        return input;
        
    }
    /**
     * <p>
     * Apply a FlipHorizontal to an image.
     * </p>
     * 
     * <p>
     * Flips the image horizontally in place swapping the pixels.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the Flip object to.
     * @param width The width of the image being passed in.
     * @param height The height of the image being passed in.
     * @return The flipped image.
     */
    public BufferedImage applyFlipHorizontal (BufferedImage input, int width, int height){
        
        int pixelWidth = width - 1;//sets a variable to keep loop through thr rows and stop it going out of bounds

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width/2; col++) {
               int first = input.getRGB(col, row);//store first pixels to swap
               int last = input.getRGB(pixelWidth - col, row);// store second pixel to swap
               input.setRGB(col, row, last);//set first pixel
               input.setRGB(pixelWidth-col, row, first);//set secon pixel
            }
        }
        return input;//return the image


       
    } 
    /**
     * <p>
     * Apply a FlipVertical to an image.
     * </p>
     * 
     * <p>
     * Flips the image Vertically in place swapping the pixels.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the Flip object to.
     * @param width The width of the image being passed in.
     * @param height The height of the image being passed in.
     * @return The flipped image.
     */
    public BufferedImage applyFlipVertical (BufferedImage input,int width, int height){
        
        int pixelHeight = height -1;// sets a vaeriable to make sure the loop doesn't go out of bounds

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height/2; row++) {
               int first = input.getRGB(col, row);//store first pixels to swap
               int last = input.getRGB(col, pixelHeight - row);// store second pixel to swap
               input.setRGB(col, row, last);//set first pixel
               input.setRGB(col, pixelHeight - row, first);//set secon pixel
            }
        }
        return input;//return the image
       
       
    }
}

