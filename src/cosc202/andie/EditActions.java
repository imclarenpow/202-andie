import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class EditActions {
    
    protected Vector<Action> actions;

    public EditActions() {
        actions = new Vector<Action>();
        actions.add(new UndoAction("Undo", null, "Undo", Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_Y)));
    }

    public JMenu createMenu() {
        JMenu editMenu = new JMenu("Edit");

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    public class UndoAction extends ImageAction {

        UndoAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Undo called");
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

    public class RedoAction extends ImageAction {

        RedoAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Redo called");
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
        }
    }

}
