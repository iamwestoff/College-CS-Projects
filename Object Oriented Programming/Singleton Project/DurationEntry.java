/**
 * @author Easton Herbon
 * @version 11.10.2023
 *
 * @brief used to record a singular duration in the profiler
 */

public record DurationEntry(long duration, String message) {

    @Override
    public String message() {
        return message == null ? "" : message;
    }
}// end class

/* OLD CODE CONVERTED TO RECORD
public class DurationEntry {
    private final long duration;
    private final String message;

    public DurationEntry(long duration, String message) {
        this.duration = duration;
        this.message = message;
    }

    public long getDuration() {
        return duration;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }
}
 */