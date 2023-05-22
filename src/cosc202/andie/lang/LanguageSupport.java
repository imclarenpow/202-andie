/** @author Isaac
 */
package cosc202.andie.lang;
//importing utilities to support internationalisation
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cosc202.andie.Andie;

 /** Class that allows the implementation of different languages into ANDIE */
 public class LanguageSupport {

    private String defLang = "language.txt";
    private ResourceBundle bundle = ResourceBundle.getBundle("cosc202.andie.lang.MessageBundles.MessageBundle");
    
    /** Constructor that sets default (currently not working) language string overrides */
    public LanguageSupport(){
        loadDefaultLanguage();
    }
    /** Deals with replacing the text into the wanted language */
    public String text(String txt){
        return bundle.getString(txt);
    }
    /** Method that sets the default language using a file reader so that the default is remembered
     *  for next time the program is run.
     */
    public void setDefaultLanguage(String languageCode, String countryCode) {
        // Set the default Locale based on the language code
        Locale newLocale = new Locale(languageCode, countryCode);
        Locale.setDefault(newLocale);
        
        // Store the language preference in a file
        write(languageCode, countryCode);
        Andie a = new Andie();
        a.langSet();
    }
    /** Runs when ANDIE Starts, this checks what the current default is */
    public void loadDefaultLanguage() {
        // Load the language preference from the file
        try (Scanner sc = new Scanner(new File(defLang))) {
            String languageCode = sc.nextLine();
            String countryCode = sc.nextLine();
            Locale newLocale = new Locale(languageCode, countryCode);
            Locale.setDefault(newLocale);
        } catch (Exception e) {
            try (Scanner sc = new Scanner(new File("src/cosc202/andie/lang/" + defLang))) {
                String languageCode = sc.nextLine();
                String countryCode = sc.nextLine();
                Locale newLocale = new Locale(languageCode, countryCode);
                Locale.setDefault(newLocale);
            } catch (Exception ex) {
                write();
                e.printStackTrace();
            }
        }
    }
    //default write script
    public void write() {
        // makes english the language if no default file is found
        System.out.println(System.getProperty("user.dir") + "\\" + defLang);
        try (FileWriter fr = new FileWriter(System.getProperty("user.dir") + "\\" + defLang)) {
            fr.write("en");
            fr.write("\n");
            fr.write("NZ");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Andie a = new Andie();
        a.langSet();
        loadDefaultLanguage();
    }
    //specified write script
    public void write(String languageCode, String countryCode) {
        // makes english the language if no default file is found

        try (PrintWriter fr = new PrintWriter(defLang)) {
            fr.println(languageCode);
            fr.println(countryCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter fr = new PrintWriter("src/cosc202/andie/lang/"+defLang)) {
            fr.println(languageCode);
            fr.println(countryCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Andie a = new Andie();
        a.langSet();
        loadDefaultLanguage();
    }
    /* added so DocuWindow is able to get the language code to change the directory
        to the correct language for the documentation popup */
    public String langCode(){
        return Locale.getDefault().getLanguage();
    }
 }
    

