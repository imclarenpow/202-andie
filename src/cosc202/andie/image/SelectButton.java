package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

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
public class SelectButton {

    // Data fields
    private static boolean isSelectMode;
   // private static Cursor defaultCursor;
    private static ImageIcon icon;
    
    /**
     * <p>
     * A constructor for a PencilButton
     * Ensures that DrawMode is initially disabled and the initial PencilButton icon is set correctly
     * The constructor also saves a copy of the default cursor for later use
     * </p>
     */
    public SelectButton() {
        isSelectMode = false;
       // defaultCursor = Andie.getCursor();
        icon = new ImageIcon("assets/selecticon.jpg"); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
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
        button.addActionListener(new SelectListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    public static void disableSelectMode(){
        isSelectMode = false; 
    }
    
    public static void enableSelectMode(){
        isSelectMode = true;
    }

    private class SelectAction extends ImageAction{

        SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            isSelectMode = false;
        }

        public void actionPerformed(ActionEvent e){
            Select select = new Select();
            select.setTarget(target);
            target.getImage().apply(select);
            target.getParent().revalidate();
        }
        
        
    }

    private class SelectListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(isSelectMode){
                disableSelectMode();
            }else{
                enableSelectMode(); 
                SelectAction selectAction = new SelectAction(null, icon, null, null);
                selectAction.actionPerformed(e);
            }
        }
    }

}