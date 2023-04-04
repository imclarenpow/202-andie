package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import cosc202.andie.lang.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ColourActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
   
    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        //note that all the strings call the lang instance
        actions.add(new ConvertToGreyAction(lang.text("greyscale"), null, lang.text("convtogray"), Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAndContrastAction(lang.text("brightnessandcontrast"), null, lang.text("applybrightnessandcontrast"), Integer.valueOf(KeyEvent.VK_B)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        // again calling the lang instance
        JMenu fileMenu = new JMenu(lang.text("colour"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * Action to adjust an image's brightness and contrast
     * 
     * @author Nic Scott - with assistance from ChatGPT for JSlider formatting
     * @see BrightnessAndContrast
     */
    public class BrightnessAndContrastAction extends ImageAction {

        /**
         * Create a new brightness-and-contrast action.
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        BrightnessAndContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p> 
         * Callback for when the BrightnessAndContrast action is triggered
         * </p>
         * 
         * <p>
         * Performs the BrightnessAndContrast action by creating sliders for brightness and contrast, 
         * displaying a message in an option dialog, getting the user's response, 
         * and adjusting the brightness and contrast of the image accordingly.
         * </p>
         *
         * @param e The event triggering this callback
         */
        public void actionPerformed(ActionEvent e) {
            int brightness = 0;
            int contrast = 0;
            // Create the sliders for brightness and contrast
            JSlider brightnessSlider = new JSlider(-100, 100, 0);
            JSlider contrastSlider = new JSlider(-100, 100, 0);

            // Configure the sliders
            brightnessSlider.setMajorTickSpacing(50);
            brightnessSlider.setMinorTickSpacing(10);
            brightnessSlider.setPaintTicks(true);
            brightnessSlider.setPaintLabels(true);

            contrastSlider.setMajorTickSpacing(50);
            contrastSlider.setMinorTickSpacing(10);
            contrastSlider.setPaintTicks(true);
            contrastSlider.setPaintLabels(true);

            // Create the message to display in the option dialog
            Object[] message = {
                    lang.text("brightnesslabel"), brightnessSlider,
                    lang.text("contrastlabel"), contrastSlider
            };

            // Show the option dialog and get the user's response
            int option = JOptionPane.showConfirmDialog(null, message, lang.text("dispbrightcont"), JOptionPane.OK_CANCEL_OPTION);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightness = brightnessSlider.getValue();
                contrast = contrastSlider.getValue();
            }

            target.getImage().apply(new BrightnessAndContrast(brightness, contrast));
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
