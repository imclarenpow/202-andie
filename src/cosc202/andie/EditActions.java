package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.lang.LanguageSupport;

 /**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class EditActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    // Sets the maximum dimension of images for resize 
    private final double MAX_DIMENSION_LIMIT = 20000;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        //string values call lang
        actions = new ArrayList<Action>();
        actions.add(new UndoAction(lang.text("undo"), null, lang.text("undo"), Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction(lang.text("redo"), null, lang.text("redo"), Integer.valueOf(KeyEvent.VK_Y)));
        actions.add(new ResizeAction(lang.text("resize"), null, lang.text("resize"), Integer.valueOf(KeyEvent.VK_R)));

        actions.add(new FlipHorizontalAction(lang.text("fliphoriz"), null, lang.text("fliphoriz"), Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new FlipVerticalAction(lang.text("flipvert"), null, lang.text("flipvert"), Integer.valueOf(KeyEvent.VK_V)));

        actions.add(new RotateClockwiseAction(lang.text("rotate90r"), null, lang.text("rotate90cw"), Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new RotateAntiClockwiseAction(lang.text("rotate90l"), null, lang.text("rotate90acw"), Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new Rotate180Action(lang.text("rotate180"), null, lang.text("rotate180"), Integer.valueOf(KeyEvent.VK_D)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(lang.text("edit"));

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

     /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */   
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Action to resize an image according to a scale retrieved from the user.
     * </p>
     * 
     * @see Resize
     * @author Niamh Avery
     * @version 1.0
     */
    public class ResizeAction extends ImageAction {

        /**
         * <p>
         * Creates a resize action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        
        /**
         * <p>
         * Callback for when the resize action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeAction is triggered.
         * It obtains a scale from the user via a pop-up box and
         * it then resizes the currently displayed image according
         * to this scale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */

         
        public void actionPerformed(ActionEvent e) {
            // Determine the current width and height.
            double scale = 0;

            // Finds the minimum and maximum scales for resizing
            double largestDimension = Math.max(target.getImage().getCurrentImage().getWidth(), target.getImage().getCurrentImage().getHeight());
            double smallestDimension = Math.min(target.getImage().getCurrentImage().getWidth(), target.getImage().getCurrentImage().getHeight());
            double minScale = Math.ceil(100 / smallestDimension + 0.01) / 100; // ensures image dimensions will not fall below 0
            double maxScale = Math.floor(100 * MAX_DIMENSION_LIMIT / largestDimension) / 100;
            
            // Pop-up dialog box to ask the user for the new width and height values.
            SpinnerNumberModel scaleModel = new SpinnerNumberModel(1, minScale, maxScale, 0.01);
            JSpinner scaleSpinner = new JSpinner(scaleModel);
            String optionDialogText = lang.text("enterscale") + ": " + minScale + " -> " + maxScale;
            int option = JOptionPane.showOptionDialog(null, scaleSpinner, optionDialogText, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Checks the return value from the dialog box
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                scale = scaleModel.getNumber().doubleValue();
                if (scale == 1 || scale < minScale || scale > maxScale) {
                    // Warning dialog for when no changes have been made
                    JOptionPane.showMessageDialog(null, lang.text("resizescalewarning"),
                    lang.text("invalidscale"),
                    JOptionPane.WARNING_MESSAGE);
                } else {
                    target.getImage().apply(new Resize(scale));
                    target.repaint();
                    target.getParent().revalidate();
                }
            }
            
        }
    }

    /**
     * <p>
     * Action to flip the input image horizontally.
     * </p>
     * 
     * @see Flip
     */  
    public class FlipHorizontalAction extends ImageAction{
        /**
         * <p>
         * Create a flip horizontal action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipHorizontalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Flip Horizontal action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipHorizontalAction is triggered.
         * It flips the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new Flip("Horizontal"));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to flip the input image Vertically.
     * </p>
     * 
     * @see Flip
     */  
    public class FlipVerticalAction extends ImageAction{
        /**
         * <p>
         * Create a flip Vertically action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FlipVerticalAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**
         * <p>
         * Callback for when the flip vertical action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FlipVerticalAction is triggered.
         * It flips the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new Flip("Vertical"));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to rotate the input image 90 degrees right/clockwise.
     * </p>
     * 
     * @see Rotate
     */  
    public class RotateClockwiseAction extends ImageAction{
        /**
         * <p>
         * Create a rotate 90 degrees clockwise action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateClockwiseAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**
         * <p>
         * Callback for when the rotate 90 degrees clockwise action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateClockwiseAction is triggered.
         * It flips the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new Rotate("ClockwiseNinety"));
            target.repaint();
            target.getParent().revalidate();
        }

    }
    /**
     * <p>
     * Action to rotate the input image 90 degrees left/anti-clockwise.
     * </p>
     * 
     * @see Rotate
     */
    public class RotateAntiClockwiseAction extends ImageAction{
        /**
         * <p>
         * Create a rotate anti-clockwise action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RotateAntiClockwiseAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**
         * <p>
         * Callback for when the rotate 90 degrees anti-clocwise action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RotateAntiClockwiseAction is triggered.
         * It flips the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new Rotate("AntiClockwiseNinety"));
            target.repaint();
            target.getParent().revalidate();
        }

    }
    /**
     * <p>
     * Action to rotate the input image 180 degrees.
     * </p>
     * 
     * @see Rotate
     */
    public class Rotate180Action extends ImageAction{
        /**
         * <p>
         * Create a rotate 180 degrees action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        Rotate180Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }
        /**
         * <p>
         * Callback for when the rotate 180 degrees action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the Rotate180Action is triggered.
         * It flips the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new Rotate("HundredEighty"));
            target.repaint();
            target.getParent().revalidate();
        }

    }
}
