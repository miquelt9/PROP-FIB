package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the document looked for is not found.
 */
public class notFoundDocument extends Exception {
    public notFoundDocument(String title, String author) {

        super("The author " + author + " does not create a Document with title " + title + ".");
    }
}
