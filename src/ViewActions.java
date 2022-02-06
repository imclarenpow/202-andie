import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class ViewActions {
    
    protected Vector<Action> actions;

    public ViewActions() {
        actions = new Vector<Action>();
        actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1)));
    }

    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }

    public class ZoomInAction extends ImageAction {

        ZoomInAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Zoom in called");
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class ZoomOutAction extends ImageAction {

        ZoomOutAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Zoom out called");
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class ZoomFullAction extends ImageAction {

        ZoomFullAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Zoom out called");
            target.setZoom(100);
            target.revalidate();
            target.getParent().revalidate();
        }

    }



}
