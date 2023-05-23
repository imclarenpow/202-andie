package cosc202.andie;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cosc202.andie.edit.EditActions;
import cosc202.andie.view.ViewActions;
import cosc202.andie.lang.LanguageSupport;


/**
 * <p>
 * A class to represent ANDIE's tool bar
 * Provides a number of buttons that can be used by the application class to fill out a tool bar
 * Each button has a custom ActionListener to respond to button clicks
 * </p>
 */
public class ToolBarDetails {
    // Language Instance
    private LanguageSupport lang = new LanguageSupport();

    // Data fields
    // Each represents a button to be placed on the tool bar
    private static JButton undoB;
    private static JButton redoB;
    private static JButton zoomIn;
    private static JButton zoomOut;

    /**
     * <p>
     * Returns true if a button is a zoom button
     * Used by ANDIE to add and remove zoom buttons from the tool bar
     * </p>
     * @param button the button to be checked
     * @return
     */
    public static boolean isZoomButton(JButton button) {
        return button == zoomIn || button == zoomOut;
    }
    
    /**
     * <p>
     * Returns an ArrayList of buttons to be used on ANDIE's tool bar
     * Each button is assigned a custom ActionListener to respond to clicks
     * </p>
     * @return
     */
    public ArrayList<JButton> buttons() {
        
        ArrayList<JButton> toolbarButtons = new ArrayList<JButton>();

        EditActions editActions = new EditActions();
        ViewActions viewActions = new ViewActions();

        undoB = new JButton(lang.text("undo"),buttonIcon("undoIcon"));
        redoB = new JButton(lang.text("redo"),buttonIcon("redoIcon"));
        zoomIn = new JButton(lang.text("zoomin"),buttonIcon("zoomInIcon"));
        zoomOut = new JButton(lang.text("zoomout"),buttonIcon("zoomOutIcon"));


        // Custom ActionListeners

        undoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editActions.new UndoAction("", null, "", null).actionPerformed(e);
                Andie.makeToolBarVisible();
            }
        });

        redoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editActions.new RedoAction("", null, "", null).actionPerformed(e);
                Andie.makeToolBarVisible();
            }
        });

        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewActions.new ZoomInAction("", null, "", null).actionPerformed(e);
                Andie.makeToolBarVisible();
            }
        });

        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewActions.new ZoomOutAction("", null, "", null).actionPerformed(e);
                Andie.makeToolBarVisible();
            }
        });

        toolbarButtons.add(undoB);
        toolbarButtons.add(redoB);
        toolbarButtons.add(zoomIn);
        toolbarButtons.add(zoomOut);

        return toolbarButtons;
    }

    /**
     * <p>
     * Returns the Icon for a given button or null if no icon is available
     * </p>
     * @param button the button whose icon will be found
     * @return the icon of the button (or null)
     */
    public Icon buttonIcon(String button) {
        Icon iconToReturn = null;
        if(button == "undoIcon"){
            iconToReturn = new ImageIcon("assets/undoIcon.png");
        }
        else if(button == "redoIcon"){
            iconToReturn= new ImageIcon("assets/redoIcon.png");
        }
        else if(button == "zoomInIcon"){
            iconToReturn = new ImageIcon("assets/ZoomInIcon.png");
        }
        else if(button == "zoomOutIcon"){
            iconToReturn = new ImageIcon("assets/ZoomOutIcon.png");
        }
        return iconToReturn;
    }
}
