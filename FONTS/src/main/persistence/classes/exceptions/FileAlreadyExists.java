package src.main.persistence.classes.exceptions;

/**
 * This exception is thrown whenever the user tries to download a file that
 * already exists with the same name in that directory
 * 
 * @author Walter J.T.V
 */
public class FileAlreadyExists extends Exception {
    public FileAlreadyExists(String Path, String Name) {

        super("The file with name " + Name + " already exists in the directory " + Path);
    }
}
