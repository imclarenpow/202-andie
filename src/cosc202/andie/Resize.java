package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;

public class Resize implements ImageOperation, java.io.Serializable {
    private int scale;

    Resize(int scale) {
        this.scale = scale;
    }

    Resize() {
        this(0);
    }

    public BufferedImage apply(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        double adjustedScale = 1.0 + scale / 100.0;
        width = (int)Math.round((double)width * adjustedScale);
        height = (int)Math.round((double)height * adjustedScale);
        Image scaledImage = input.getScaledInstance(width, height, 0);
        

        BufferedImage output = new BufferedImage(width, height, input.getType());
        Graphics2D outputGraphics = output.createGraphics();
        outputGraphics.drawImage(scaledImage, 0, 0, null);
        return output;
    }
}
