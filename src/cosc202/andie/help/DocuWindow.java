package cosc202.andie.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The DocuWindow class displays a JFrame window with help documentation for Andie.
 * @author Isaac Powell
 * @version 1.2
 * 
 * current issues:
 * - when first opened, file text does not appear until selecting another category
 * - issues unknown regarding ViewHelp.txt, just this file, currently the break is saving the
 *   method from throwing an exception.
 * 
 * additions in ver 2.0:
 * - add html text interface for indents, bolding changing size of text etc.
 * - add internationalisation support (using the language file to select which file is shown)
 *      (will require subdirectories once again)
 * - in the future documentation will tell the user what each of the keyboard shortcuts are
 */
public class DocuWindow extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JList<String> categoryList;
    private String[] files = {"FileHelp.txt",
        "EditHelp.txt", "FilterHelp.txt", "ColourHelp.txt", "HelpHelp.txt", "ViewHelp.txt" };
    private String[] category = new String[files.length];
    private String[] categoryText = new String[files.length];

    /** sets up JFrame, currently set size of 600x400 open to change. */
    public DocuWindow() {
        super("Documentation Window");
        fileReader();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        // Creates text area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Creates category list
        categoryList = new JList<>(category);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        categoryList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateText();
            }
        });

        // Add components to window
        JScrollPane scrollPane = new JScrollPane(textArea);
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
        textArea.setText(categoryText[index]);
    }
/** Reads the text stored in documentation, as outlined in the private array above.
 *  Spent ages milling over why it wasn't working, had a file mentioned twice. Be sure to Check this in future.
 */
    public void fileReader(){
        for(int i=0; i<files.length; i++){
            categoryText[i] = "";   
            try{
                File f = new File("src/cosc202/andie/help/documentation", files[i]);
                Scanner sc = new Scanner(f);
                if(sc.hasNextLine()){
                    category[i] = sc.nextLine();
                }else{
                    break;
                }
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.isEmpty()) {
                        categoryText[i] += line + "\n";
                    }
                }
                sc.close();
            } catch(IOException e){
                System.out.println("Problem finding " + files[i]);
            }
        }
        
    }
}
