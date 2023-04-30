package cosc202.andie.filter;

import java.awt.image.*;

import cosc202.andie.image.*;

/**
 * <p>
 * ImageOperation to apply a Soft (weak) blur filter.
 * </p>
 * 
 * <p>
 * A Soft Blur filter can be implemented by a convoloution.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Niamh Avery - Adapted from the University of Otago's COSC202 Lab Book
 * @version 1.0
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
    SoftBlur() {

    }

    /**
     * <p>
     * Apply a Soft Blur filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Soft Blur filter is implemented via convolution.
     * The size of the convolution kernel is set to 3x3.  
     * </p>
     * 
     * @param input The image to apply the Soft Blur filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage input) {
        float[] array = {0, 1/8.0f, 0,
                         1/8.0f, 1/2.0f, 1/8.0f,
                         0, 1/8.0f, 0};
        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(),
                                    input.copyData(null),
                                    input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);
        return output;
    }
}
