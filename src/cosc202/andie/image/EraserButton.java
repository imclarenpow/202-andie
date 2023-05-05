package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.edit.EditActions;
import cosc202.andie.edit.EditActions.UndoAction;

/** 
 * <p>
 * A class that represents the buttons used to set the pencil width
 * The class has data fields to represent each of the small, medium and large buttons (implemented as JButtons)
 * The class also has a method to create the buttons
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
     * A constructor for the WidthButtons class
     * The constructor sets each of the three width buttons to a new JButton instance and gives each a new ImageIcon
     * The ImageIcon sets each icon to a blue circle of a size depending on the button
     * The circles were originally created in Microsoft Word
     * </p>
     */
    public EraserButton() {
        icon = new ImageIcon("assets/eraser.png");
    }
    
    /**
     * <p>
     * Creates an array of JButtons to represent the width buttons
     * Each JButton is assigned an action listener
     * The buttons are also formatted so that they are transparent etc.
     * </p>
     * 
     * @return an array of JButtons representing the width buttons
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
     * An implementation of the ActionListener interface that responds to clicks of the width buttons
     * </p>
     */
    private class EraserListener implements ActionListener {

        /**
         * <p>
         * Handles clicks of the width buttons
         * The static Pencil.width field is set here depending on which of the three width buttons was clicked
         * </p>
         * 
         * @param e the ActionEvent created by the click
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