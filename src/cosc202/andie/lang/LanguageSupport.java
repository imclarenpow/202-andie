/** Class by Isaac
 *  WORK IN PROGRESS!
 */
package cosc202.andie.lang;
//importing utilities to support internationalisation
import java.util.prefs.Preferences;
import java.util.Locale;
import java.util.ResourceBundle;
/** Things that need to be implemented: 
 * 1. Allow the language to be changed to default value (so that it doesn't start up in english every time after closing)
 * 2. Decide on which Action toolbar to add changing the language into
 * 3. Change code so that it can implement the system default language and remember the user's setting (perhaps a text file? or something better)
 * 4. Add more language options (easy)
 */

 /** Class that allows the implementation of different languages into ANDIE */
 public class LanguageSupport {
    //placeholder String for better implementation later on
    private String language = "_en_NZ";
    //for now, adds the string above language to the end of this string (will change when figure out how to set default)
    private ResourceBundle bundle = ResourceBundle.getBundle("cosc202.andie.lang.MessageBundle" + language);
    private Preferences prefs = Preferences.userNodeForPackage(LanguageSupport.class);
    
    // constructor that sets default (currently not working) language string overrides
    public LanguageSupport(){
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        prefs.put("language", "mi");
        prefs.put("country", "NZ");
    }
    //deals with replacing the text into the wanted language
    public String text(String txt){
        return bundle.getString(txt);
    }
    //method that will be used later to change the language in the settings
    public void setLang(String lang){
        language = lang;
    }
}
