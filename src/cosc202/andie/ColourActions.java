package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class ColourActions {
    
    protected Vector<Action> actions;

    public ColourActions() {
        actions = new Vector<Action>();
        actions.add(new ColourGreyscaleAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
    }

    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class ColourGreyscaleAction extends ImageAction {

        ColourGreyscaleAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Converted to greyscale");
            target.getImage().apply(new ConvertToGrey());
            target.repaint();
            target.getParent().revalidate();
        }

    }

}
