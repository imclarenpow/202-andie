package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.edit.EditActions;
import cosc202.andie.edit.EditActions.UndoAction;

/** 
 * <p>
 * A class that represents the eraser button that removes all pencil drawings that have been applied
 * The class has a method to create the button
 * It has a data field to hold the ImageIcon containing the image of the eraser
 * In addition, there is a private subclass that provides a custom ActionListener implementation to respond to button clicks
 * </p>
 * 
 * @author Niamh Avery
 * @version 1.0
 */
public class EraserButton {
    // Data fields 
    private ImageIcon icon;

    /**
     * <p>
     * A constructor for the EraserButton class
     * The constructor sets the icon of the EraserButton to an image of an eraser
     * </p>
     */
    public EraserButton() {
        icon = new ImageIcon("assets/eraser26.png"); // Retrieved from https://png.pngtree.com/png-clipart/20230105/original/pngtree-eraser-clipart-png-image_8875456.png (free to use) and modified with Microsoft Publisher + Microsoft Word
    }
    
    /**
     * <p>
     * Creates a new JButton to represent the eraser button
     * The eraser JButton is assigned an EraserListener - an implementation of ActionListener
     * The button is also formatted so that it is transparent etc.
     * </p>
     * 
     * @return a JButton representing the eraser button
     */
    public JButton createButton() {
        JButton eraserJButton = new JButton(icon);
        eraserJButton.addActionListener(new EraserListener());
        eraserJButton.setOpaque(false);
        eraserJButton.setContentAreaFilled(false);
        eraserJButton.setBorderPainted(false);

        return eraserJButton;
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the eraser button
     * </p>
     */
    private class EraserListener implements ActionListener {

        /**
         * <p>
         * Handles clicks of the eraser button
         * Creates a new instance of EditActions and its member class UndoAction
         * The undoDraw method of UndoAction is called which removes all drawings
         * Following this, drawing is reenabled for the user
         * </p>
         * 
         * @param e the ActionEvent triggering this method call (created by the click)
         */
        public void actionPerformed(ActionEvent e) {
            EditActions editActions = new EditActions();
            UndoAction eraserAction = editActions.new UndoAction(null, icon, null, null);
            eraserAction.undoDraw(e);
            
            // Prompts a pencilAction enabling the user to draw
            Andie.reenableDraw(e);
        }
    }
}