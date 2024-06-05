/**
 * @author Easton Herbon
 * @version 11.10.2023
 *
 * @brief object is thrown when start() and stop() are not strictly in order
 */


public class ProfilerException extends RuntimeException {
    ProfilerException(String message) {
        super(message);
    }
}// end class