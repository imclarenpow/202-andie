package cosc202.andie.edit;
import java.awt.image.BufferedImage;
import cosc202.andie.image.*;

public class Rotate180 implements ImageOperation, java.io.Serializable{
    /**
     * <p>
     * Construct a Rotate object 
     * </p>
     * 
     * <p>
     * The direction th eimage will be rotated.
     * </p>
     * 
     */
    Rotate180(){
        
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
     * @return The rotated image.
     */
    public BufferedImage apply (BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
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
        
        return input; 
    }
}
