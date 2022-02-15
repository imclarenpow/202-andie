import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FilterActions {
    
    protected Vector<Action> actions;

    public FilterActions() {
        actions = new Vector<Action>();
        actions.add(new FilterMedianAction("Median filter", null, "Apply a median filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new FilterGaussianAction("Gaussian filter", null, "Apply a Gaussian blur filter", Integer.valueOf(KeyEvent.VK_G)));
    }

    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");

        for(Action action: actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    public class FilterMedianAction extends ImageAction {

        FilterMedianAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {

            int radius = 1;
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, "Enter filter radius", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            System.out.println("Median Filter called");
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    public class FilterGaussianAction extends ImageAction {

        FilterGaussianAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
            super(text, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Gaussian blur called");
        }

    }

}
