package edith.exception;

/**
 * Represents exceptions that are specific to Edith.
 */
public class EdithException extends Exception {
    /**
     * Constructs an EdithException with the error message.
     *
     * @param message Description of the error.
     */
    public EdithException(String message) {
        super(message);
    }
}
