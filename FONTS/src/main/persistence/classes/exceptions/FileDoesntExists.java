package src.main.persistence.classes.exceptions;

/**
 * This exception is thrown whenever the user tries to upload
 * 
 * @author Walter J.T.V
 */
public class FileDoesntExists extends Exception {
    public FileDoesntExists(String Path) {

        super("Such file located in " + Path + "doesn't appear to exist (Maybe is a folder?)");
    }
}
