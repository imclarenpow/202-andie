package cosc202.andie.help;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.image.*;
import cosc202.andie.lang.*;

/**
 * Actions Menu for help.
 * 
 * @author Isaac Powell
 * @version 1.0
 * 
 * additions:
 * - implements internationalisation
 * - two buttons currently, perhaps on later versions more buttons
 */

public class HelpActions {
    /** creating an instance of the LanguageSupport class for internationalisation */
    private LanguageSupport lang = new LanguageSupport();
    /** a list of actions for the help menu */
    protected ArrayList<Action> actions;

    /**
     * Create a set of Help Menu Actions.
     */
    public HelpActions(){
        //String values in here call lang.txt for internationalisation
        actions = new ArrayList<Action>();

        actions.add(new LanguageAction(lang.text("language"), null, lang.text("language"), Integer.valueOf(KeyEvent.VK_L)));
        actions.add(new DocuAction(lang.text("help"), null, lang.text("help"), Integer.valueOf(KeyEvent.VK_H)));
    }
    /**
     * Create the menu containing the list of filter actions
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu(lang.text("help"));
        for(Action action: actions){
            fileMenu.add(new JMenuItem(action));
        }
        return fileMenu;
    }
    public JMenu reset(){
        return createMenu();
    }
    /** Allows the user to change the language of the program
     * @author Isaac with assistance from ChatGPT
     */
    public class LanguageAction extends ImageAction{
       
        LanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            //Options for the dropdown menu
            String[] options = {lang.text("english"), lang.text("maori"), lang.text("japanese"), lang.text("german"),
                lang.text("mongolian"), lang.text("italian"), lang.text("spanish"), lang.text("ukranian"), lang.text("urdu")};
            JComboBox<String> dropdown = new JComboBox<>(options);
            JPanel panel = new JPanel();
            panel.add(dropdown);
        }

            public void actionPerformed(ActionEvent e) {
                // have to reference indirectly as static by extension
                LanguageSupport l = new LanguageSupport();
                String[] options = {"English", "Maori", "Japanese", "German", "Mongolian", "Italian", "Spanish", "Ukranian", "Urdu"};
                JComboBox<String> dropdown = new JComboBox<>(options);
                JPanel panel = new JPanel();
                panel.add(dropdown);
                int result = JOptionPane.showOptionDialog(null, panel, l.text("select"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, // icon
                            // internationalisationing the buttons                
                                new Object[] {
                                    l.text("ok"),
                                    l.text("cancel")
                                },
                                lang.text("cancel")); //default selection (if window is closed)
                if (result == JOptionPane.OK_OPTION) {
                    String selectedOption = (String) dropdown.getSelectedItem();
                    if(selectedOption == "English"){
                        l.setDefaultLanguage("en", "NZ");
                    }else if(selectedOption == "Maori"){
                        l.setDefaultLanguage("mi", "NZ");
                    }else if(selectedOption == "Japanese"){
                        l.setDefaultLanguage("ja", "JP");
                    }
                }
            }
    }
    public class DocuAction extends ImageAction{
        DocuAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }
        public void actionPerformed(ActionEvent e){
            new DocuWindow();
        }
    }
}

