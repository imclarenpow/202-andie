package cosc202.andie.image;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import cosc202.andie.image.*;
import cosc202.andie.lang.*;

/*
 * Needs to check if Select is applied, before allowing shapes to be drawn
 */
public class ShapesActions {
    private boolean selectionApplied = false;
    private static Select select;
    protected ArrayList<Action> actions; 
    private static JMenu shapesMenu;
    private final ImageIcon ICON = new ImageIcon("assets/shapesicon.png"); // retrieved from https://www.pngfind.com/mpng/ibTmJxT_shapes-clipart-png-transparent-png/ (free to use license)
    private final ImageIcon GRAYEDICON = new ImageIcon("assets/grayscaleshapesicon.png");

    public ShapesActions(Select select) {
        this.select = select;
        actions = new ArrayList<Action>();
        
        actions.add(new RectangleAction("Filled Rectangle", null, null, null));
        actions.add(new EllipseAction("Filled Ellipse", null, null, null));
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
        selectionApplied = true; // only called in SelectButton when Select is applied, no need to check Select variable
    }

    public void stopListening() {
        shapesMenu.setIcon(GRAYEDICON);
        selectionApplied = false; // only called in SelectButton when Select is not applied, no need to check Select variable
    }

    public class RectangleAction extends ImageAction {
        RectangleAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Rectangle rectangle = new Rectangle(select.getStartPoint(), select.getEndPoint());
            select.revert();
            target.getImage().apply(rectangle);
            target.repaint();
            target.getParent().revalidate();
            //Needs pop-up menu for outline/fill selection & color picker 
        }
    }

    public class EllipseAction extends ImageAction {
        EllipseAction(String name, ImageIcon icon, String desc, Integer mnemonic){
           super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Ellipse ellipse = new Ellipse(select.getStartPoint(), select.getEndPoint());
            select.revert();
            target.getImage().apply(ellipse);
            target.repaint();
            target.getParent().revalidate();
            //Needs pop-up menu for outline/fill selection & color picker 
        }
    }

    public class LineAction extends ImageAction {
        public LineAction(String name, ImageIcon icon, String desc, Integer mnemonic){
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            Line line = new Line(select.getStartPoint(), select.getEndPoint());
            select.revert();
            target.getImage().apply(line);
            target.repaint();
            target.getParent().revalidate();
            //Needs pop-up menu for outline/fill selection & color picker 
        }
    }
}
