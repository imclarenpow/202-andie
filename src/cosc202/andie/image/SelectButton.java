package cosc202.andie.image;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

import cosc202.andie.Andie;
import cosc202.andie.ImagePanel;
import cosc202.andie.lang.LanguageSupport;


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
    private static ShapesActions shapesActions;
    private static JMenu shapesMenu;
    private static SelectMouseMotionListener selectListener;
    private static boolean hasImage; 
    
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

    public void enableSelectMode() {
        if(selectListener == null){
            selectListener = new SelectMouseMotionListener();
        }
        if(cropJButton == null){
            cropButton = new CropButton(this);
            cropJButton = cropButton.createButton();
        }
        if(shapesMenu == null){
            shapesActions = new ShapesActions(this);
            shapesMenu = shapesActions.createMenu();
        }

        startListening();
        Andie.addButtonToMenuBar(cropJButton);
        Andie.addMenu(shapesMenu);
        isSelectMode = true;
    }
    
    public void disableSelectMode() {
        select.revert(); //check for selection applied already in revert()
        Andie.setSelectIcon(icon);
        stopListening();
        Andie.removeButtonFromMenuBar(cropJButton);
        Andie.removeMenu(shapesMenu);
        isSelectMode = false;
    }

    public static Select getSelect(){
        return select;
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
        if (select != null && select.getTarget().getImage().hasImage()) {
            select.getTarget().addMouseListener(selectListener);
            select.getTarget().addMouseMotionListener(selectListener);
        } 
    }

    public static void stopListening(){
        if (select != null && select.getTarget().getImage().hasImage()) {
            select.getTarget().removeMouseListener(selectListener);
            select.getTarget().removeMouseMotionListener(selectListener);
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
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            if(isSelectMode) {
                disableSelectMode();
            } else {
                SelectAction selectAction = new SelectAction(null, icon, null, null);
                selectAction.actionPerformed(e);
                if (hasImage) {
                    int fullscreen = JOptionPane.showConfirmDialog(null, "Select features must be used in a fullscreen window, do you want to continue?", "Confirm Fullscreen", JOptionPane.OK_CANCEL_OPTION);
                    if(fullscreen == 0){
                        Andie.setSelectIcon(new ImageIcon("assets/exit26.png",null));
                        Dimension currentScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
                        ImagePanel.screenSizeOverride = new Dimension((int)Math.round(currentScreenSize.getWidth()), (int)Math.round(currentScreenSize.getHeight()));
                        enableSelectMode();
                    }
                }
            } 
        }
    }

    private class SelectAction extends ImageAction{
        SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if(target.getImage().hasImage()){
                    if(select == null){
                        select = new Select();
                    }
                    select.setTarget(target);
                    target.repaint();
                    target.getParent().revalidate();
                    hasImage = true;
            }else{
                target.getImage().ShowNoImageError();
                hasImage = false;
            }
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
                shapesActions.stopListening();
            }else{
                select.setEnd(e.getPoint());
                select.getTarget().getImage().apply(select);
                select.getTarget().repaint();
                select.getTarget().getParent().revalidate();
                cropButton.startListening();
                shapesActions.startListening();
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