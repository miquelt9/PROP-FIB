package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever an documents search by author is done but
 * the author doesn't have documents.
 */
public class authorWithoutDocs extends Exception {
    public authorWithoutDocs(String author) {
        super(author + " doesn't have any documents");
    }

}
