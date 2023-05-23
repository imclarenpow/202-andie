package cosc202.andie.filter;

import java.awt.Color;
import java.awt.image.*;
import cosc202.andie.image.*;

/**
 * <p>
 * A class for the Emboss Filter
 * Used to apply an Emboss Filter to a particular image
 * Specifies a filter index
 * </p>
 */
public class EmbossFilter implements ImageOperation, java.io.Serializable {

    // Data field
    private int filterIndex;

    /**
     * <p>
     * A constructor for an Emboss Filter
     * </p>
     * 
     * @param filterIndex the index of the Emboss Filter
     */
    public EmbossFilter(int filterIndex) {
        this.filterIndex = filterIndex;
    }

    /**
     * Applies an emboss filter to the given image using the specified filter index.
     *
     * @param image       The image to apply the filter to.
     * @param filterIndex The index of the emboss filter to use.
     * @return The filtered image.
     * 
     * @author James Larkin
     */
    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] embossKernel = getEmbossKernel(filterIndex);

        // Iterate over each pixel of the image
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumRed = 0, sumGreen = 0, sumBlue = 0;

                // Apply the emboss filter convolution
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // Get the color of the neighboring pixel
                        Color pixelColor = new Color(image.getRGB(x + i, y + j));
                        int kernelValue = embossKernel[i + 1][j + 1];

                        // Apply the kernel value to the color channels
                        sumRed += kernelValue * pixelColor.getRed();
                        sumGreen += kernelValue * pixelColor.getGreen();
                        sumBlue += kernelValue * pixelColor.getBlue();
                    }
                }

                // Clamp the resulting values to the valid color range
                // Add bias before clamping
                int newRed = clamp(sumRed + 128, 0, 255);
                int newGreen = clamp(sumGreen + 128, 0, 255);
                int newBlue = clamp(sumBlue + 128, 0, 255);

                // Create a new color using the filtered values
                Color newColor = new Color(newRed, newGreen, newBlue);
                // Set the new color to the filtered image
                filteredImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return filteredImage;
    }

    /**
     * Retrieves the emboss filter kernel based on the filter index.
     *
     * @param filterIndex The index of the emboss filter.
     * @return The emboss filter kernel.
     */
    private static int[][] getEmbossKernel(int filterIndex) {
        int[][] kernel = new int[3][3];

        switch (filterIndex) {
            case 0:
                kernel = new int[][] { { 0, 0, 0 }, { 1, 0, -1 }, { 0, 0, 0 } };
                break;
            case 1:
                kernel = new int[][] { { 0, 0, -1 }, { 0, 0, 0 }, { 1, 0, 0 } };
                break;
            case 2:
                kernel = new int[][] { { 1, 0, 0 }, { 0, 0, 0 }, { 0, 0, -1 } };
                break;
            case 3:
                kernel = new int[][] { { 0, 1, 0 }, { 0, 0, 0 }, { 0, -1, 0 } };
                break;
            case 4:
                kernel = new int[][] { { 0, 0, 1 }, { 0, 0, 0 }, { -1, 0, 0 } };
                break;
            case 5:
                kernel = new int[][] { { 0, 0, 0 }, { -1, 0, 1 }, { 0, 0, 0 } };
                break;
            case 6:
                kernel = new int[][] { { -1, 0, 0 }, { 0, 0, 0 }, { 0, 0, 1 } };
                break;
            case 7:
                kernel = new int[][] { { 0, -1, 0 }, { 0, 0, 0 }, { 0, 1, 0 } };
                break;
            case 8:
                kernel = new int[][] { { 0, 0, -1 }, { 0, 0, 0 }, { 1, 0, 0 } };
                break;
        }
        return kernel;
    }

    /**
     * Clamps the value within the specified range.
     *
     * @param value The value to clamp.
     * @param min   The minimum allowed value.
     * @param max   The maximum allowed value.
     * @return The clamped value.
     */
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));

    }
}
