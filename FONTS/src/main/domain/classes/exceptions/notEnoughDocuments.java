package src.main.domain.classes.exceptions;

/**
 * This exception is thrown whenever there are not enough documents to return.
 */
public class notEnoughDocuments extends Exception {
    private int maxDocs;

    public notEnoughDocuments(int max) {

        super("There are not enough documents on the system to perform the query");
        maxDocs = max;

    }

    public int getMaxDocs() {
        return maxDocs;
    }
}
