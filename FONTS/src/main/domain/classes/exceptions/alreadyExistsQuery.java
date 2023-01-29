package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever a new query is added but the query already
 * exists in the database.
 */
public class alreadyExistsQuery extends Exception {
    public alreadyExistsQuery(String name) {

        super("The query " + name + " already exists");

    }
}
