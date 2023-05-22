package cosc202.andie.filter;

import java.awt.image.BufferedImage;
//import java.awt.Color;

import cosc202.andie.image.*;

/**
 * Using MeanFilter Code as a template to start the layout of the filter,
 * 
 * @author Isaac
 *         With help from ChatGPT
 * 
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {
    private int radius;
    private double offset; //new code for the offset

    GaussianBlur(int radius, double offset) {
        this.radius = radius;
        this.offset = offset;
    }

    GaussianBlur() {
        this(1, 0.0); //the default offset is 0.0

    }

    public BufferedImage apply(BufferedImage input) {
        int padding = radius * 2;
        BufferedImage paddedInput = new BufferedImage(
                input.getWidth() + padding,
                input.getHeight() + padding,
                BufferedImage.TYPE_INT_RGB);

        // copy input into paddedInput
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                int rgb = input.getRGB(x, y);
                paddedInput.setRGB(x + radius, y + radius, rgb);
            }
        }

        double sigma = (double) radius / 3;
        double[][] kernel = createKernel(radius, sigma);

        // get the dimensions of the padded image
        int paddedWidth = paddedInput.getWidth();
        int paddedHeight = paddedInput.getHeight();
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

       // Scanning colour of each pixel
       for (int x = 0; x < input.getWidth(); x++) {
        for (int y = 0; y < input.getHeight(); y++) {
            // Compute weighted average of colors in the neighborhood
            double red = 0.0;
            double green = 0.0;
            double blue = 0.0;
            double weightSum = 0.0;
            for (int i = -radius; i <= radius; i++) {
                for (int j = -radius; j <= radius; j++) {
                    int px = x + i + radius;
                    int py = y + j + radius;
                    int rgb = paddedInput.getRGB(px, py);
                    double weight = kernel[i + radius][j + radius];
                    red += weight * ((rgb >> 16) & 0xFF) + offset;
                    green += weight * ((rgb >> 8) & 0xFF) + offset;
                    blue += weight * (rgb & 0xFF) + offset;
                    if (px >= 0 && px < paddedWidth && py >= 0 && py < paddedHeight) {
                        weightSum += weight;
                    }
                }
            }

            // Normalize the color values by the total weight
            int pixel = 0xFF000000 |
                    (((int) Math.max(0, Math.min(255, red / weightSum))) << 16) |
                    (((int) Math.max(0, Math.min(255, green / weightSum))) << 8) |
                    ((int) Math.max(0, Math.min(255, blue / weightSum)));
            output.setRGB(x, y, pixel);
        }
    }

    return output;
}

    /**
     * separate kernel creation method to make it easier to understand whats going
     * on
     */
    private double[][] createKernel(int radius, double sigma) {
        double[][] kernel = new double[radius * 2 + 1][radius * 2 + 1];
        // parts of equation turned to variables for readability
        double sigmaSquared = sigma * sigma;
        double twoSigmaSquared = 2 * sigmaSquared;
        double sqrtTwoPiSigma = Math.sqrt(2 * Math.PI) * sigma;

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                double rSquared = i * i + j * j;
                kernel[i + radius][j + radius] = Math.exp(-rSquared / twoSigmaSquared) / sqrtTwoPiSigma;
            }
        }

        return kernel;
    }

}