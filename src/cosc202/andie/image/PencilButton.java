package cosc202.andie.image;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.ImagePanel;
import cosc202.andie.filter.FilterActions;

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
    private static JButton eraserJButton;
    private PencilListener listener;

    /** 
     * <p>
     * Checks if ANDIE is in draw mode
     * </p>
     * 
     * @return true if ANDIE is in draw mode
     */
    public static boolean isDraw() {
        return isDrawMode;
    }
    
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
        EraserButton eraserButton = new EraserButton();
        eraserJButton = eraserButton.createButton();
    }

    /**
     * Enables ANDIE's draw mode by instantiating a new PencilListener if not already present 
     * and then enabling the PencilListener
     * @param e the ActionEvent triggering this method call
     */
    public void enableDraw(ActionEvent e) {
        if (listener == null) {
            listener = new PencilListener();
        }
        listener.reenableListener(e);
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
        Pencil.setListening(false);
        // Changes the cursor back to the default cursor
        Andie.setCursor(defaultCursor);  
        Andie.setPencilIcon(icon); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
        Andie.removeButtonsFromMenuBar(widthJButtons);
        Andie.removeButtonFromMenuBar(eraserJButton);
        ImagePanel.screenSizeOverride = new Dimension(0, 0); // removes the screen size override
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the PencilButton
     * Handles cases where draw mode is enabled and disabled
     * </p>
     */
    private class PencilListener implements ActionListener {
        private PencilAction pencilAction;

        public boolean reenableListener(ActionEvent e) {
            boolean result = true;
            // Code to change the cursor to a pencil
            // Adapted from https://stackoverflow.com/questions/4274606/how-to-change-cursor-icon-in-java
            /*if (pencilAction != null) {
                disableDrawMode();
                pencilAction.stopListening();
            }*/

            // Prompts a pencilAction enabling the user to draw
            this.pencilAction = new PencilAction(null, icon, null, null);
            if (pencilAction.hasImageToDraw()) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Cursor newCursor = toolkit.createCustomCursor(new ImageIcon("assets/pencil.png").getImage(), new Point(0, 0), "pencil");
                Andie.setCursor(newCursor);
            } else {
                result = false;
            }
            pencilAction.actionPerformed(e);
            return result;
        }

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
                Pencil.setListening(true);

                // Enables draw mode, updates the cursor and pencil icon
                Andie.setPencilIcon(new ImageIcon("assets/exit26.png", null)); // retrieved from https://icon-icons.com/icon/cancel-close-cross-delete-exit/114048 (free to use license)
                isDrawMode = true;
                boolean enabled = reenableListener(e);

                if (enabled) {
                    // Adds buttons for setting pencil width to the menu bar
                    Andie.addButtonsToMenuBar(widthJButtons);

                    // Adds the eraser button to the menu bar
                    Andie.addButtonToMenuBar(eraserJButton);
                }
            }
        }
    }

    /**
     * <p>
     * An extension of ImageAction that enables the user to draw on an image
     * </p>
     */
    private class PencilAction extends ImageAction {
        private Pencil pencil;
        
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

        public boolean hasImageToDraw() {
            return target.getImage().hasImage();
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
            if (target.getImage().hasImage()) {
                pencil = new Pencil();
                pencil.setTarget(target);
                target.defaultZoom();
                target.repaint();

                Dimension currentScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
                ImagePanel.screenSizeOverride = new Dimension((int)Math.round(currentScreenSize.getWidth() / 1.2), (int)Math.round(currentScreenSize.getHeight() / 1.2));
                
                target.repaint();
                target.getImage().apply(pencil);
                target.repaint();
                target.getParent().revalidate();
            } else {
                target.getImage().ShowNoImageError();
            }
           
        }
    }
}