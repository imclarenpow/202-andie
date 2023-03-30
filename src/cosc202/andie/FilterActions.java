package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.lang.LanguageSupport;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood. 
 * This includes a mean filter (a simple blur) in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FilterActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /** A list of actions for the Filter menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        //string values call lang
        actions = new ArrayList<Action>();
        
        actions.add(new SharpenFilterAction(lang.text("sharpenfilter"), null, lang.text("applysharpen"), Integer.valueOf(KeyEvent.VK_P)));
        actions.add(new MedianFilterAction(lang.text("medianfilter"), null, lang.text("applymedian"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new MeanFilterAction(lang.text("meanfilter"), null, lang.text("applymean"), Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SoftBlurAction(lang.text("softblur"), null, lang.text("applysoftblur"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new GaussianFilterAction(lang.text("gaussian"), null, lang.text("applygaussian"), Integer.valueOf(KeyEvent.VK_G)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(lang.text("filter"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

/**
 * @author Isaac
 * Working, uses a spinner, want to update to use a slider when applying polish
 */
public class GaussianFilterAction extends ImageAction{
    GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic){
        super(name, icon, desc, mnemonic);
    }
    public void actionPerformed(ActionEvent e) {

        // Default rad of 1.
        int radius = 1;
    
        // Create a slider to select the radius value (tony hawk is a rad slider)
        JSlider tonyHawk = new JSlider(0, 25, 1);
        tonyHawk.setMajorTickSpacing(5);
        tonyHawk.setMinorTickSpacing(1);
        tonyHawk.setPaintTicks(true);
        tonyHawk.setPaintLabels(true);
    
        // Pop-up dialog box to ask for the radius value.
        int option = JOptionPane.showOptionDialog(null, tonyHawk, lang.text("enterfiltrad"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    
        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (option == JOptionPane.OK_OPTION) {
            radius = tonyHawk.getValue();
        }
    
        // Create and apply the filter
        target.getImage().apply(new GaussianBlur(radius));
        target.repaint();
        target.getParent().revalidate();
    }
}
    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, lang.text("enterfiltrad"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Creates a new action for the sharpen-filter.
         * </p>
         */
        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Whenever the Sharpnes-Filter is activated, this method is called.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {

            target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
            JOptionPane.showMessageDialog(null, lang.text("sharpfiltrun"));
        }

    }

    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized {@link SharpenFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            //Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, lang.text("enterfiltrad"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            // Create and apply the filter
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    public class SoftBlurAction extends ImageAction {

            SoftBlurAction(String name, ImageIcon icon, 
                            String desc, Integer mnemonic) {
                super(name, icon, desc, mnemonic);
            }

            public void actionPerformed(ActionEvent e) {
                target.getImage().apply(new SoftBlur());
                target.repaint();
                target.getParent().revalidate();
                JOptionPane.showMessageDialog(null, lang.text("softblurrun"));
            }
        }
}
