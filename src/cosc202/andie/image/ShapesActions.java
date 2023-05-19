package cosc202.andie.image;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.image.*;
import cosc202.andie.lang.*;

public class ShapesActions {
    protected ArrayList<Action> actions; 
    private static JMenu shapesMenu;
    private static Select select;
    private final ImageIcon ICON = new ImageIcon("assets/shapesicon.png"); // retrieved from https://www.pngfind.com/mpng/ibTmJxT_shapes-clipart-png-transparent-png/ (free to use license)
    private final ImageIcon GRAYEDICON = new ImageIcon("assets/grayscaleshapesicon.png");

    public ShapesActions(Select select) {
        this.select = select;
        actions = new ArrayList<Action>();

        actions.add(new OutLinedRectangleAction("Outlined Rectangle", null, null, null));
        actions.add(new FilledRectangleAction("Filled Rectangle", null, null, null));
        actions.add(new OutLinedEllipseAction("Outlined Ellipse", null, null, null));
        actions.add(new FilledEllipseAction("Filled Ellipse", null, null, null));
        actions.add(new LineAction("Line", null, null, null));
    }

    public JMenu createMenu(){
        shapesMenu = new JMenu();
        shapesMenu.setIcon(GRAYEDICON);

        for(Action action: actions){
            shapesMenu.add(action);
        }
        return shapesMenu;
    }

    public void startListening() {
        shapesMenu.setIcon(ICON);
    }

    public void stopListening() {
        shapesMenu.setIcon(GRAYEDICON);
    }

    public class OutLinedRectangleAction extends ImageAction {
        OutLinedRectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            //select.setShapeType(Select.ShapeType.OUTLINEDRECTANGLE);
        }
    }

    public class FilledRectangleAction extends ImageAction {
        FilledRectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
           // select.setShapeType(Select.ShapeType.FILLEDRECTANGLE);
        }
    }

    public class OutLinedEllipseAction extends ImageAction {
        OutLinedEllipseAction(String name, ImageIcon icon, String desc, Integer mnemonic){
           super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            //select.setShapeType(Select.ShapeType.OUTLINEDELLIPSE);
        }
    }

    public class FilledEllipseAction extends ImageAction {
        public FilledEllipseAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            //select.setShapeType(Select.ShapeType.FILLEDELLIPSE);
        }
    }

    public class LineAction extends ImageAction {
        public LineAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            //select.setShapeType(Select.ShapeType.LINE);
        }
    }
}
