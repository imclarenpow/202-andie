package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;


/**
 * <p>
 * ImageOperation to resize the currently displayed image
 * </p>
 * 
 * <p>
 * A resize filter takes a scale and multiplies the dimensions of the
 * image by this. The new dimensions are used to create a new image,
 * which is drawn onto a new BufferedImage using the Graphics2D class.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Niamh Avery
 * @version 1.0
 */
public class Resize implements ImageOperation, java.io.Serializable {
    private double resizeScale;

    /**
     * <p>
     * Construct a Resize filter with the given scale.
     * </p>
     * 
     * <p>
     * The scale is some positive value as the image's dimensions are
     * multiplied by this amount. If the given scale is less than or equal to 0,
     * it will be replaced by 0.01.
     * </p>
     * 
     * @param radius The radius of the newly constructed MeanFilter
     */
    Resize(double resizeScale) {
        this.resizeScale = resizeScale;
        if (resizeScale <= 0) {
            resizeScale = 0.01;
        }
    }

    /**
     * <p>
     * A default constructor for a Resize filter
     * </p>
     * 
     * <p>
     * This constructor does not require a parameter and will set the
     * scale to 1. This means the dimensions of the current image will
     * not change.
     * </p>
     */
    Resize() {
        this(1);
    }

    /**
     * <p>
     * Apply a Resize filter to an image.
     * </p>
     * 
     * <p>
     * The resize filter is implemented using the Graphics 2D class.
     * The size of the scale used to resize the image is specified by the {@link scale}.
     * The current image dimensions are multiplied by the scale.  
     * As such, larger scales (>1) produce larger images.
     * </p>
     * 
     * <p>
     * <a href="https://download.java.net/java/early_access/panama/docs/api/java.desktop/java/awt/Graphics2D.html">Info on the Graphics2D class</a>
     * </p>
     * 
     * @param input The image to apply the Resize filter to.
     * @return The resulting (resized/scaled) image.
     */
    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        width = (int)Math.round((double)width * resizeScale);
        height = (int)Math.round((double)height * resizeScale);

        // Creates a new Image object of the correct size
        Image scaledImage = input.getScaledInstance(width, height, 0);
        
        // Draws the resized image onto a new BufferedImage
        // Adapted from http://underpop.online.fr/j/java/help/java-converting-an-image-to-a-bufferedimage.html.gz
        BufferedImage output = new BufferedImage(width, height, input.getType());
        Graphics2D g2 = output.createGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        return output;
    }
}
