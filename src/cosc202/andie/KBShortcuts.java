package cosc202.andie;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import cosc202.andie.file.FileActions;
import cosc202.andie.file.FileActions.*;
import cosc202.andie.edit.EditActions;
import cosc202.andie.edit.EditActions.*;

public class KBShortcuts{
    private KeyAdapter keyAdapter;

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

                if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown() &! e.isShiftDown()) {
                    // save when user presses Ctrl+S
                   saveAction.actionPerformed(null);
                } else if(e.getKeyCode() == KeyEvent.VK_S && e.isControlDown() && e.isShiftDown()){
                    //save as when user presses Ctrl+Shift+S
                    saveAsAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_O && e.isControlDown()) {
                    // open when user presses Ctrl+O
                    openAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown() &! e.isShiftDown()){
                    // undo when user presses Ctrl+Z
                    undoAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown() && e.isShiftDown()){
                    // redo when user presses Ctrl+Shift+Z
                    redoAction.actionPerformed(null);
                }
            }
        };
    }

    // Expose the keyAdapter field as a public method
    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}