package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * Image Operation to apply a Sharpness Filter.
 * </p>
 * 
 * <p>
 * Used mean filter as a template to start the creation of this filter.
 * </p>
 * 
 * @author James Larkin
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {

    SharpenFilter(){}

    /**
     * <p> 
     * Applies a Sharpen Filter to an Image
     * <p>
     * 
     * <p> 
     * Sharpness filter is implemented through the use of convolution.
     * <p>
     * 
     * @param input Is the image that the Sharpess filter is applied to.
     * @return The result of the of image post filter application.
     */
    public BufferedImage apply(BufferedImage input){        
        float[] array = {0      , -1/2.0f, 0      ,
                         -1/2.0f, 3      , -1/2.0f,
                         0      , -1/2.0f, 0       };

        Kernel kernel = new Kernel(3, 3, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input,output);
        return output;
                               
    }
    
}
