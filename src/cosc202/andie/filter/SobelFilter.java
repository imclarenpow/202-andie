package cosc202.andie.filter;

import java.awt.Graphics;
import java.awt.image.*;
import cosc202.andie.image.*;

/**
 * <p>
 * Image Operation to apply a Sobel Filter.
 * </p>
 * 
 * <p>
 * Applies a Sobel Filter to a given image
 * </p>
 * 
 * @author James Larkin
 */
public class SobelFilter implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * A default constructor for a Sobel Filter
     * </p>
     */
    SobelFilter() {
    }

    // Data field
    private String sobelType;

    /**
     * <p>
     * Takes a type of Sobel filter to apply to an image
     * </p>
     * @param sobelType the type of Sobel filter
     */
    public SobelFilter(String sobelType) {
        this.sobelType = sobelType;
    }

    /**
     * <p>
     * Applies the Sobel filter to a given image
     * </p>
     * 
     * @param input the image onto which the filter is to be applied
     */
    public BufferedImage apply(BufferedImage input) {
        // Create BufferedImage of type INT_RGB
        BufferedImage inputRGB = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = inputRGB.getGraphics();
        g.drawImage(input, 0, 0, null);
        g.dispose();

        // Define the kernels
        float[] H_KERNEL = { -1f, 0f, +1f, -2f, 0f, +2f, -1f, 0f, +1f };
        float[] V_KERNEL = { -1f, -2f, -1f, 0f, 0f, 0f, +1f, +2f, +1f };

        // Create the convolution operations
        Kernel kernelOpHorizontal = new Kernel(3, 3, H_KERNEL);
        ConvolveOp convolveOpHorizontal = new ConvolveOp(kernelOpHorizontal, ConvolveOp.EDGE_NO_OP, null);

        Kernel kernelOpVertical = new Kernel(3, 3, V_KERNEL);
        ConvolveOp convolveOpVertical = new ConvolveOp(kernelOpVertical, ConvolveOp.EDGE_NO_OP, null);

        // Apply the appropriate filter
        if (sobelType.toLowerCase().equals("horizontal")) {
            return convolveOpHorizontal.filter(inputRGB, null);
        } else if (sobelType.toLowerCase().equals("vertical")) {
            return convolveOpVertical.filter(inputRGB, null);
        } else {
            throw new IllegalArgumentException("Invalid sobelType: " + sobelType);
        }
    }
}