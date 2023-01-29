package src.main.persistence.classes.exceptions;

/**
 * This exception is thrown whenever the user tries to upload a file and the
 * document already exits in the system
 * 
 * @author Walter J.T.V
 */
public class DocumentAlreadyExists extends Exception {
    public DocumentAlreadyExists(String title, String author) {

        super("The Document with title: " + title + " and author: " + author);
    }
}