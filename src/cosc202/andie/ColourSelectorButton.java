package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class ColourSelectorButton {
    private static Color drawingColour;
    private WheelIcon icon;

    public ColourSelectorButton() {
        drawingColour = Color.black;
        icon = new WheelIcon(20, 20);
    }

    public static Color getColor() {
        Color colour = drawingColour;
        return colour;
    }
    
    public JButton createButton() {
        JButton button = new JButton(icon);
        button.addActionListener(new ColourWheelListener());

        // Credit to https://stackoverflow.com/questions/4585867/transparent-jbutton
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }


    private class ColourWheelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            drawingColour = JColorChooser.showDialog(null, "Select a colour", drawingColour);   
        }
    }

    // Adapted from https://stackoverflow.com/questions/49795526/is-it-possible-to-create-an-imageicon-with-path2d-double 
    private class WheelIcon implements Icon {
        private Rectangle rectangle;

        public WheelIcon(int width, int height) {
            rectangle = new Rectangle(width, height);
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(drawingColour);
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            g2.fill(transform.createTransformedShape(rectangle));
        }
        
        public int getIconWidth() {
            return rectangle.width;
        }

        public int getIconHeight() {
            return rectangle.height;
        }
    }

}
