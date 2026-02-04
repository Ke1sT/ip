package Ada;

/**
 * Application-specific checked exception used to signal user-facing errors.
 */
public class AdaException extends Exception {
    /**
     * Creates a new exception with a detail message.
     *
     * @param message detail message shown to the user
     */
    public AdaException(String message) {
        super(message);
    }
}
