package cosc202.andie;

import java.awt.*;
import javax.swing.*;

import cosc202.andie.image.PencilButton;

/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow for rendering of an image, as well as zooming
 * in and out. 
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ImagePanel extends JPanel {

    // Overrides the Panel's size
    public static Dimension screenSizeOverride = new Dimension(0, 0);
    
    /**
     * The image to display in the ImagePanel.
     */
    private EditableImage image;

    /**
     * <p>
     * The zoom-level of the current view.
     * A scale of 1.0 represents actual size; 0.5 is zoomed out to half size; 1.5 is zoomed in to one-and-a-half size; and so forth.
     * </p>
     * 
     * <p>
     * Note that the scale is internally represented as a multiplier, but externally as a percentage.
     * </p>
     */
    private double scale;

    /**
     * <p>
     * Create a new ImagePanel.
     * </p>
     * 
     * <p>
     * Newly created ImagePanels have a default zoom level of 100%
     * </p>
     */
    public ImagePanel() {
        image = new EditableImage();
        scale = 1.0;
    }

    /**
     * <p>
     * An 'if all else fails' method to reset zoom to its default value
     * </p>
     */
    public void defaultZoom() {
        scale = 1.0;
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public EditableImage getImage() {
        return image;
    }

    /**
     * <p>
     * Get the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * </p>
     * @return The current zoom level as a percentage.
     */
    public double getZoom() {
        return 100*scale;
    }

    /**
     * <p>
     * Sets the current zoom level according to the size of the current image
     * </p>
     * 
     * <p>
     * The method retrieves the current width and height of the image,
     * computes a ratio of these to the size of ANDIE's current window,
     * and adjusts the zoom accordingly so the image fills the window
     * </p>
     */
    public void setZoomToImageSize() {
        if (image.hasImage() && screenSizeOverride.width != 0) {
            double widthRatio = image.getCurrentImage().getWidth() / Andie.getFrameSize().getWidth();
            double heightRatio = image.getCurrentImage().getHeight() / Andie.getFrameSize().getWidth();
            scale = 1.0 / (Math.max(widthRatio, heightRatio));
        }
    }

    /**
     * <p>
     * Set the current zoom level as a percentage.
     * </p>
     * 
     * <p>
     * The percentage zoom is used for the external interface, where 100% is the original size, 50% is half-size, etc. 
     * The zoom level is restricted to the range [50, 200].
     * </p>
     * @param zoomPercent The new zoom level as a percentage.
     */
    public void setZoom(double zoomPercent) {
        if (!PencilButton.isDraw()) {
            if (zoomPercent < 50) {
                zoomPercent = 50;
            }
            if (zoomPercent > 200) {
                zoomPercent = 200;
            }
            scale = zoomPercent / 100;
        }
    }

    /**
     * <p>
     * Gets the preferred size of this component for UI layout.
     * </p>
     * 
     * <p>
     * The preferred size is the size of the image (scaled by zoom level), or a default size if no image is present.
     * </p>
     * 
     * @return The preferred size of this component.
     */
    @Override
    public Dimension getPreferredSize() {
        if (screenSizeOverride.width != 0) {
            return screenSizeOverride;
        }
        else if (image.hasImage() && !PencilButton.isDraw()) {
            return new Dimension((int)Math.round(image.getCurrentImage().getWidth()*scale), 
                                 (int)Math.round(image.getCurrentImage().getHeight()*scale));
        } else {
            // Sets the default Dimension to half the screen size
            // Adapted from https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            return new Dimension((int)Math.round(screenSize.getWidth() / 2.0), (int)Math.round(screenSize.getHeight() / 2.0));
        } 
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image.hasImage()) {
            this.setSize(getPreferredSize());
            Graphics2D g2  = (Graphics2D) g.create();
            g2.scale(scale, scale);
            g2.drawImage(image.getCurrentImage(), null, 0, 0);
            g2.dispose();
        }
    }
}
