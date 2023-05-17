package cosc202.andie.image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** 
 * <p>
 * A class that represents the crop button that crops a selected area
 * The class has a method to create the button
 * It has a data field to hold the ImageIcon containing the image of the eraser
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
    private static Select select; 
    private CropListener cropListener = new CropListener();

    /**
     * <p>
     * A constructor for the CropButton class
     * The constructor sets the icon of the CropButton
     * </p>
     */
    public CropButton(Select select) {
        this.select = select;
    }

    public ImageIcon getIcon(){
        return ICON;
    }

    public ImageIcon getGrayedIcon(){
        return GRAYEDICON;
    }
    
    /**
     * <p>
     * Creates a new JButton to represent the crop button
     * The crop JButton is assigned a CropListener - an implementation of ActionListener
     * The button is also formatted so that it is transparent etc.
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

    public void startListening(){
        cropJButton.setIcon(getIcon());
        cropJButton.addActionListener(cropListener);
    }

    public void stopListening(){
        cropJButton.setIcon(getGrayedIcon());
        cropJButton.removeActionListener(cropListener);
    }

    private class CropAction extends ImageAction{
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

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
         * Creates a new instance of EditActions and its member class UndoAction
         * The undoDraw method of UndoAction is called which removes all drawings
         * Following this, drawing is reenabled for the user
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