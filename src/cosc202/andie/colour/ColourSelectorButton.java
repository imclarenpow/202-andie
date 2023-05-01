package cosc202.andie.colour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

/**
 * <p>
 * A class to represent a colour selection button for ANDIE
 * Uses a custom icon subclass to create a button to be held on ANDIE's JMenuBar
 * Sets the static drawingColour field for ANDIE which is used by drawing classes/functions
 * </p>
 * 
 * @author Niamh Avery
 * @version 1.0
 */
public class ColourSelectorButton {

    // Data fields
    private static Color drawingColour;
    private ColourIcon icon;

    /**
     * <p>
     * A constructor for a ColourSelectionButton object
     * Sets the default drawingColour to black
     * Creates a new ColourIcon - a custom implementation of Java's Icon interface
     * </p>
     */
    public ColourSelectorButton() {
        drawingColour = Color.black;
        icon = new ColourIcon(20, 20);
    }

    /**
     * <p>
     * Gets ANDIE's current drawingColour
     * </p>
     * 
     * @return the drawingColour used by drawing functions in ANDIE
     */
    public static Color getColour() {
        Color colour = drawingColour;
        return colour;
    }
    
    /**
     * Returns a JButton to represent the ColourSelectorButton
     * The JButton can be placed on ANDIE's JMenuBar
     * The button is assigned a custom listener
     * @return
     */
    public JButton createButton() {
        JButton button = new JButton(icon);
        button.addActionListener(new ColourWheelListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }


    /**
     * <p>
     * An implementation of Java's ActionListener interface to respond to clicks of the ColourSelectorButton
     * </p>
     */
    private class ColourWheelListener implements ActionListener {

        /**
         * <p>
         * Responds to clicks of the ColourSelectorButton
         * </p>
         * 
         * @param e the ActionEvent triggered by a click
         */
        public void actionPerformed(ActionEvent e) {
            // Prompts the user to select a colour
            drawingColour = JColorChooser.showDialog(null, "Select a colour", drawingColour);   
        }
    }

    /**
     * <p>
     * A custom icon to represent the 
     * A private class and custom implementation of the Icon interface
     * The colour of the icon equals the current DrawingColour used in ANDIE
     * </p>
     * 
     * Adapted from https://stackoverflow.com/questions/49795526/is-it-possible-to-create-an-imageicon-with-path2d-double 
     * 
     */
    private class ColourIcon implements Icon {
        private Rectangle rectangle;

        /**
         * <p>
         * A constructor for a new ColourIcon object
         * </p>
         * 
         * @param width the icon's width
         * @param height the icon's height
         */
        public ColourIcon(int width, int height) {
            rectangle = new Rectangle(width, height);
        }

        /** 
         * <p>
         * Paints a ColourIcon  
         * </p>
         * 
         * @param c a Component (may be null)
         * @param g the Graphics object to be used
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(drawingColour);
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            g2.fill(transform.createTransformedShape(rectangle));
        }
        
        /**
         * <p>
         * Gets the icon's width
         * </p>
         * 
         * @return the icon's width
         */
        public int getIconWidth() {
            return rectangle.width;
        }

        /**
         * <p>
         * Gets the icon's height
         * </p>
         * 
         * @return the icon's height
         */
        public int getIconHeight() {
            return rectangle.height;
        }
    }
}
