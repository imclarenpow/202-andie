package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** 
 * <p>
 * A class that represents the crop button that crops a selected area
 * The class has a method to create the button
 * It has a data field to hold the ImageIcon containing the image of the crop button, both solid and grayed
 * Also, there is a datafield to hold the Crop object that is used to crop the selected area
 * And a datafield to hold the SelectButton that this CropButton is associated with
 * In addition, there is a private subclass that provides a custom ActionListener implementation to respond to button clicks
 * </p>
 * 
 * @author Nic Scott
 * @version 1.0
 */
public class CropButton {
    // Data fields 
    private final ImageIcon ICON = new ImageIcon ("assets/cropicon.png");
    private final ImageIcon GRAYEDICON = new ImageIcon("assets/grayscalecropicon.png"); 
    private static JButton cropJButton;
    private static Crop cropper;
    private static SelectButton selectButton;
    private static Select select; 
    private CropListener cropListener = new CropListener();

    /**
     * <p>
     * A constructor for the CropButton class
     * The constructor sets the icon of the CropButton
     * </p>
     * 
     * @param selectButton the SelectButton that this CropButton is associated with
     */
    public CropButton(SelectButton selectButton) {
        this.selectButton = selectButton;
        this.select = selectButton.getSelect();
    }

    /**
     * <p>
     * Returns the ImageIcon containing the solid image of the crop button
     * </p>
     * 
     * @return the ImageIcon containing the solid image of the crop button
     */
    public ImageIcon getIcon(){
        return ICON;
    }

    /**
     * <p>
     * Returns the ImageIcon containing the grayed image of the crop button
     * </p>
     * 
     * @return the ImageIcon containing the grayed image of the crop button
     */
    public ImageIcon getGrayedIcon(){
        return GRAYEDICON;
    }
    
    /**
     * <p>
     * Creates a new JButton to represent the crop button
     * The crop JButton is assigned a CropListener - an implementation of ActionListener
     * The button is also formatted so that it is transparent, and by default grayed out as an area must be selected before it can be used
     * </p>
     * 
     * @return a JButton representing the eraser button
     */
    public JButton createButton() {
        cropJButton = new JButton(GRAYEDICON);
        cropJButton.setOpaque(false);
        cropJButton.setContentAreaFilled(false);
        cropJButton.setBorderPainted(false);

        return cropJButton;
    }

    /**
     * <p>
     * Adds a listener to the crop button and sets its icon to the solid crop icon
     * </p>
     */
    public void startListening(){
        cropJButton.setIcon(getIcon());
        cropJButton.addActionListener(cropListener);
    }

    /**
     * <p>
     * Removes the listener from the crop button and sets its icon to the grayed crop icon
     * </p>
     */
    public void stopListening(){
        cropJButton.setIcon(getGrayedIcon());
        cropJButton.removeActionListener(cropListener);
    }

    /**
     * <p>
     * An implementation of the ImageAction class that crops the selected area and disables select mode
     * </p>
     */
    private class CropAction extends ImageAction{
        /**
         * 
         */
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * actionPerfomed method that crops the selected area and disables select mode
         * </p>
         * 
         * <p>
         * Creates an instance of the Crop class and applies it to the image, before disabling 
         * Uses co-ordinates from the Select instance associated with the SelectButton to create the Crop instance
         * The Crop instance is then applied to the image
         * The Select instance has its original image set to the cropped image
         * Select mode is then disabled by calling the disableSelectMode method of the SelectButton
         * </p>
         * 
         * @param e the ActionEvent triggering this method call (created by the click)
        */
        public void actionPerformed(ActionEvent e){
            if (cropper == null) {
                cropper = new Crop(select.getStartPoint(), select.getEndPoint());
            }else{
                cropper.setStart(select.getStartPoint());
                cropper.setEnd(select.getEndPoint());
            }
            select.revert();
            target.getImage().apply(cropper);
            target.repaint();
            target.getParent().revalidate();
            select.setOriginal(cropper.getCropped());
            select.setTarget(target);
            selectButton.disableSelectMode();
        }
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the crop button
     * </p>
     */
    private class CropListener implements ActionListener {
        /**
         * <p>
         * Handles clicks of the crop button
         * </p>
         * 
         * <p>
         * Creates an instance of the CropAction class and calls its actionPerformed method
         * Removes the listener from the crop button and sets its icon to the grayed crop icon
         * </p>
         * 
         * @param e the ActionEvent triggering this method call (created by the click)
         */
        public void actionPerformed(ActionEvent e) {
            CropAction cropAction = new CropAction(null, null, null, null);
            cropAction.actionPerformed(e);
            stopListening();
        }
    }
}