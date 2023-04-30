package cosc202.andie;
import cosc202.andie.lang.*;

import java.awt.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class Andie {
    private static JFrame f;
    private static JButton pencilJButton;

    // Sets the maximum dimension of images for resize 
    public static final double MAX_DIMENSION_LIMIT = 20000;
    
    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented {@link ImageOperation}s and triggerd via 
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * 
     * @throws Exception if something goes wrong.
     */
  /** Closes and Reopens an instance of ANDIE, as to reset the language in the current session */
    public void langSet(){  
        try{
            f.dispose();
            createAndShowGUI();
        }catch(Exception ex){
            System.out.println("Error while running langSet");
        }
    }
    private static void createAndShowGUI() throws Exception {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");
        f = frame;
        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();

        // File menus are pretty standard, so things that usually go in File menus go here.
        FileActions fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());

        // View actions control how the image is displayed, but do not alter its actual content
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());

        // Filters apply a per-pixel operation to the image, generally based on a local window
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());

        // Actions that affect the representation of colour in the image
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());

        // Adds the colour wheel for drawing tools to the menu bar
        JSeparator separator = new JSeparator(); // Credit to https://stackoverflow.com/questions/12212254/adding-spacing-between-elements-in-jmenubar for separator idea
        menuBar.add(separator);
        PencilButton pencilButton = new PencilButton();
        pencilJButton = pencilButton.createButton();
        menuBar.add(pencilJButton);
        ColourSelectorButton colourSelector = new ColourSelectorButton();
        menuBar.add(colourSelector.createButton());
        
        // Sets the minimum size for warning messages
        // Adapted from https://stackoverflow.com/questions/14299741/setting-size-of-jpanel-or-joptionpane
        UIManager.put("OptionPane.minimumSize",new Dimension(500, 100)); 

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public static void setCursor(Cursor c) {
        f.setCursor(c);
    }

    public static void setPencilIcon(ImageIcon icon) {
        pencilJButton.setIcon(icon);
        pencilJButton.repaint();
    }

    public static Cursor getCursor() {
        return f.getCursor();
    }

    /**
     * <p>
     * Fits ANDIE's window size to the current image size
     * </p>
     * 
     * <p>
     * Updates the dimensions JFrame representing the window of the active ANDIE instance
     * according to the dimensions of the current image being displayed.
     * If the image is too large, the window will instead be set to the maximum size (the computer's screen resolution).
     * </p>
     * 
     * @param size the size of the image
     */
    public static void resizeWindowToImage(Dimension size) {
        if (size.width > Toolkit.getDefaultToolkit().getScreenSize().getWidth() || size.height > Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
            size = Toolkit.getDefaultToolkit().getScreenSize();
        }
        f.setSize(size);
    }

    /**
     * <p>
     * Returns the dimensions of ANDIE's current window
     * </p>
     * 
     * <p>
     * A method to retrieve the width and height of the JFrame containing the
     * current ANDIE instance, i.e. the dimensions of the window currently
     * being used for ANDIE. The method returns the resulting dimensions as
     * a new Dimension object.
     * </p>
     * 
     * @return A Dimension representing the size of the frame (includes width and height)
     */
    public static Dimension getFrameSize() {
        double width = f.getContentPane().getWidth() - f.getJMenuBar().getHeight();
        double height = f.getContentPane().getHeight() - f.getJMenuBar().getHeight();
        return new Dimension((int)Math.round(width), (int)Math.round(height));
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used
     * @throws Exception If something goes awry
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {
        LanguageSupport lang = new LanguageSupport();
        lang.loadDefaultLanguage();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
