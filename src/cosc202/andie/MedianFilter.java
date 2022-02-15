import java.awt.image.*;
import java.util.*;
public class MedianFilter implements ImageOperation, java.io.Serializable {
    
    private int radius;

    MedianFilter(int radius) {
        this.radius = radius;    
    }

    MedianFilter() {
        this(1);
    }

    public BufferedImage apply(BufferedImage input) {
        int size = (2*radius+1) * (2*radius+1);
        float [] array = new float[size];
        Arrays.fill(array, 1.0f/size);

        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        //BufferedImage output = convOp.createCompatibleDestImage(input, input.getColorModel());
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input, output);

        return output;
    }


}
