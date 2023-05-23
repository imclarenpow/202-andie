package cosc202.andie.macros;

import java.util.*;
import java.io.*;

import javax.imageio.IIOException;
import javax.swing.*;

import cosc202.andie.EditableImage;
import cosc202.andie.image.*;
import cosc202.andie.lang.*;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Actions provided by the Macro menu.
 */
public class MacroActions {
    private LanguageSupport lang = new LanguageSupport();
    private boolean recording = false;
    private ArrayList<Action> actions;
    private Stack <ImageOperation> imageOps;
    private Stack <ImageOperation> macroOps;
    private EditableImage editableImage = new EditableImage();
    private int size;
    
    

    public MacroActions() {
        actions = new ArrayList<Action>();
        actions.add(new MacroStartAction(lang.text("startmac"), null, lang.text("startmac"), KeyEvent.VK_P));
        actions.add(new MacroStopAction(lang.text("stopandsavemac"), null, lang.text("stopandsavemac"), KeyEvent.VK_R));
        actions.add(new MacroOpenAction(lang.text("openmac"), null, lang.text("openmac"), KeyEvent.VK_R));
    }

    public JMenu createMenu() {
        JMenu macroMenu = new JMenu(lang.text("mac"));

        for (Action action : actions) {
            macroMenu.add(new JMenuItem(action));
        }

        return macroMenu;
    }

    /**
    * <p>
    * Action to open an image from file.
    * </p>
    * 
    * @see EditableImage#open(String)
    */
   public class MacroStartAction extends ImageAction {

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
       public MacroStartAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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
        
        if(target.getImage().hasImage() ){
            System.out.println("Macro start");
            recording = true;
            if(editableImage.getImageOps() != null){
            size = editableImage.getImageOps().size();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, lang.text("noimagewarning"),
            lang.text("noimage"),
            JOptionPane.WARNING_MESSAGE);
        }
           }
        }

        public class MacroStopAction extends ImageAction {

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
            public MacroStopAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
                super(name, icon, desc, mnemonic);
            }

        public void actionPerformed(ActionEvent e) {
            macroOps = new Stack<ImageOperation>();
            // TODO: Implement macro record logic
            System.out.println("Macro Record");
            if (recording) {
                imageOps = editableImage.getImageOps();
                for (int i = size; i < imageOps.size(); i++) {
                    macroOps.push(imageOps.get(i));
                }
                String userInput = JOptionPane.showInputDialog(null, "Enter your macro file name:");

                try {
                    saveMacro(userInput);
                    JOptionPane.showMessageDialog(null, "Macro saved: " + userInput, "Macro Saved", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Warning! Error saving macro" + ex.getMessage(), "Macro Saving Error", JOptionPane.ERROR_MESSAGE);
                }

                // Print the user's input
                System.out.println("User input: " + userInput);
            } else {
                JOptionPane.showMessageDialog(null, "Warning! There is no macro recording!", "noMacroRecording", JOptionPane.WARNING_MESSAGE);

            }
           

        }
    }

    public class MacroOpenAction extends ImageAction {

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
        public MacroOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
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

if (result == JFileChooser.APPROVE_OPTION) {//taken from fileactions class and modified for reading macros.
    try {
        File selectedFile = fileChooser.getSelectedFile();
        String imageFilePath = selectedFile.getCanonicalPath();

        if (imageFilePath.toLowerCase().endsWith(".macro")) {
            // File has the ".macro" extension, continue with processing
            for (ImageOperation imageOperation : macroOps) {
                target.getImage().apply(imageOperation);
            }
            target.repaint();
            target.getParent().revalidate();
        } else {
            // File does not have the ".macro" extension, handle the error or display a message to the user
            JOptionPane.showMessageDialog(null, lang.text("invalidfilename"), lang.text("invalidfilename"), JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (IIOException noFile) {
        JOptionPane.showMessageDialog(null, lang.text("openfilenamewarning"), lang.text("invalidfilename"), JOptionPane.ERROR_MESSAGE);
        return;
    } catch (SecurityException ex) {
        JOptionPane.showMessageDialog(null, lang.text("noaccesswarning"), lang.text("accessdenied"), JOptionPane.ERROR_MESSAGE);
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
 
    public void saveMacro(String macroName) throws Exception {        

        String macroFileName = macroName + ".macro";

        FileOutputStream fileOut = new FileOutputStream(macroFileName);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(macroOps);
        objOut.close();
        fileOut.close();
    }
}

