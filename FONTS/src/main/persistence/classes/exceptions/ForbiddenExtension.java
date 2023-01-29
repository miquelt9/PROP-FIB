package src.main.persistence.classes.exceptions;

/**
 * Exception used in multiple cases where the format is not supported by this
 * program (sus,txt,xml)
 * 
 * @author Walter J.T.V
 */
public class ForbiddenExtension extends Exception {
    public ForbiddenExtension(String Extension) {

        super("The file extension : " + Extension + " is not currently supported");
    }
}
