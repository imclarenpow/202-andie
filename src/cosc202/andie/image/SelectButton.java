package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

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
    private static CropButton cropButton;
    private static JButton cropJButton;
    private static SelectMouseMotionListener selectListener;
    
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

    public static void enableSelectMode() {
        if(selectListener == null){
            selectListener = new SelectMouseMotionListener();
        }
        if(cropJButton == null){
            cropButton = new CropButton(select);
            cropJButton = cropButton.createButton();
        }
        startListening();
        Andie.addButtonToMenuBar(cropJButton);
        isSelectMode = true;
    }
    
    public static void disableSelectMode() {
        select.revert(); //check for selection applied already in revert()
        Andie.setSelectIcon(icon);
        stopListening();
        Andie.removeButtonFromMenuBar(cropJButton);
        isSelectMode = false;
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

    public static void startListening(){
        select.getTarget().addMouseListener(selectListener);
        select.getTarget().addMouseMotionListener(selectListener);
    }

    public static void stopListening(){
        select.getTarget().removeMouseListener(selectListener);
        select.getTarget().removeMouseMotionListener(selectListener);
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

    private static class SelectMouseMotionListener extends MouseInputAdapter {
        /**
         * Identifies the initial coordinates when the mouse is pressed/dragged
         */
        public void mousePressed(MouseEvent e) {
            select.setStart(e.getPoint());
            //System.out.println(e.getPoint().getX() + ", " + e.getPoint().getY()); 
            // For bug-fixing co-ordinate problems
        }

        /**
         * Identifies the final co-ordinates where the mouse is released
         */
        public void mouseReleased(MouseEvent e) {
            if(select.isSelectionApplied()){
                select.revert();
                cropButton.stopListening();
            }else{
                select.setEnd(e.getPoint());
                select.getTarget().getImage().apply(select);
                select.getTarget().repaint();
                select.getTarget().getParent().revalidate();
                cropButton.startListening();
            }
        }

        /**
         * Responds to mouse drags by drawing a rectangle onto the target image
         */
        public void mouseDragged(MouseEvent e) {
            select.setEnd(e.getPoint());
        }

    }

}