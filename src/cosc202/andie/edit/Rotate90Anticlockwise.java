package cosc202.andie.edit;
import java.awt.image.BufferedImage;
import cosc202.andie.image.*;

public class Rotate90Anticlockwise implements ImageOperation, java.io.Serializable  {
    /**
     * <p>
     * Construct a Rotate object 
     * </p>
     * 
     * <p>
     * The direction th eimage will be rotated.
     * </p>
     */
    Rotate90Anticlockwise(){
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
     * @return The rotated image.
     */
        public BufferedImage apply (BufferedImage input){
            int width = input.getWidth();
            int height = input.getHeight();
            BufferedImage output = new BufferedImage(height, width, input.getType());//new image to assign pixels to

            int rowNum = output.getHeight();//Integer to keep track of which row of the new image to assign inputs columns pixels to.
        
            for (int col = 0; col < width; col++) {
                rowNum--;
                for (int row = 0; row < height; row++) {
                int first = input.getRGB(col, row);//Store the pixel to be set
                output.setRGB(row, rowNum, first);//set the pixel in the new image
                
                }
            }
            return output;
        }
        }

