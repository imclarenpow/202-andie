package cosc202.andie;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import cosc202.andie.file.FileActions;
import cosc202.andie.file.FileActions.*;

public class KBShortcuts{
    private FileActions fa;
    private KeyAdapter keyAdapter;

    public KBShortcuts(){
        // Create a new KeyAdapter and assign it to the keyAdapter field
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                FileActions fileActions = new FileActions();
                FileSaveAction saveAction = fileActions.new FileSaveAction(null, null, null, null);
                FileOpenAction openAction = fileActions.new FileOpenAction(null, null, null, null);

                if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                    // do something when user presses Ctrl+S
                   saveAction.actionPerformed(null);
                } else if (e.getKeyCode() == KeyEvent.VK_O && e.isControlDown()) {
                    // do something when user presses Ctrl+O
                    openAction.actionPerformed(null);
                }
            }
        };
    }

    // Expose the keyAdapter field as a public method
    public KeyAdapter getKeyAdapter() {
        return keyAdapter;
    }
}