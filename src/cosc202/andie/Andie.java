package cosc202.andie;
import cosc202.andie.colour.*;
import cosc202.andie.edit.*;
import cosc202.andie.file.*;
import cosc202.andie.file.FileActions.FileSaveAction;
import cosc202.andie.filter.*;
import cosc202.andie.help.HelpActions;
import cosc202.andie.image.*;
import cosc202.andie.lang.*;
import cosc202.andie.view.*;

import java.awt.*;
import javax.imageio.*;
import javax.swing.*;

import java.awt.event.*;

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
    private static PencilButton pencilButton;
    private static JButton pencilJButton;
    private static JButton selectJButton;
    private static LanguageSupport lang = new LanguageSupport();
    private static JMenuBar menuBar;
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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // The main content area is an ImagePanel
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Add in menus for various types of action the user may perform.
        menuBar = new JMenuBar();

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

        // Adds help menu to JFrame
        HelpActions helpActions = new HelpActions();
        menuBar.add(helpActions.createMenu());

        // Adds the colour wheel for drawing tools to the menu bar
        JSeparator separator = new JSeparator(); // Credit to https://stackoverflow.com/questions/12212254/adding-spacing-between-elements-in-jmenubar for separator idea
        menuBar.add(separator);
        pencilButton = new PencilButton();
        pencilJButton = pencilButton.createButton();
        menuBar.add(pencilJButton);
        ColourSelectorButton colourSelector = new ColourSelectorButton();
        menuBar.add(colourSelector.createButton());

        //Select Button
        SelectButton selectButton = new SelectButton();
        selectJButton = selectButton.createButton();
        menuBar.add(selectJButton);

        
        // Sets the minimum size for warning messages
        // Adapted from https://stackoverflow.com/questions/14299741/setting-size-of-jpanel-or-joptionpane
        UIManager.put("OptionPane.minimumSize",new Dimension(500, 100)); 

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);

        /** Set up and check for KB Shortcuts
         * @author Isaac
         */
        frame.requestFocusInWindow();
        KBShortcuts kbShortcuts = new KBShortcuts();
        frame.addKeyListener(kbShortcuts.getKeyAdapter());
        /** @author Isaac
         * Add a WindowAdapter to detect when the user is trying to close the window */
        frame.addWindowListener(new WindowAdapter() {
            FileActions fileActions = new FileActions();
            FileSaveAction saveAction = fileActions.new FileSaveAction(lang.text("save"),
                null, 
                lang.text("savethefile"), 
                Integer.valueOf(KeyEvent.VK_S));
            /** windowClosing method inside the window listener
             *  on instance that image is open and window is trying to be closed,
             *  Save popup will give you the option to Save, Not Save or Cancel Closing.
             *  If no image is open, this popup will not appear.
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if(saveAction.imageOpen()){
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }else{
                    int option = JOptionPane.showOptionDialog(
                        frame,
                        lang.text("doyouwanttosave"),
                        lang.text("save"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[] {lang.text("save"), lang.text("dontsave"), lang.text("cancel")},
                        lang.text("save")
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        // User wants to save changes
                        saveAction.actionPerformed(null);
                        frame.dispose();
                    } else if (option == JOptionPane.NO_OPTION) {
                        // User does not want to save changes
                        frame.dispose();
                    }
                    // if cancel is selected window will not close
                }
        }});
    }

    /**
     * <p>
     * Reenables draw mode in ANDIE 
     * Used after all drawings have been erased
     * </p>
     * @param e the ActionEvent triggering this method call
     */
    public static void reenableDraw(ActionEvent e) {
        pencilButton.enableDraw(e);
    }

    /**
     * <p>
     * Adds a given array of buttons to ANDIE's menu bar
     * </p>
     * @param buttons the buttons to be added to the menu bar
     */
    public static void addButtonsToMenuBar(JButton[] buttons) {
        for (JButton button : buttons) {
            menuBar.add(button);
        }
        f.pack();
    }

    /**
     * <p>
     * Adds a given button to ANDIE's menu bar
     * </p>
     * @param button the button to be added to the menu bar
     */
    public static void addButtonToMenuBar(JButton button) {
        menuBar.add(button);
        f.pack();
    }

    /**
     * <p>
     * Removes a given array of buttons from ANDIE's menu bar
     * </p>
     * @param buttons the buttons to be removed from the menu bar
     */
    public static void removeButtonsFromMenuBar(JButton[] buttons) {
        for (JButton button : buttons) {
            menuBar.remove(button);
        }
        f.pack();
    }

    /**
     * <p>
     * Removes a given button from ANDIE's menu bar
     * </p>
     * @param button the button to be removed from the menu bar
     */
    public static void removeButtonFromMenuBar(JButton button) {
        menuBar.remove(button);
        f.pack();
    }

    /**
     * <p>
     * Changes the cursor in use to a new cursor 
     * e.g. for drawing with a pencil as the cursor
     * </p>
     * @param c the new cursor to be used
     */
    public static void setCursor(Cursor c) {
        f.setCursor(c);
    }

    /**
     * <p>
     * Sets the ImageIcon of the pencil button
     * </p>
     * @param icon the new icon for the pencil button
     */
    public static void setPencilIcon(ImageIcon icon) {
        pencilJButton.setIcon(icon);
        pencilJButton.repaint();
    }

    /**
     * <p>
     * Sets the ImageIcon of the select button
     * </p>
     * @param icon the new icon for the select button
     */
    public static void setSelectIcon(ImageIcon icon){
        selectJButton.setIcon(icon);
        selectJButton.repaint();
    }

    /**
     * <p>
     * Obtains the current mouse cursor in use
     * </p>
     * @return the cursor
     */
    public static Cursor getCursor() {
        return f.getCursor();
    }

    /**
     * <p>
     * Throws a generic warning when ANDIE encounters an error
     * </p>
     */
    public static void throwGenericError() {
        JOptionPane.showMessageDialog(null, lang.text("genericerrorwarning"),
        lang.text("error"),
        JOptionPane.WARNING_MESSAGE);
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