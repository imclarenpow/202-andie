package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;

public class Resize implements ImageOperation, java.io.Serializable {
    private double resizeScale;

    Resize(double resizeScale) {
        this.resizeScale = resizeScale;
    }

    Resize() {
        this(1);
    }

    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        width = (int)Math.round((double)width * resizeScale);
        height = (int)Math.round((double)height * resizeScale);

        // Creates a new Image object of the correct size
        Image scaledImage = input.getScaledInstance(width, height, 0);
        
        // Draws the resized image onto a new BufferedImage
        // Adapted from http://underpop.online.fr/j/java/help/java-converting-an-image-to-a-bufferedimage.html.gz
        BufferedImage output = new BufferedImage(width, height, input.getType());
        Graphics2D g2 = output.createGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        return output;
    }
}
