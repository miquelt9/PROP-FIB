package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the title field is empty.
 */
public class emptyTitle extends Exception {
    public emptyTitle() {

        super("The title field is empty");

    }
}
