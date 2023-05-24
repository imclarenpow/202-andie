package cosc202.andie.edit;
import java.awt.image.BufferedImage;
import cosc202.andie.image.*;

public class Rotate90Clockwise implements ImageOperation, java.io.Serializable  {
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
    Rotate90Clockwise(){
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
     * @return The rotated image.
     */
    public BufferedImage apply (BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage output = new BufferedImage(height, width, input.getType());//new image to assign pixels to

            int colNum = output.getWidth();//Integer to keep track of which column of the new image to assign inputs rows pixels to.
        
            for (int row = 0; row < height; row++) {
                colNum--;
                for (int col = 0; col < width; col++) {
                int first = input.getRGB(col, row);//Store the pixel to be set
                output.setRGB(colNum, col, first);//set the pixel in the new image
                
                }
            }
        return output;
    }
    }
    

