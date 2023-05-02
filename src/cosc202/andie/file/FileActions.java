package cosc202.andie.file;

import java.util.*;
import java.awt.event.*;

import javax.imageio.IIOException;
import javax.swing.*;

import cosc202.andie.Andie;
import cosc202.andie.EditableImage;
import cosc202.andie.ImagePanel;
import cosc202.andie.image.*;
import cosc202.andie.lang.*;

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
    private ArrayList<String> validImageExtensions = new ArrayList<String>(Arrays.asList("jpg", "png"));

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
         * @throws IIOException If the specified file is invalid or corrupted.
         * @throws SecurityException If the application doesn't have access privileges to the file.
         * @throws Exception For all other unhandled exceptions.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                    
                    //Sets window size according to image size
                    Andie.resizeWindowToImage(target.getPreferredSize());
                } catch (IIOException noFile) {
                    JOptionPane.showMessageDialog(null, lang.text("openfilenamewarning"),
                            lang.text("invalidfilename"), JOptionPane.ERROR_MESSAGE);
                            return;
                } catch (SecurityException ex) {
                    JOptionPane.showMessageDialog(null, lang.text("noaccesswarning"),
                            lang.text("accessdenied"), JOptionPane.ERROR_MESSAGE);
                            return;
                } catch (Exception ex) {
                    System.out.println(ex);
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
        public FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
                String imageFilepath = getValidImageFilepath(target, "Save Image");
                if (!imageFilepath.isEmpty()) {
                    target.getImage().saveAs(imageFilepath);
                }
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    /**
     * <p>
     * Repeatedly prompts the user for an image filepath until one with a valid extension is returned
     * </p>
     * 
     * <p>
     * This helper method is used when a valid filepath is needed for saving/exporting an image.
     * It displays a dialog box prompting the user for an image filepath
     * If they provide a filepath without a valid extension then a warning is displayed and the user is again prompted for a filepath
     * </p>
     * 
     * @param e The event triggering this callback.
     */
    private String getValidImageFilepath(ImagePanel target, String dialogName) {
        JFileChooser fileChooser = new JFileChooser();
        String filePath = "";
        int result = fileChooser.showDialog(target, dialogName);
        while (filePath.equals("") && result == JFileChooser.APPROVE_OPTION) {
            try {
                String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                boolean hasValidExtension = validImageExtensions.contains(imageFilepath.substring(1+imageFilepath.lastIndexOf(".")).toLowerCase());
                if (hasValidExtension) {
                    filePath = imageFilepath;
                } else {
                    throw new Exception("Invalid image filepath");
                }
            } catch (Exception ex) {
                // Displays an error message when an invalid filename is presented
                // Adapted from https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
                JOptionPane.showMessageDialog(null, lang.text("filenamewarning") + "\n" +
                validImageExtensions.toString(),
                lang.text("invalidfilename"),
                JOptionPane.WARNING_MESSAGE);
                result = fileChooser.showSaveDialog(target);
            }
        } 
        
        return filePath;
    }

    /**
     * <p>
     * Action to export an edited image to a new file location.
     * </p>
     * 
     * @author Niamh Avery
     * @see EditableImage#exportAs(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and exports the edited image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                String imageExportFilepath = getValidImageFilepath(target, "Export image");
                if (!imageExportFilepath.isEmpty()) {
                    target.getImage().exportAs(imageExportFilepath);
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
