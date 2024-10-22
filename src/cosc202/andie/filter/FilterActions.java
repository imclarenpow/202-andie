package cosc202.andie.filter;

import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.awt.Graphics2D;
import java.awt.GridLayout;
//import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import cosc202.andie.EditableImage;
import cosc202.andie.image.*;
import cosc202.andie.lang.*;
//import cosc202.andie.EditableImage.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
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
        // string values call lang
        actions = new ArrayList<Action>();

        actions.add(new SharpenFilterAction(lang.text("sharpenfilter"), null, lang.text("applysharpen"),
                Integer.valueOf(KeyEvent.VK_P)));
        actions.add(new MedianFilterAction(lang.text("medianfilter"), null, lang.text("applymedian"),
                Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new MeanFilterAction(lang.text("meanfilter"), null, lang.text("applymean"),
                Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SoftBlurAction(lang.text("softblur"), null, lang.text("applysoftblur"),
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new GaussianFilterAction(lang.text("gaussian"), null, lang.text("applygaussian"),
                Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new EmbossFilterAction(lang.text("embossfilter"), null, lang.text("applyemboss"),
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new SobelFilterAction(lang.text("sobelfilter"), null, lang.text("applysobel"),
                Integer.valueOf(KeyEvent.VK_X)));

    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(lang.text("filter"));

        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * @author Isaac
     *         Working, uses a spinner, want to update to use a slider when applying
     *         polish
     */
    public class GaussianFilterAction extends ImageAction {
        GaussianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            // Default rad of 1.
            int radius = 1;
            int offset = 0;

            // Create a slider to select the radius value (tony hawk is a rad slider)
            JSlider tonyHawk = new JSlider(0, 25, 1);
            tonyHawk.setMajorTickSpacing(5);
            tonyHawk.setMinorTickSpacing(1);
            tonyHawk.setPaintTicks(true);
            tonyHawk.setPaintLabels(true);

            int option = 10;
            String[] options = {lang.text("ok"), lang.text("preview"), lang.text("cancel")};
            ImageIcon previewIcon = getPreviewImage(target.getImage(), -1, "sobel");

            while (option != JOptionPane.OK_OPTION) {
                // Pop-up dialog box to ask for the radius value.
                option = JOptionPane.showOptionDialog(null, tonyHawk, lang.text("enterfiltrad"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, previewIcon, options, options[2]);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else {
                    radius = tonyHawk.getValue();
                    previewIcon = getPreviewImage(target.getImage(), radius, "gaussian");
                }
            }

            // Create and apply the filter
            target.getImage().apply(new GaussianBlur(radius, offset));
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
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            // Determine the radius - ask the user.
            int radius = 1;
            int offset = 0;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = 10;
            ImageIcon previewIcon = getPreviewImage(target.getImage(), -1, "emboss"); // Gets the image with no filter applied
            String[] options = {lang.text("ok"), lang.text("preview"), lang.text("cancel")};

            while (option != JOptionPane.OK_OPTION) {
                option = JOptionPane.showOptionDialog(null, radiusSpinner, lang.text("enterfiltrad"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, previewIcon, options, options[2]);
                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else {
                    radius = radiusModel.getNumber().intValue();
                    previewIcon = getPreviewImage(target.getImage(), radius, "mean");
                }
            }

            // Create and apply the filter
            target.getImage().apply(new MeanFilter(radius, offset));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new sharpen-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
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
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            boolean success = target.getImage().apply(new SharpenFilter());
            target.repaint();
            target.getParent().revalidate();
            if (success) {
                JOptionPane.showMessageDialog(null, lang.text("sharpfiltrun"));
            }
        }
    }

    /**
     * <p>
     * Action to blur an image with a median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new median-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the median filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            // Determine the radius - ask the user.
            int radius = 1;

            // Create a panel to hold the slider
            JPanel panel = new JPanel();
            JSlider medianSlider = new JSlider(0, 10, 0);
            medianSlider.setMajorTickSpacing(5);
            medianSlider.setMinorTickSpacing(1);
            medianSlider.setPaintTicks(true);
            medianSlider.setPaintLabels(true);
            panel.add(medianSlider);

            Object[] message = { lang.text("enterfiltrad"), panel };
            int option = 10;
            String[] options = {lang.text("ok"), lang.text("preview"), lang.text("cancel")};
            ImageIcon previewIcon = getPreviewImage(target.getImage(), -1, "emboss"); // Gets the image with no filter applied
            while (option != JOptionPane.OK_OPTION) {
                option = JOptionPane.showOptionDialog(null, message, lang.text("medianfilter"),
                        1, JOptionPane.YES_NO_CANCEL_OPTION, previewIcon, options, options[2]);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else {
                    radius = medianSlider.getValue();
                }
                previewIcon = getPreviewImage(target.getImage(), radius, "median");
            }


            // Create and apply the filter
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to blur an image with a soft blur filter.
     * </p>
     * 
     * @see SoftBlur
     */
    public class SoftBlurAction extends ImageAction {
        /**
         * <p>
         * Create a new soft-blur action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurAction(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the soft blur filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SoftBlur is triggered.
         * It applies a soft blur filter to the current image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            boolean success = target.getImage().apply(new SoftBlur());
            target.repaint();
            target.getParent().revalidate();
            if (success) {
                JOptionPane.showMessageDialog(null, lang.text("softblurrun"));
            }
        }
    }

    /**
     * <p>
     * Action to apply an emboss effect to an image with an emboss filter.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new emboss-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Whenever the Emboss-Filter is activated, this method is called.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            int filterIndex = 1;
            String[] filters = {"0","1", "2", "3", "4", "5", "6", "7", "8" };

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            // panel.add(new JLabel("enterfiltrad"));
            JComboBox<String> comboBox = new JComboBox<>(filters);
            panel.add(comboBox);

            Object[] message = { lang.text("enterembossmode"), panel };
            ImageIcon previewIcon = getPreviewImage(target.getImage(), -1, "emboss");
            String[] options = {lang.text("ok"), lang.text("preview"), lang.text("cancel")};
            int option = 5;
            while (option != JOptionPane.OK_OPTION) {
                option = JOptionPane.showOptionDialog(null, message, lang.text("embossfilter"), 1,
                JOptionPane.YES_NO_CANCEL_OPTION, previewIcon, options, options[2]);

                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else {
                    String selectedItem = (String) comboBox.getSelectedItem();
                    filterIndex = Integer.parseInt(selectedItem) - 1; // Adjust the index
                }
                previewIcon = getPreviewImage(target.getImage(), filterIndex, "emboss");
            }
                target.getImage().apply(new EmbossFilter(filterIndex));
                target.repaint();
                target.getParent().revalidate();
                
                if (target.getImage().hasImage()) {
                    JOptionPane.showMessageDialog(null, lang.text("embossfiltrun"));
                }
        }
    }

    /**
         * <p>
         * Generates an image icon for previewing the effects of applying various filters
         * </p>
         * 
         * @param image the image for which a preview is to be generated
         * @param filterindex the index of the specific filter mode to be applied (-1 for no filter)
         * @param mode the overarching filter type (emboss or sobel)
         */
        private ImageIcon getPreviewImage(EditableImage image, int filterIndex, String mode) {
            ImageIcon resultIcon = null;
            if (image.getCurrentImage() != null) {
                if (filterIndex != -1) {
                    if (mode.equals("emboss")) {
                        image.apply(new EmbossFilter(filterIndex));
                    } else if (mode.equals("mean")) {
                        image.apply(new MeanFilter(filterIndex, 0));
                    } else if (mode.equals("median")) {
                        image.apply(new MedianFilter(filterIndex));
                    } else if (mode.equals("gaussian")) {
                        image.apply(new GaussianBlur(filterIndex, 0));
                    } else { // assumes a sobel filter
                        if (filterIndex == 0) {
                            image.apply(new SobelFilter("vertical"));
                        } else {
                            image.apply(new SobelFilter("horizontal"));
                        }
                    }
                }
                
                // Resizes the current image to match the desired preview image size
                BufferedImage previewImage = image.getCurrentImage();
                int width = previewImage.getWidth();
                int height = previewImage.getHeight();
                double ratio = (double)height / (double)width;
                width = 400; // Desired preview size
                height  = (int)((double)width * ratio);
                
                // Draws the resized image onto a new BufferedImage
                // Adapted from http://underpop.online.fr/j/java/help/java-converting-an-image-to-a-bufferedimage.html.gz
                BufferedImage result = new BufferedImage(width, height, previewImage.getType());
                Graphics2D g2 = result.createGraphics();
                g2.drawImage(previewImage.getScaledInstance(width, height, 0), 0, 0, null);
                resultIcon = new ImageIcon(result);
                if (filterIndex != -1) {
                    image.undo(); // Undoes the change to ensure the actual image isn't modified
                }   
            }
            
            return resultIcon;
        }

    /**
     * <p>
     * Action to apply Sobel filter to an image.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SobelFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new Sobel-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Whenever the Sobel-Filter is activated, this method is called.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            if (PencilButton.isDraw()) {
                PencilButton.disableDrawMode();
            }

            String[] filters = { lang.text("horizontal"), lang.text("vertical") };
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            JComboBox<String> comboBox = new JComboBox<>(filters);
            panel.add(comboBox);
            ImageIcon previewIcon = getPreviewImage(target.getImage(), -1, "sobel");
            Object[] message = { lang.text("entersobelmode"), panel };
            int option = 10;
            String[] options = {lang.text("ok"), lang.text("preview"), lang.text("cancel")};
            while (option != JOptionPane.OK_OPTION && option != JOptionPane.CANCEL_OPTION) {
                option = JOptionPane.showOptionDialog(null, message, lang.text("sobelfilter"), 1, 
                JOptionPane.YES_NO_CANCEL_OPTION, previewIcon, options, options[2]);
                String selectedItem = (String) comboBox.getSelectedItem();
                if (option == JOptionPane.OK_OPTION) {
                    selectedItem = (String) comboBox.getSelectedItem();
                    SobelFilter sobelFilter = new SobelFilter(selectedItem);
                    // Apply the Sobel filter and repaint the image
                    target.getImage().apply(sobelFilter);
                    target.repaint();
                    target.getParent().revalidate();
                    if (target.getImage().hasImage()) {
                        JOptionPane.showMessageDialog(null, lang.text("sobelfiltrun"));
                    }
                }
                
                if (selectedItem.equals(lang.text("horizontal"))) {
                    previewIcon = getPreviewImage(target.getImage(), 1, "sobel");
                } else {
                    previewIcon = getPreviewImage(target.getImage(), 0, "sobel");
                }
            }  
        }

    }
}