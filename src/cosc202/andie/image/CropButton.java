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
    private ImageIcon icon;
    private static Crop cropper;
    private static Select select; 

    /**
     * <p>
     * A constructor for the CropButton class
     * The constructor sets the icon of the CropButton
     * </p>
     */
    public CropButton() {
        icon = new ImageIcon("assets/cropicon.png"); // Retrieved from https://png.pngtree.com/png-clipart/20230105/original/pngtree-eraser-clipart-png-image_8875456.png (free to use) and modified with Microsoft Publisher + Microsoft Word
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
        JButton cropJButton = new JButton(icon);
        cropJButton.addActionListener(new CropListener());
        cropJButton.setOpaque(false);
        cropJButton.setContentAreaFilled(false);
        cropJButton.setBorderPainted(false);

        return cropJButton;
    }

    private class CropAction extends ImageAction{
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e){
            if (cropper == null) {
                cropper = new Crop(select.getStartPoint(), select.getEndPoint());
            }
            target.getImage().apply(cropper);
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * An implementation of the ActionListener interface that responds to clicks of the eraser button
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
            CropAction cropAction = new CropAction(null, icon, null, null);
            cropAction.actionPerformed(e);
        }
    }
}