package cosc202.andie.image;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.Andie;

/** 
 * <p>
 * This class represents the PencilButton and its functionality, including calls to Pencil.java that allow the user to draw on an image
 * The PencilButton is placed on the menu bar 
 * Its colour is derived from the drawingColour set by the ColourSelectorButton
 * The PencilButton also determines whether ANDIE is currently in draw mode
 * It includes private classes for the PencilAction and a PencilListener to respond to button clicks
 * </p>
 * 
 * @author Niamh Avery
 * @version 1.0
 */
public class PencilButton {

    // Data fields
    private static boolean isDrawMode;
    private static Cursor defaultCursor;
    private static ImageIcon icon;
    private static JButton[] widthJButtons;
    private static Pencil pencil;
    
    /**
     * <p>
     * A constructor for a PencilButton
     * Ensures that DrawMode is initially disabled and the initial PencilButton icon is set correctly
     * The constructor also saves a copy of the default cursor for later use
     * </p>
     */
    public PencilButton() {
        isDrawMode = false;
        defaultCursor = Andie.getCursor();
        icon = new ImageIcon("assets/pencil.png"); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
        WidthButtons widthButtons = new WidthButtons();
        widthJButtons = widthButtons.createButtons();
    }
    
    /**
     * <p>
     * Creates a JButton representing the PencilButton to be used on ANDIE's JMenuBar
     * Includes a PencilListener object to respond to button clicks
     * </p>
     * 
     * @return a JButton representing the PencilButton
     */
    public JButton createButton() {
        JButton button = new JButton(icon);
        button.addActionListener(new PencilListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    /**
     * <p>
     * Disables ANDIE's draw mode
     * Sets the cursor and pencil icon back to their defaults
     * </p>
     */
    public static void disableDrawMode() {
        isDrawMode = false;
        pencil.stopListening();
        // Changes the cursor back to the default cursor
        Andie.setCursor(defaultCursor);  
        Andie.setPencilIcon(icon); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
        Andie.removeButtonsFromMenuBar(widthJButtons);
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the PencilButton
     * Handles cases where draw mode is enabled and disabled
     * </p>
     */
    private class PencilListener implements ActionListener {
        /**
         * <p>
         * Handles clicks of the PencilButton
         * </p>
         * 
         * @param e the ActionEvent created by the click
         */
        public void actionPerformed(ActionEvent e) {
            if (isDrawMode) {
                // Disables ANDIE's draw mode
                disableDrawMode();
            } else {
                // Enables draw mode, updates the cursor and pencil icon
                Andie.setPencilIcon(new ImageIcon("assets/exit.png", null)); // retrieved from https://icon-icons.com/icon/cancel-close-cross-delete-exit/114048 (free to use license)
                isDrawMode = true;
                
                // Code to change the cursor to a pencil
                // Adapted from https://stackoverflow.com/questions/4274606/how-to-change-cursor-icon-in-java
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Cursor newCursor = toolkit.createCustomCursor(new ImageIcon("assets/pencil.png").getImage(), new Point(0, 0), "pencil");
                Andie.setCursor(newCursor);

                // Adds buttons for setting pencil width to the menu bar
                Andie.addButtonsToMenuBar(widthJButtons);

                // Prompts a pencilAction enabling the user to draw
                PencilAction pencilAction = new PencilAction(null, icon, null, null);
                pencilAction.actionPerformed(e);
            }
        }
    }

    /**
     * <p>
     * An extension of ImageAction that enables the user to draw on an image
     * </p>
     */
    private class PencilAction extends ImageAction {
        
        /**
         * <p>
         * Create a new pencil action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        PencilAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the pencil action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the PencilAction is triggered.
         * It enables the user to draw on an image
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            pencil = new Pencil();
            pencil.setTarget(target);
            target.getImage().apply(pencil);
            target.repaint();
            target.getParent().revalidate();
        }
    }
}