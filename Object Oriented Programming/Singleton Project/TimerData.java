/**
 * @author Easton Herbon
 * @version 11.10.2023
 *
 * @brief used to hold the data related to a singular instance of timer in the profiler
 */

public class TimerData {
    private long totalTime;
    private long startTime;
    private int count;
    private long longestTime;
    private long shortestTime;
    private final java.util.List<DurationEntry> durations;

    public TimerData() {
        totalTime = 0;
        count = 0;
        longestTime = Long.MIN_VALUE;
        shortestTime = Long.MAX_VALUE;
        durations = new java.util.ArrayList<>();
    }

    public void start(String message) {
        startTime = System.currentTimeMillis();
    }

    public void stop(String message) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        totalTime += duration;
        count++;
        longestTime = Math.max(longestTime, duration);
        shortestTime = Math.min(shortestTime, duration);

        durations.add(new DurationEntry(duration, message));
    }

    public int getCount() {
        return count;
    }

    public long getAverageTime() {
        return count > 0 ? totalTime / count : 0;
    }

    public long getLongestTime() {
        return longestTime;
    }

    public long getShortestTime() {
        return shortestTime;
    }

    public java.util.List<DurationEntry> getDurations() {
        return durations;
    }

    public void incrementCount() {
        count++;
    }
}// end class