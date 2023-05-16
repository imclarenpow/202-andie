package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.Andie;


/** 
 * <p>
 * Adapted from Niamh's PencilButton implemntation
 * This class represents the SelectButton and its functionality, including calls to Select.java that allow the user to draw on an image
 * The SelectButton is placed on the menu bar 
 * It includes private classes for the SelectAction and a SelectListener to respond to button clicks
 * </p>
 * 
 * 
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class SelectButton {
    //private static Cursor defaultCursor;
    private static ImageIcon icon;
    private static boolean isSelectMode;
    private static Select select;
    private static JButton cropJButton;
    
    /**
     * <p>
     * A constructor for a Select Button
     * The constructor also saves a copy of the default cursor for later use
     * </p>
     */
    public SelectButton() {
       // defaultCursor = Andie.getCursor();
        icon = new ImageIcon("assets/selecticon.jpg"); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
    }
    
    public static void disableSelectMode() {
        Andie.setSelectIcon(icon);
        select.stopListening();
        Andie.removeButtonFromMenuBar(cropJButton);
        isSelectMode = false;
    }

    public static void enableSelectMode() {
        if(cropJButton == null){
            CropButton cropButton = new CropButton(select);
            cropJButton = cropButton.createButton();
        }
        select.startListening();
        Andie.addButtonToMenuBar(cropJButton);
        isSelectMode = true;
    }
    
    /**
     * <p>
     * Creates a JButton representing the SelectButton to be used on ANDIE's JMenuBar
     * Includes a SelectListener object to respond to button clicks
     * </p>
     * 
     * @return a JButton representing the SelectButton
     */
    public JButton createButton() {
        JButton selectButton = new JButton(icon);
        selectButton.addActionListener(new SelectListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        selectButton.setOpaque(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setBorderPainted(false);

        return selectButton;
    }

    private class SelectAction extends ImageAction{
        SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if(select == null){
                select = new Select();
            }
            select.setTarget(target);
            target.repaint();
            target.getParent().revalidate();
        }
        
        
    }


    /**
     * <p>
     * Handles clicks of the SelectButton
     * </p>
     * 
     * @param e the ActionEvent created by the click
     */
    private class SelectListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(isSelectMode){
                disableSelectMode();
            }else{
                Andie.setSelectIcon(new ImageIcon("assets/exit26.png",null));
                SelectAction selectAction = new SelectAction(null, icon, null, null);
                selectAction.actionPerformed(e);
                enableSelectMode();
            }
    }
    }

}