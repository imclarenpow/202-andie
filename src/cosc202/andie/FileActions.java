package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.lang.LanguageSupport;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications, 
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class FileActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    /** A list of valid extensions for an image file */
    private ArrayList<String> validImageExtensions = new ArrayList<String>(Arrays.asList("jpg", "png", ".bmp", ".jpeg", ".gif", ".wbmp"));

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        //string values call lang
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction(lang.text("open"), null, lang.text("openafile"), Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction(lang.text("save"), null, lang.text("savethefile"), Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction(lang.text("saveas"), null, lang.text("savethefile"), Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction(lang.text("exportas"), null, lang.text("exportthefile"), Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new FileExitAction(lang.text("exit"), null, lang.text("exittheprog"), Integer.valueOf(0)));
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(lang.text("file"));

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                } catch (Exception ex) {
                    System.exit(1);
                }
            }

            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().save();           
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

         /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                String imageFilepath = getValidImageFilepath(target);
                if (!imageFilepath.isEmpty()) {
                    target.getImage().saveAs(imageFilepath);
                }
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    private String getValidImageFilepath(ImagePanel target) {
        JFileChooser fileChooser = new JFileChooser();
        String filePath = "";
        int result = JFileChooser.APPROVE_OPTION;
        do {
            try {
                result = fileChooser.showSaveDialog(target);
                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                boolean hasValidExtension = validImageExtensions.contains(imageFilepath.substring(1+imageFilepath.lastIndexOf(".")).toLowerCase());
                if (hasValidExtension) {
                    filePath = imageFilepath;
                } else if (result == JFileChooser.APPROVE_OPTION) {
                    //Displays an error message - thx https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
                    JOptionPane.showMessageDialog(null,
                    "Filename must include a valid image extension from the following set:\n " + validImageExtensions.toString(),
              "Error saving image",
                    JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                System.exit(1);
            }
        } while (filePath.equals("") && result == JFileChooser.APPROVE_OPTION);
        
        return filePath;
    }

    public class FileExportAction extends ImageAction {

        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            try {
                String imageFilepath = getValidImageFilepath(target);
                if (!imageFilepath.isEmpty()) {
                    target.getImage().exportAs(imageFilepath);
                }
            } catch (Exception ex) {
                System.exit(1);
            }
        }
    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

         /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }

}
