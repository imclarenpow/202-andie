package cosc202.andie;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cosc202.andie.edit.EditActions;
import cosc202.andie.view.ViewActions;


public class ToolBarDetails {
    
    public static void buttons(JToolBar toolBar) {
    
        EditActions editActions = new EditActions();
        ViewActions viewActions = new ViewActions();

        JButton undoB = new JButton("Undo",buttonIcon("undoIcon"));
        JButton redoB = new JButton("Redo",buttonIcon("redoIcon"));
        JButton zoomIn = new JButton("Zoom In",buttonIcon("zoomInIcon"));
        JButton zoomOut = new JButton("Zoom Out",buttonIcon("zoomOutIcon"));

        undoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editActions.new UndoAction("", null, "", null).actionPerformed(e);
            }
        });

        redoB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editActions.new RedoAction("", null, "", null).actionPerformed(e);
            }
        });

        zoomIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewActions.new ZoomInAction("", null, "", null).actionPerformed(e);
            }
        });

        zoomOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewActions.new ZoomOutAction("", null, "", null).actionPerformed(e);
            }
        });

        toolBar.add(undoB);
        toolBar.add(redoB);
        toolBar.add(zoomIn);
        toolBar.add(zoomOut);

    }
    public static Icon buttonIcon(String button) {
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
