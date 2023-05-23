package cosc202.andie;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cosc202.andie.edit.EditActions;
import cosc202.andie.view.ViewActions;


public class ToolBarDetails {

    private static JButton undoB;
    private static JButton redoB;
    private static JButton zoomIn;
    private static JButton zoomOut;

    public static boolean isZoomButton(JButton button) {
        return button == zoomIn || button == zoomOut;
    }
    
    public ArrayList<JButton> buttons() {
        
        ArrayList<JButton> toolbarButtons = new ArrayList<JButton>();

        EditActions editActions = new EditActions();
        ViewActions viewActions = new ViewActions();

        undoB = new JButton("Undo",buttonIcon("undoIcon"));
        redoB = new JButton("Redo",buttonIcon("redoIcon"));
        zoomIn = new JButton("Zoom In",buttonIcon("zoomInIcon"));
        zoomOut = new JButton("Zoom Out",buttonIcon("zoomOutIcon"));

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
