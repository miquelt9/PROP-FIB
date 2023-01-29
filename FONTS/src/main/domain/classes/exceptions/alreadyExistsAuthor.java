package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever trying to add a new author that is already
 * on the database.
 */
public class alreadyExistsAuthor extends Exception {
    public alreadyExistsAuthor(String author) {

        super(author + " already exists.");

    }
}
