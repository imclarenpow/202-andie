package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.lang.*;

/**Test test
 * 
 * <p>
 * Actions provided by the View menu.
 * </p>
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    // creating an instance of the LanguageSupport class for Internationalisation
    private LanguageSupport lang = new LanguageSupport();
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        //string values call lang
        actions = new ArrayList<Action>();
        actions.add(new ZoomInAction(lang.text("zoomin"), null, lang.text("zoomin"), Integer.valueOf(KeyEvent.VK_PLUS)));
        actions.add(new ZoomOutAction(lang.text("zoomout"), null, lang.text("zoomout"), Integer.valueOf(KeyEvent.VK_MINUS)));
        actions.add(new ZoomFullAction(lang.text("zoomfull"), null, lang.text("zoomfull"), Integer.valueOf(KeyEvent.VK_1)));
        actions.add(new LanguageAction(lang.text("language"), null, lang.text("language"), Integer.valueOf(KeyEvent.VK_1)));
    }

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu(lang.text("view"));

        for (Action action: actions) {
            viewMenu.add(new JMenuItem(action));
        }

        return viewMenu;
    }
    public JMenu reset(){
        return createMenu();
    }
    /** Allows the user to change the language of the program
     * @author Isaac with assistance from ChatGPT
     */
    public class LanguageAction extends ImageAction{
    // need to implement the lang calling for each string value    
        LanguageAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
            //Options for the dropdown menu
            String[] options = {lang.text("english"), lang.text("maori"), "Other"};
            JComboBox<String> dropdown = new JComboBox<>(options);
            JPanel panel = new JPanel();
            panel.add(dropdown);
        }

                public void actionPerformed(ActionEvent e) {
                    // have to reference indirectly as static by extension
                    LanguageSupport l = new LanguageSupport();
                    String[] options = {"English", "Maori", "Coming Soon"};
                    JComboBox<String> dropdown = new JComboBox<>(options);
                    JPanel panel = new JPanel();
                    panel.add(dropdown);
                    int result = JOptionPane.showConfirmDialog(null, panel,
                            lang.text("select"), JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String selectedOption = (String) dropdown.getSelectedItem();
                        if(selectedOption == "English"){
                            l.setDefaultLanguage("en", "NZ");
                        }else if(selectedOption == "Maori"){
                            l.setDefaultLanguage("mi", "NZ");
                        }
                    }
                }
        }

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }
        

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-iout action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {

        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.revalidate();
            target.getParent().revalidate();
        }

    }



}
