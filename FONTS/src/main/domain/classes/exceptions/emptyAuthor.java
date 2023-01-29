package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the field of author is empty.
 */
public class emptyAuthor extends Exception {
    public emptyAuthor() {

        super("The author field is empty");

    }
}
