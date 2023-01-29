package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever the query looked for is not found.
 */
public class notFoundQuery extends Exception {

    public notFoundQuery(String name) {

        super("The query " + " does not exist");

    }
}
