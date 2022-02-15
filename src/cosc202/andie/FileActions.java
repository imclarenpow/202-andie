package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FileActions {
    
    protected Vector<Action> actions;


    public FileActions() {
        actions = new Vector<Action>();
        actions.add(new FileOpenAction("Open", null, "Open a file", Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction("Save", null, "Save the file", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction("Save As", null, "Save a copy", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExitAction("Exit", null, "Exit the program", Integer.valueOf(0)));
    }

    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class FileOpenAction extends ImageAction {

        FileOpenAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("File Open called");
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class FileSaveAction extends ImageAction {

        FileSaveAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("File Save called");
            try {
                target.getImage().save();           
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public class FileSaveAsAction extends ImageAction {

        FileSaveAsAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("File Save As called");

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

    }

    public class FileExitAction extends AbstractAction {

        FileExitAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Exit called");
            System.exit(0);
        }

    }

}
