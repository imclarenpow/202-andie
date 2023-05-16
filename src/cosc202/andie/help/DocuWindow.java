package cosc202.andie.help;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.*;

/**
 * The DocuWindow class displays a JFrame window with help documentation for Andie.
 * 
 * modifications made by ChatGPT:
 * - changed JTextArea to JEditorPane to support HTML formatting
 * - added HTMLKit to the JEditorPane for proper rendering of HTML content
 * - modified updateText() method to set the text of the JEditorPane as HTML
 */
public class DocuWindow extends JFrame implements ActionListener {
    private JEditorPane editorPane;
    private JList<String> categoryList;
    private String[] files = {"FileHelp.html",
        "EditHelp.html", "FilterHelp.html", "ColourHelp.html", "HelpHelp.html", "ViewHelp.html" };
    private String[] category = {"File","Edit","Filter", "Colour", "Help", "View"};
    private String[] categoryText = new String[files.length];

    /** sets up JFrame, currently set size of 600x400 open to change. */
    public DocuWindow() {
        super("Documentation Window");
        fileReader();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        // Creates editor pane
        editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setFont(new Font("Arial", Font.PLAIN, 14));
        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane.setEditorKit(kit);

        // Creates category list
        categoryList = new JList<>(category);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        updateText();
        categoryList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed(null);
            }
        });

        // Add components to window
        JScrollPane scrollPane = new JScrollPane(editorPane);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(categoryList, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        updateText();
    }

    private void updateText() {
        int index = categoryList.getSelectedIndex();
        editorPane.setText(categoryText[index]);
        editorPane.setCaretPosition(0);
    }

    /** Reads the text stored in documentation, as outlined in the private array above.
     *  Spent ages milling over why it wasn't working, had a file mentioned twice. Be sure to Check this in future.
     */
    public void fileReader(){
        for(int i=0; i<files.length; i++){
            categoryText[i] = "";   
            try{
                File f = new File("src/cosc202/andie/help/documentation", files[i]);
                /* added charsetName: "UTF-8" to solve not reading ViewHelp.html due to macron.
                credit to Reuben for helping fing this issue */
                Scanner sc = new Scanner(f, "UTF-8");
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.isEmpty()) {
                        categoryText[i] += line + "<br>";
                    }
                }
                sc.close();
            } catch(IOException e){
                System.out.println("Problem finding " + files[i]);
            }
        }
    }
}
