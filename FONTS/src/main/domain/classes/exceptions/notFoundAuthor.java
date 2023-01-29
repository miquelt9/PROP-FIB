package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the author looked for is not found.
 */
public class notFoundAuthor extends Exception {
    public notFoundAuthor(String author) {

        super("The author with name " + author + " does not exist.");

    }
}
