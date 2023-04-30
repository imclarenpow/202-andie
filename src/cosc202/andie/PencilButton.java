package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import cosc202.andie.lang.LanguageSupport;

public class PencilButton {
    private static boolean isDrawMode;
    private static Cursor defaultCursor;
    private static ImageIcon icon;
    
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();

    public PencilButton() {
        isDrawMode = false;
        defaultCursor = Andie.getCursor();
        icon = new ImageIcon("assets/pencil.png"); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)
    }
    
    public JButton createButton() {
        JButton button = new JButton(icon);
        button.addActionListener(new PencilListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    public static void disableDrawMode() {
        isDrawMode = false;
        // Changes the cursor back to the default cursor
        Andie.setCursor(defaultCursor);  
        Andie.setPencilIcon(icon); // retrieved from https://cdn-icons-png.flaticon.com/512/1046/1046346.png (free to use license)

    }

    private class PencilListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (isDrawMode) {
                disableDrawMode();
            } else {
                Andie.setPencilIcon(new ImageIcon("assets/exit.png", null)); // retrieved from https://static.vecteezy.com/system/resources/previews/018/888/692/non_2x/signs-close-icon-png.png (free to use license)
                isDrawMode = true;
                
                // Code to change the cursor to a pencil
                // Adapted from https://stackoverflow.com/questions/4274606/how-to-change-cursor-icon-in-java
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Cursor newCursor = toolkit.createCustomCursor(new ImageIcon("assets/pencil.png").getImage(), new Point(0, 0), "pencil");
                Andie.setCursor(newCursor);
                PencilAction pencilAction = new PencilAction(null, icon, null, null);
                pencilAction.actionPerformed(e);
            }
        }
    }

    private class PencilAction extends ImageAction {
        
        PencilAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Pencil pencil = new Pencil();
            pencil.setTarget(target);
            target.getImage().apply(pencil);
            target.repaint();
            target.getParent().revalidate();
        }
    }
}