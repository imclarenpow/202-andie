package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.awt.*;
import cosc202.andie.ImagePanel;

/**
 * <p>
 * A class that uses JUnit 5 to test the method's of Andie's ImagePanel classgiche
 * </p>
 * @author Niamh Avery
 * @version 1.0
 */
public class ImagePanelTest {

    /**
     * <p>
     * A dummy test to ensure JUnit is working as intended
     * </p>
     */
    @Test
    void initialDummyTest() {
    }

    /**
     * <p>
     * Checks that the default zoom value of an instance of the ImagePanel class is equal to the intended value
     * </p>
     */
    @Test
    void getZoomInitialValue() {
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }

    /**
     * <p>
     * Checks that the zoom value is updated to the minimum value when it is set to a value below the minimum
     * </p>
     */
    @Test
    void getZoomAfterSetZoom() {
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(0.0);
        Assertions.assertFalse(testPanel.getZoom() == 100.0);
        Assertions.assertTrue(testPanel.getZoom() >= 50.0);
    }

    /**
     * <p>
     * Checks the image parameter of the ImagePanel class is correctly initialised 
     * </p>
     */
    @Test
    void checkImageExists() {
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertNotNull(testPanel.getImage());
    }

    /** 
     * <p>
     * Checks the default size of the test panel is set correctly
     * </p>
     */
    @Test 
    void checkDefaultSize() {
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(testPanel.getPreferredSize(), new Dimension(450, 450));
    }

}
