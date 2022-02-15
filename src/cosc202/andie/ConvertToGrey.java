package cosc202.andie;

import java.awt.image.*;

public class ConvertToGrey implements ImageOperation, java.io.Serializable {

    ConvertToGrey() {

    }

    public BufferedImage apply(BufferedImage input) {
  
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                int argb = input.getRGB(x, y);
                int a = argb & 0xFF000000 >> 24;
                int r = argb & 0x00FF0000 >> 16;
                int g = argb & 0x0000FF00 >> 8;
                int b = argb & 0x000000FF;

                int gray = (int) Math.round(0.3*r + 0.6*g + 0.1*b);

                argb = (a << 24) + (gray << 16) + (gray << 8) + gray;
                input.setRGB(x, y, argb);
            }
        }
        
        return input;
    }
    
}
