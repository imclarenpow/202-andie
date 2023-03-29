package cosc202.andie;

import javax.swing.JOptionPane;
import cosc202.andie.lang.LanguageSupport;
public class NoFileSelectedPopup {
    private LanguageSupport lang = new LanguageSupport();

    public NoFileSelectedPopup(){
    }
    public void window(){
        JOptionPane.showMessageDialog(null, lang.text("cantapplyfilter"));
    }
}
