package cosc202.andie.filter;

import java.awt.image.*;
import java.util.Arrays;

import cosc202.andie.image.*;

/**
 * <p>
 * ImageOperation to apply a Median (simple blur) filter.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {

    private int radius;
    // The offset that will be added to each color component of the pixel
    private int offset;

    // Constructor that takes both radius and offset as parameters
    MedianFilter(int radius, int offset) {
        this.radius = radius;
        this.offset = offset;
    }

    // Constructor that only takes radius as parameter, default offset is set to 0
    MedianFilter(int radius) {
        this(radius, 0);
    }

    // Default constructor, default radius is set to 1, default offset is set to 0
    MedianFilter() {
        this(1, 0);
    }

    /**
     * <p>
     * Applies a Median Filter to an Image
     * <p>
     * 
     * @param input Is the image that the Median filter is applied to.
     * @return The result of the of image post filter application.
     */
    public BufferedImage apply(BufferedImage input) {
        int dimension = 2 * radius + 1;
        int width = input.getWidth();
        int height = input.getHeight();
        // Creates a new BufferedImage to hold the output
        BufferedImage output = new BufferedImage(width, height, input.getType());

        // Iterate over each pixel in the input image
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                // Arrays to store the color components of the neighboring pixels
                int[] r1 = new int[dimension * dimension];
                int[] g1 = new int[dimension * dimension];
                int[] b1 = new int[dimension * dimension];
                int[] x1 = new int[dimension * dimension];

                int index = 0;
                // Iterate over each neighboring pixel
                for (int k = i - radius; k <= i + radius; ++k) {
                    for (int l = j - radius; l <= j + radius; ++l) {
                        // Handle edge pixels by clamping k2 and l2 to valid indices
                        int k2 = Math.max(0, Math.min(height - 1, k));
                        int l2 = Math.max(0, Math.min(width - 1, l));

                        // Extract the color components of the pixel
                        int rgbx = input.getRGB(l2, k2);
                        int x = (rgbx & 0xFF000000) >> 24;
                        int r = (rgbx & 0x00FF0000) >> 16;
                        int g = (rgbx & 0x0000FF00) >> 8;
                        int b = (rgbx & 0x000000FF);

                        // Store the color components in the corresponding arrays
                        r1[index] = r;
                        g1[index] = g;
                        b1[index] = b;
                        x1[index] = x;

                        index++;
                    }
                }

                // Sort the arrays of color components
                Arrays.sort(x1);
                Arrays.sort(r1);
                Arrays.sort(g1);
                Arrays.sort(b1);

                // Calculate the median color components, add the offset, and ensure that the
                // result stays within the valid range (0-255)
                // The result is then packed into an integer and set as the color of the
                // corresponding pixel in the output image
                int rgb = ((x1[x1.length / 2]) << 24) | (((r1[r1.length / 2] + offset) & 0xFF) << 16)
                        | (((g1[g1.length / 2] + offset) & 0xFF) << 8) | ((b1[b1.length / 2] + offset) & 0xFF);
                output.setRGB(j, i, rgb);
            }
        }
        // Return the output image
        return output;
    }
}
