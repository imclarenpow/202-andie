/** Class by Isaac
 *  WORK IN PROGRESS!
 */
package cosc202.andie.lang;
//importing utilities to support internationalisation
import java.util.prefs.Preferences;
import java.util.Locale;
import java.util.ResourceBundle;

import cosc202.andie.Andie;
/** Things that need to be implemented: 
 *  1. Need to figure out how to make it so that when the
 *    language is selected the program starts using the new
 *    languages for the jFrame
 *  2. Need to add one more language (easy)
 *  3. Commenting and removing broken code
 *  4. Deciding whether or not the language dropdown menu
 *     Should be in the programs default or should be in en
 */

 /** Class that allows the implementation of different languages into ANDIE */
 public class LanguageSupport {
    //placeholder String for better implementation later on
    //private String language = "_en_NZ";
    //for now, adds the string above language to the end of this string (will change when figure out how to set default)
    private ResourceBundle bundle = ResourceBundle.getBundle("cosc202.andie.lang.MessageBundle");
    private Preferences prefs = Preferences.userNodeForPackage(Andie.class);
    // constructor that sets default (currently not working) language string overrides
    public LanguageSupport(){
        
    }
    //deals with replacing the text into the wanted language
    public String text(String txt){
        return bundle.getString(txt);
    }
    public void setLanguage(String languageCode, String countryCode) {
        // Set the default Locale based on the language code
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);
    
        // Store the language preference using Preferences
        prefs = Preferences.userNodeForPackage(Andie.class);
        prefs.put("language", languageCode);
        prefs.put("country", "countryCode");
    }
    //method that will be used later to change the language in the settings
   /* public void setLang(String lang){
        //deleting previous default file
        
            File langFile = new File("langDefault.txt");
            if(langFile.delete()){
                System.out.println("langDefault.txt deleted");
            }
        try{
            File langWrite = new File("langDefault.txt");
            if(langWrite.createNewFile()){
                System.out.println("New langDefault.txt created");
            }else{
                System.out.println("langDefault.txt was not deleted in previous step");
            }
            }catch(IOException k){
                System.out.println("Couldn't create file");
                k.printStackTrace();
            }
        try{
            FileWriter newDef = new FileWriter("langDefault.txt");
            newDef.write(lang);
            newDef.close();
            }catch(IOException e){
                System.out.println("Error Writing new default");
                e.printStackTrace();
            }
        checkLang();
        
    }
    public void checkLang(){
        try{
            File f = new File("langDefault.txt");
            Scanner sc = new Scanner(f);
            String l = sc.nextLine();
            language = l;
            sc.close();
        }catch(IOException e){
            System.out.println("Couldn't set lang string");
        }
        Andie.langSet();
    }*/
}

