package cosc202.andie.filter;

import java.awt.image.BufferedImage;
//import java.awt.Color;

import cosc202.andie.image.*;

/** Using MeanFilter Code as a template to start the layout of the filter,
 * @author Isaac 
 * With help from ChatGPT
 * 
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable{
    private int radius;
    
    GaussianBlur(int radius){
        this.radius = radius;
    }
    GaussianBlur(){
        this(1);
    }
    
    public BufferedImage apply(BufferedImage input) {
        //get the dimensions of the image inputted
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        if(radius>input.getWidth()||radius>input.getHeight()){
            System.out.println("Don't break my filter I'm not changing anything >:(");
            return input;
        }
        // Sigma / Variance value used for determining the kernel sizes for the grid
        // Set to 1/3 of radius like recommended in the lab book
        double sigma = (double)radius/3;
        double[][] kernel = createKernel(radius, sigma);
    
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
                        int px = Math.min(Math.max(x + i, 0), input.getWidth() - 1);
                        int py = Math.min(Math.max(y + j, 0), input.getHeight() - 1);
                        int rgb = input.getRGB(px, py);
                        double weight = kernel[i + radius][j + radius];
                        // changed to use hex instead of 255 rgb values
                        red += weight * ((rgb >> 16) & 0xFF);
                        green += weight * ((rgb >> 8) & 0xFF);
                        blue += weight * (rgb & 0xFF);
                        weightSum += weight;
                    }
                }
    
                // Normalize the color values by the total weight
                int pixel = 0xFF000000 |
                        (((int) (red / weightSum)) << 16) |
                        (((int) (green / weightSum)) << 8) |
                        ((int) (blue / weightSum));
                output.setRGB(x, y, pixel);
            }
        }
        
        return output;
    }

    /** separate kernel creation method to make it easier to understand whats going on */ 
    private double[][] createKernel(int radius, double sigma) {
        double[][] kernel = new double[radius * 2 + 1][radius * 2 + 1];
        //parts of equation turned to variables for readability
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