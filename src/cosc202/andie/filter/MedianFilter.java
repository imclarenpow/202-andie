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
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable{

    private int radius;

    MedianFilter(int radius){
        this.radius = radius;
    }

    MedianFilter(){
        this(1);
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
        //Gets the width of the inputed image
        int width = input.getWidth();
        //Gets the height of the inputed image
        int height = input.getHeight();
        //Creates an instance of the output, inputing the width & height.
        BufferedImage output = new BufferedImage(width, height, input.getType());

        for (int i = radius; i < height - radius; ++i) {
            for (int j = radius; j < width - radius; ++j) {
                //Method uses arrays to store the rgb values of neighbouring pixels
                int[] r1 = new int[dimension * dimension];
                int[] g1 = new int[dimension * dimension];
                int[] b1 = new int[dimension * dimension];
                int[] x1 = new int[dimension * dimension];

                int index = 0;
                for (int k = i - radius; k <= i + radius; ++k) {
                    for (int l = j - radius; l <= j + radius; ++l) {
                        if (k < 0 || k >= height || l < 0 || l >= width) {
                            continue;
                        }
                        //Extracts the rgb & x (transparency) values from each pixel
                        int rgbx = input.getRGB(l, k);
                        // >> (Bit shift operator) shifts the extracted value to the right by the specified number of bits
                        int x = (rgbx & 0xFF000000) >> 24;
                        int r = (rgbx & 0x00FF0000) >> 16;
                        int g = (rgbx & 0x0000FF00) >> 8;
                        int b = (rgbx & 0x000000FF);

                        r1[index] = r;
                        g1[index] = g;
                        b1[index] = b;
                        x1[index] = x;

                        index++;
                    }
                }

                Arrays.sort(x1);
                Arrays.sort(r1);
                Arrays.sort(g1);
                Arrays.sort(b1);

                int rgb = ((x1[x1.length / 2]) << 24) | ((r1[r1.length / 2]) << 16) | ((g1[g1.length / 2]) << 8) | (b1[b1.length / 2]);
                output.setRGB(j, i, rgb);
            }
        }

        return output;
    }
}
