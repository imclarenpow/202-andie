package cosc202.andie.image;

import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Shapes Menu, which is a submenu of the SelectButton
 * <p>
 * A class that creates the Shapes Menu and the actions that it contains
 * The class has a method to create the menu
 * It has a data field to hold the ImageIcon containing the image of the shapes menu, both solid and grayed
 * Also, there is a datafield to hold the SelectButton that this ShapesMenu is associated with
 * In addition, there is a private subclass that provides a custom ActionListener implementation to respond to button clicks
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class ShapesActions {
    //Data fields 
    private Color drawingColour;
    private boolean selectionApplied = false;
    private static SelectButton selectButton;
    private static Select select;
    protected ArrayList<Action> actions; 
    private static JMenu shapesMenu;
    private final ImageIcon ICON = new ImageIcon("assets/shapesicon.png"); // retrieved from https://www.pngfind.com/mpng/ibTmJxT_shapes-clipart-png-transparent-png/ (free to use license)
    private final ImageIcon GRAYEDICON = new ImageIcon("assets/grayscaleshapesicon.png");

    /**
     * <p>
     * A constructor for the ShapesMenu class
     * The constructor creates the list of actions that the menu will contain, and adds them to the list
     * These are the Shapes that will be drawn on a selected area when the user clicks on the menu item
     * </p> 
     * 
     * @param selectButton the SelectButton that this ShapesMenu is associated with
     */
    public ShapesActions(SelectButton selectButton) {
        this.selectButton = selectButton;
        this.select = selectButton.getSelect();
        actions = new ArrayList<Action>();
        
        actions.add(new RectangleAction("Rectangle", null, null, null));
        actions.add(new EllipseAction("Ellipse", null, null, null));
        actions.add(new LineAction("Line", null, null, null));
    }

    /**
     * <p>
     * Creates and returns the shapes menu, adding each action to the menu that was created in the constructor
     * By default this is with a grayed icon, as the user must first select an area to apply a shape to
     * </p>
     * @return the shapes menu
     */
    public JMenu createMenu(){
        shapesMenu = new JMenu();
        shapesMenu.setIcon(GRAYEDICON);

        for(Action action: actions){
            shapesMenu.add(action);
        }
        return shapesMenu;
    }

    /** 
     * <p>
     * A method that sets the shapes menu to have a solid icon
     * </p>
    */
    public void startListening() {
        shapesMenu.setIcon(ICON);
    }

    /**
     * <p>
     * A method that sets the shapes menu to have a grayed icon
     * </p>
     */
    public void stopListening() {
        shapesMenu.setIcon(GRAYEDICON);
    }

    /**
     * <p>
     * Action to draw a rectangle on an image, with the user selecting color and fill
     * </p>
     * 
     * @see Rectangle
     */
    public class RectangleAction extends ImageAction {

        /**
         * <p>
         * Creates a new Rectangle action
         * </p>
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        RectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the rectangle action is triggered
         * </p>
         * 
         * <p>
         * Performs the Rectangle action by asking the user for input on whether they want the shape filled, and what colour they want it to be
         * Then, it creates a new Rectangle object with the user's input, and applies it to the image
         * This uses coordinates from the area selection and also reverts the selection, and disables the select mode
         * Handles the case where the user has not selected an area to apply a shape to, displaying an error message
         * </p> 
         * 
         * @param e the event that triggered the action
         */
        public void actionPerformed(ActionEvent e) {
            if(select.isSelectionApplied()){
                int filled = JOptionPane.showConfirmDialog(null, "Do you want your shape filled?", "Shape Fill", JOptionPane.YES_NO_OPTION);
                drawingColour = JColorChooser.showDialog(null, "Select a colour", drawingColour);
                Rectangle rectangle = new Rectangle(select.getStartPoint(), select.getEndPoint(), drawingColour);
                rectangle.setFilled(filled);
                select.revert();
                target.getImage().apply(rectangle);
                target.repaint();
                target.getParent().revalidate();
                stopListening();
                selectButton.disableSelectMode(); 
            }else{
                JOptionPane.showMessageDialog(null, "You must first select an area to apply a shape to", "No Selection", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    /**
     * <p>
     * Action to draw an ellipse on an image, with the user selecting color and fill
     * </p>
     * 
     * @see Ellipse
     */
    public class EllipseAction extends ImageAction {
        /**
         * <p>
         * Creates a new Ellipse action
         * </p>
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        EllipseAction(String name, ImageIcon icon, String desc, Integer mnemonic){
           super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the ellipse action is triggered
         * </p>
         * 
         * <p>
         * Performs the Ellipse action by asking the user for input on whether they want the shape filled, and what colour they want it to be
         * Then, it creates a new Ellipse object with the user's input, and applies it to the image
         * This uses coordinates from the area selection and also reverts the selection, and disables the select mode
         * Handles the case where the user has not selected an area to apply a shape to, displaying an error message
         * </p>
         * 
         * @param e the event that triggered the action
         */
        public void actionPerformed(ActionEvent e) {
            if(select.isSelectionApplied()){
                int filled = JOptionPane.showConfirmDialog(null, "Do you want your shape filled?", "Shape Fill", JOptionPane.YES_NO_OPTION);
                drawingColour = JColorChooser.showDialog(null, "Select a colour", drawingColour);
                Ellipse ellipse = new Ellipse(select.getStartPoint(), select.getEndPoint(), drawingColour);
                ellipse.setFilled(filled);
                select.revert();
                target.getImage().apply(ellipse);
                target.repaint();
                target.getParent().revalidate();
                stopListening();
                selectButton.disableSelectMode();
            }else{
                JOptionPane.showMessageDialog(null, "You must first select an area to apply a shape to", "No Selection", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to draw a line on an image, with the user selecting color
     * </p>
     * 
     * @see Line
     */
    public class LineAction extends ImageAction {
        /**
         * <p>
         * Creates a new Line action
         * </p>
         * 
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        public LineAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the line action is triggered
         * </p>
         * 
         * <p>
         * Performs the Line action by asking the user for input on what colour they want it to be
         * Then, it creates a new Line object with the user's input, and applies it to the image
         * This uses coordinates from the area selection and also reverts the selection, and disables the select mode
         * Handles the case where the user has not selected an area to apply a shape to, displaying an error message
         * </p>
         * 
         * @param e the event that triggered the action
         */
        public void actionPerformed(ActionEvent e) {
            if(select.isSelectionApplied()){
                drawingColour = JColorChooser.showDialog(null, "Select a colour", drawingColour);
                Line line = new Line(select.getStartPoint(), select.getEndPoint(), drawingColour);
                select.revert();
                target.getImage().apply(line);
                target.repaint();
                target.getParent().revalidate();
                selectButton.disableSelectMode(); 
            }else{
                JOptionPane.showMessageDialog(null, "You must first select an area to apply a shape to", "No Selection", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
