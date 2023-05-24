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
 * A class that handles the SelectButton, and the Crop Button and Shape Menu which pop-up when the SelectButton is clicked
 * This class also tracks the mouse position for the use in selecting an area (@see Select)
 * </p>
 * 
 * <p>
 * Creates the aformentioned buttons and icons, ensuring users can only use them when a selection has been made
 * This is done by disabling the buttons when the user has not selected an area, and making their icons grayed out for visual feedback
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class SelectButton {
    //Data fields
    private LanguageSupport lang = new LanguageSupport();
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
     * A constructor for a Select Button, setting the icon to be used for the button when Select features are not in use
     * </p>
     */
    public SelectButton() {
        icon = new ImageIcon("assets/selecticon.jpg"); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
    }

    /**
     * <p>
     * Enables select mode
     * </p>
     *
     * <p>
     * Initalises listeners and buttons if they have not been initalised already, adding them to the menu and enabling the select mode
     * </p>
     */
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
    
    /**
     * <p>
     * Disables select mode
     * </p>
     * 
     * <p>
     * Reverts selection if any has been made, sets the select icon back to default
     * Removes listeners and buttons from the menu, and disables select mode
     * </p>
     */
    public void disableSelectMode() {
        select.revert(); //check for selection applied already in revert()
        Andie.setSelectIcon(icon);
        stopListening();
        Andie.removeButtonFromMenuBar(cropJButton);
        Andie.removeMenu(shapesMenu);
        isSelectMode = false;
        ImagePanel.screenSizeOverride = new Dimension(0, 0); // removes the screen size override
    }

    /**
     * <p>
     * Returns whether or not select mode is enabled
     * </p>
     * 
     * @return true if select mode is enabled, false otherwise
     */
    public static boolean isSelectMode(){
        return isSelectMode;
    }

    /**
     * <p>
     * Returns the Select object associated with the button
     * This is used to access the co-ordinates of the selected area and the targeted image panel
     * </p>
     * 
     * @return the Select object associated with the button
     */
    public static Select getSelect(){
        return select;
    }
    
    /**
     * <p>
     * Creates a JButton representing the SelectButton to be used on ANDIE's JMenuBar
     * Adds a SelectListener object to respond to button clicks
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

    /**
     * <p>
     * Adds the select listener to the target image panel, enabling click and drag to select
     * </p>
     */
    public static void startListening(){
        if (select != null && select.getTarget().getImage().hasImage()) {
            select.getTarget().addMouseListener(selectListener);
            select.getTarget().addMouseMotionListener(selectListener);
        } 
    }

    /**
     * <p>
     * Removes the select listener from the target image panel, disabling click and drag to select
     * </p>
     */
    public static void stopListening(){
        if (select != null && select.getTarget().getImage().hasImage()) {
            select.getTarget().removeMouseListener(selectListener);
            select.getTarget().removeMouseMotionListener(selectListener);
        shapesActions.stopListening();
        cropButton.stopListening();
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
        /**
         * <p>
         * Disables select mode if the button is clicked when select mode is enabled
         * Otherwise, enables select mode and triggers the SelectAction
         * </p>
         */
        public void actionPerformed(ActionEvent e){
            //Toolkit toolkit = Toolkit.getDefaultToolkit();
            if(isSelectMode) {
                disableSelectMode();
            } else {
                SelectAction selectAction = new SelectAction(null, icon, null, null);
                selectAction.actionPerformed(e);
            }
        }
    }

    /**
     * <p>
     * Handles the behaviour of the SelectButton after its clicked
     * This resizes the screen for consistent behaviour of co-oridnates
     * </p>
     */
    private class SelectAction extends ImageAction{
        /**
         * <p>
         * Constructor for the SelectAction class
         * </p>
         * @param name
         * @param icon
         * @param desc
         * @param mnemonic
         */
        SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Enables select mode and resizes the screen
         * </p>
         * 
         * <p>
         * Initalises select if it has not been initalised already, setting the target
         * Sets the icon to the exit icon, so the user understands it must be clicked again to exit select mode
         * Resizes the screen for consistent behaviour of co-ordinates
         * Enables select mode
         * Displays error message if selection is attempted when ANDIE does not have an image open
         * </p>
         */
        public void actionPerformed(ActionEvent e){
            if(target.getImage().hasImage()){
                    if(select == null){
                        select = new Select();
                    }
                    select.setTarget(target);
                    target.repaint();
                    target.getParent().revalidate();
                    Andie.setSelectIcon(new ImageIcon("assets/exit26.png",null));
                    Dimension currentScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    ImagePanel.screenSizeOverride = new Dimension((int)Math.round(currentScreenSize.getWidth() / 1.2), (int)Math.round(currentScreenSize.getHeight() / 1.2));
                    enableSelectMode();
                    hasImage = true;
            }else{
                target.getImage().ShowNoImageError();
                hasImage = false;
            }
        }
        
        
    }

    /**
     * <p>
     * Sets co-ordinates and applies the selection when the mouse is clicked and dragged by calling the apply method of Select
     * This also handles the behaviour of the crop button and the shapes menu, which should only be used when a selection is applied
     * </p>
     */
    private static class SelectMouseMotionListener extends MouseInputAdapter {
        /**
         * <p>
         * Identifies the initial coordinates when the mouse is pressed/dragged
         * </p>
         */
        public void mousePressed(MouseEvent e) {
            select.setStart(e.getPoint());
            //System.out.println(e.getPoint().getX() + ", " + e.getPoint().getY()); 
            // For bug-fixing co-ordinate problems
        }

        /**
         * <p>
         * When the user has not selected an area:
         * Identifies the final co-ordinates where the mouse is released
         * Applies the selection by calling Select's apply method, as well as enabling the crop button and shapes menu
         * 
         * When the user has selected an area:
         * Reverts the selection by calling Select's revert method, as well as disabling the crop button and shapes menu
         * </p>
         */
        public void mouseReleased(MouseEvent e) {
            if(select.isSelectionApplied()){
                select.revert();
                cropButton.stopListening();
                shapesActions.stopListening();
            }else{
                select.setEnd(e.getPoint());
                select.apply(select.getTarget().getImage().getCurrent());
                select.getTarget().repaint();
                select.getTarget().getParent().revalidate();
                cropButton.startListening();
                shapesActions.startListening();
            }
        }

        /**
         * <p>
         * Updates the end point of the selection as the mouse is dragged
         * </p>
         */
        public void mouseDragged(MouseEvent e) {
            select.setEnd(e.getPoint());
        }

    }

}