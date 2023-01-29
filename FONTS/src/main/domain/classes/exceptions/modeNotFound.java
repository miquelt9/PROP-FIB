package src.main.domain.classes.exceptions;

/**
 * This exception is thrown the looked for node is not found.
 */
public class modeNotFound extends Exception {
    public modeNotFound(String mode) {

        super("The mode " + mode + " is not in the configuration.");

    }
}
