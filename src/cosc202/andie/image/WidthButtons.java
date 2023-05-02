package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.image.Pencil.WidthEnum;

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
public class WidthButtons {
    // Data fields 
    private static JButton smallButton;
    private static JButton mediumButton;
    private static JButton largeButton;

    /**
     * <p>
     * A constructor for the WidthButtons class
     * The constructor sets each of the three width buttons to a new JButton instance and gives each a new ImageIcon
     * The ImageIcon sets each icon to a blue circle of a size depending on the button
     * The circles were originally created in Microsoft Word
     * </p>
     */
    public WidthButtons() {
        smallButton = new JButton(new ImageIcon("assets/smallWidth.png"));
        mediumButton = new JButton(new ImageIcon("assets/mediumWidth.png"));
        largeButton = new JButton(new ImageIcon("assets/largeWidth.png"));
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
    public JButton[] createButtons() {
        JButton[] widthButtons = {smallButton, mediumButton, largeButton};

        for (JButton button : widthButtons) {
            button.addActionListener(new WidthListener());

            // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
        }

        return widthButtons;
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the width buttons
     * </p>
     */
    private class WidthListener implements ActionListener {

        /**
         * <p>
         * Handles clicks of the width buttons
         * The static Pencil.width field is set here depending on which of the three width buttons was clicked
         * </p>
         * 
         * @param e the ActionEvent created by the click
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == smallButton) {
                Pencil.setWidth(WidthEnum.SMALL);
            } else if (e.getSource() == mediumButton) {
                Pencil.setWidth(WidthEnum.MEDIUM);
            } else if (e.getSource() == largeButton) {
                Pencil.setWidth(WidthEnum.LARGE);
            }
        }
    }
}