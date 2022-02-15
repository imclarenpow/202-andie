import javax.swing.*;

public abstract class ImageAction extends AbstractAction {
    protected static ImagePanel target;

    ImageAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public static void setTarget(ImagePanel newTarget) {
        target = newTarget;
    } 

    public static ImagePanel getTarget() {
        return target;
    }

}
