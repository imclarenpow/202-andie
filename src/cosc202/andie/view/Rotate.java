package cosc202.andie.view;

import java.awt.image.BufferedImage;

import cosc202.andie.image.*;

/**
 * <p>
 * ImageOperation to rotate the image being displayed 
 * </p>
 * 
 * <p>
 * A rotate filter that takes rotates the pixels in an image treating them 
 * like a 2d array for 90 degree roatations and flips the pixels in place for 
 * 180degree rotation.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Rochelle Cole and idea from chatGPT
 */

public class Rotate implements ImageOperation{
    
    private String rotateDirection;// String to set the direction of the rotate
    /**
     * <p>
     * Construct a Rotate object 
     * </p>
     * 
     * <p>
     * The direction th eimage will be rotated.
     * </p>
     * 
     * @param rotateDirection The direction indicator of the rotate object
     */
    public Rotate(String direction){
        this.rotateDirection = direction;
    }

    /**
     * <p>
     * Apply a Rotate object to an image.
     * </p>
     * 
     * <p>
     * The Rotate object uses rotateDirection to determine direction of the rotate
     * The apply method calls applyClockwiseNinety, applyAntiClockwiseNinety4 and applyHundredEighty on the
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
        
        if (rotateDirection == "ClockwiseNinety") {
            input = applyClockwiseNinety(input, w, h); // chat GPT was used for the idea for the "input =" as I couldn't figure out why it wouldn't return the image
        }
        else if (rotateDirection == "AntiClockwiseNinety") {
            input = applyAntiClockwiseNinety(input, w, h);
        }
        else if (rotateDirection == "HundredEighty") {
            applyHundredEighty(input, w, h);
        } 
        return input;//return updated image
        
    }
    
    /**
     * <p>
     * Apply Rotate Clockwise to an image.
     * </p>
     * 
     * <p>
     * The Rotate object uses rotateDirection to determine direction of the rotate
     * The applyClockwiseNinety method creates a new buffered image and takes the row
     * of the original image and assigns it to a columna ccordingly.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the Flip object  to.
     * @param width The amount of pixels in the width of the input object.
     * @param height The amount of pixels in the height of the input object.
     * @return The rotated image.
     */
    public BufferedImage applyClockwiseNinety (BufferedImage input, int width, int height){
        
        BufferedImage output = new BufferedImage(height, width, input.getType());//new image to assign pixels to

        int colNum = output.getWidth();//Integer to keep track of which column of the new image to assign inputs rows pixels to.
      
        for (int row = 0; row < height; row++) {
            colNum--;
            for (int col = 0; col < width; col++) {
               int first = input.getRGB(col, row);//Store the pixel to be set
               output.setRGB(colNum, col, first);//set the pixel in the new image
            
            }
        }
        return output;//return updated image
    }

    /**
     * <p>
     * Apply Rotate Anti-Clockwise to an image.
     * </p>
     * 
     * <p>
     * The Rotate object uses rotateDirection to determine direction of the rotate
     * The applyAntiClockwiseNinety method creates a new buffered image and takes the pixels
     * of the original image and assigns it to the new image accordingly.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the rotate object to.
     * @param width The amount of pixels in the width of the input object.
     * @param height The amount of pixels in the height of the input object.
     * @return The rotated image.
     */
    public BufferedImage applyAntiClockwiseNinety (BufferedImage input, int width, int height){
        
        BufferedImage output = new BufferedImage(height, width, input.getType());//new image to assign pixels to

        int rowNum = output.getHeight();//Integer to keep track of which row of the new image to assign inputs columns pixels to.
      
        for (int col = 0; col < width; col++) {
            rowNum--;
            for (int row = 0; row < height; row++) {
               int first = input.getRGB(col, row);//Store the pixel to be set
               output.setRGB(row, rowNum, first);//set the pixel in the new image
            
            }
        }
        return output;//return updated image
        
    }
    
    /**
     * <p>
     * Apply Rotate Hundred and Eighty to an image.
     * </p>
     * 
     * <p>
     * The Rotate object uses rotateDirection to determine direction of the rotate
     * The applyHundredEighty method creates a new buffered image and swaps the pixels
     * of the original image horizontally and vertically in place.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the rotate object to.
     * @param width The amount of pixels in the width of the input object.
     * @param height The amount of pixels in the height of the input object.
     * @return The rotated image.
     */
    public BufferedImage applyHundredEighty (BufferedImage input, int width, int height){
        
        int pixelWidth = width - 1;//variable to set the limit of what pixel to loop to for the width of the image
        int pixelHeight = height -1;//variable to set the limit of what pixel to loop to for the height of the image

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width/2; col++) {
               int first = input.getRGB(col, row);//stores the first pixel to swap
               int last = input.getRGB(pixelWidth - col, row);//stores the second pixel to swap
               input.setRGB(col, row, last);//set the first pixel in it's new location
               input.setRGB(pixelWidth-col, row, first);//set the second pixel in it's new location
            }
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height/2; row++) {
               int first = input.getRGB(col, row);//stores the first pixel to swap
               int last = input.getRGB(col, pixelHeight - row);//stores the second pixel to swap
               input.setRGB(col, row, last);//set the first pixel in it's new location
               input.setRGB(col, pixelHeight - row, first);//set the second pixel in it's new location
            }
        }
        
        return input;  //return the updated image     
    }
}

  