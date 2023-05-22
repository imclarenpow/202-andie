package cosc202.andie;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import cosc202.andie.file.FileActions;
import cosc202.andie.file.FileActions.*;
import cosc202.andie.edit.EditActions;
import cosc202.andie.edit.EditActions.*;

/**
 * <p>
 * A class to represent the keyboard shortcuts used in ANDIE
 * </p>
 */
public class KBShortcuts{
    private KeyAdapter keyAdapter;
    private int[] konamiCode = {KeyEvent.VK_UP, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_B, KeyEvent.VK_A, KeyEvent.VK_ENTER};
    private int currentKonamiIndex = 0;

    /**
     * <p>
     * Instantiates a new KBShortcuts object
     * Responds to presses of particular keys with particular ImageActions
     * </p> 
     */
    public KBShortcuts(){
        // Create a new KeyAdapter and assign it to the keyAdapter field
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                /** File Menu Related Action Instances */
                FileActions fileActions = new FileActions();
                FileSaveAction saveAction = fileActions.new FileSaveAction(null, null, null, null);
                FileOpenAction openAction = fileActions.new FileOpenAction(null, null, null, null);
                FileSaveAsAction saveAsAction = fileActions.new FileSaveAsAction(null, null, null, null);

                /** Edit Menu Related Action Instances */
                EditActions editActions = new EditActions();
                UndoAction undoAction = editActions.new UndoAction(null, null, null, null);
                RedoAction redoAction = editActions.new RedoAction(null, null, null, null);

                if (e.getKeyCode() == KeyEvent.VK_S && (e.isControlDown()|| e.isMetaDown()) &! e.isShiftDown()) {
                    // save when user presses Ctrl+S (or Command+S on mac)
                   saveAction.actionPerformed(null);
                } else if(e.getKeyCode() == KeyEvent.VK_S && (e.isControlDown()|| e.isMetaDown()) && e.isShiftDown()){
                    //save as when user presses Ctrl+Shift+S (or Command+Shift+S on mac)
                    saveAsAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_O && (e.isControlDown()|| e.isMetaDown())) {
                    // open when user presses Ctrl+O (or Command+O on mac)
                    openAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_Z && (e.isControlDown()|| e.isMetaDown()) &! e.isShiftDown()){
                    // undo when user presses Ctrl+Z (or Command+Z on mac)
                    undoAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_Z && (e.isControlDown()|| e.isMetaDown()) && e.isShiftDown()){
                    // redo when user presses Ctrl+Shift+Z (or Command+Shift+Z on mac)
                    redoAction.actionPerformed(null);
                } else if (e.getKeyCode() == konamiCode[currentKonamiIndex]) {
                    currentKonamiIndex++;
                    if (currentKonamiIndex == konamiCode.length) {
                        // lmao
                        JOptionPane.showMessageDialog(null, "30 Lives Added", "Konami Code", JOptionPane.INFORMATION_MESSAGE);
                        currentKonamiIndex = 0;
                    }               
                } else {
                    currentKonamiIndex = 0;
                }
            }
        };
    }

    // Expose the keyAdapter field as a public method
    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}
