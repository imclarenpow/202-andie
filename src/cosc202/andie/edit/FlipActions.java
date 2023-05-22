package cosc202.andie.edit;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.image.*;
import cosc202.andie.lang.*;

 /**
 * <p>
 * Actions provided by the Flip menu.
 * </p>
 * 
 * <p>
 * The Flip menu provides commonly used methods for mirroring images
 * Users can use it to 'mirror' images across a horizontal or vertical axis
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FlipActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public FlipActions() {
        actions = new ArrayList<Action>();
        //string values call lang
        actions.add(new FlipHorizontalAction(lang.text("fliphoriz"), null, lang.text("fliphoriz"), Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new FlipVerticalAction(lang.text("flipvert"), null, lang.text("flipvert"), Integer.valueOf(KeyEvent.VK_V)));
   }

    /**
     * <p>
     * Create a menu contianing the list of Flip actions.
     * </p>
     * 
     * @return The flip menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu(lang.text("flip"));

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
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
}
