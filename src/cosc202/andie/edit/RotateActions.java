package cosc202.andie.edit;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.image.*;
import cosc202.andie.lang.*;
import cosc202.andie.view.*;

 /**
 * <p>
 * Actions provided by the Rotate menu.
 * </p>
 * 
 * <p>
 * The Rotate menu provides commonly used methods for users to rotate images
 * Users can specify the degree and direction of rotation by
 * selecting a particular action from within the menu
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class RotateActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public RotateActions() {
        actions = new ArrayList<Action>();
        //string values call lang
        actions.add(new RotateClockwiseAction(lang.text("rotate90r"), null, lang.text("rotate90cw"), Integer.valueOf(KeyEvent.VK_R)));
        actions.add(new RotateAntiClockwiseAction(lang.text("rotate90l"), null, lang.text("rotate90acw"), Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new Rotate180Action(lang.text("rotate180"), null, lang.text("rotate180"), Integer.valueOf(KeyEvent.VK_D)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Rotate actions.
     * </p>
     * 
     * @return The rotate menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(lang.text("rotate"));

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
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
