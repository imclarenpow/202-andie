package cosc202.andie;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * 
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various image editing and processing operations.
 * 
 * @author Steven Mills
 * @version 0.0
 */
public class Andie {

    /**
     * Launches the main GUI for the ANDIE program.
     * 
     * This method sets up an interface consisting of an active image (an {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save, edit, etc. 
     * These operations are implemented as {@code ImageAction}s grouped by their general purpose into menus.
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     */
    private static void createAndShowGUI() {
        // Set up the main GUI frame
        JFrame frame = new JFrame("ANDIE");
        try {
            Image image = ImageIO.read(new File("./src/icon.png"));
            frame.setIconImage(image);
        } catch (Exception e) {
            System.err.println("Failed to load ANDIE icon");
        }
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
        
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);


    }
/**
 * Main entry point to the ANDIE program.
 * 
 * Creates and launches the main GUI in a separate thread.
 * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
 * 
 * @param args - command line arguments, not currently used
 * @throws Exception - if something goes awry
 * @see #createAndShowGUI()
 */
    public static void main(String[] args) throws Exception {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
