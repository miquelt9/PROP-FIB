package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the format of a new given query is not
 * correct.
 */
public class queryNotCorrect extends Exception {
    public queryNotCorrect() {

        super("The query you introduce is not correct. Please check the query.");
    }
}
