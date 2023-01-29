package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever a new document is created but it already
 * exists in the database.
 */
public class alreadyExistsDoc extends Exception {
    public alreadyExistsDoc(String title, String author) {

        super(author + " already has a document with title " + title + ".");

    }
}
