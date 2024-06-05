import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Easton Herbon
 * @version 11.10.2023
 *
 * @brief used to keep track of the times of execution in the code snippets using the
 * Singleton design pattern.
 * it is also used to generate a professional report through html and JFrame formatting.
 */


public class Profiler {
    private static Profiler instance;
    private final Map<String, TimerData> timers;
    private boolean enabled;

    private Profiler() {
        timers = new HashMap<>();
        enabled = true;
    }

    // **SINGLETON**
    public static Profiler getInstance() {
        if (instance == null) { instance = new Profiler(); }
        return instance;
    }

    public void start(String id) {
        start(id, null);
    }

    public void start(String id, String message) {
        if (!enabled) { return; }

        if (!timers.containsKey(id)) { timers.put(id, new TimerData()); }

        timers.get(id).start(message);
    }

    public void stop(String id) {
        stop(id, null);
    }

    public void stop(String id, String message) {
        if (!enabled) { return; }

        if (!timers.containsKey(id)) { throw new ProfilerException("Profiler stop called without corresponding start for id: " + id); }

        timers.get(id).stop(message);
    }

    public void count(String id) {
        if (!enabled) { return; }

        if (!timers.containsKey(id)) { timers.put(id, new TimerData()); }

        timers.get(id).incrementCount();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void generateReport() {
        if (!enabled) { return; }


        JFrame frame = new JFrame("Profiler Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        for (Map.Entry<String, TimerData> entry : timers.entrySet()) {
            String id = entry.getKey();
            TimerData data = entry.getValue();

            JPanel panel = new JPanel(new BorderLayout());

            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setEditable(false);

            StringBuilder html = new StringBuilder("<html><body>");
            html.append("<h2>").append(id).append("</h2>");
            html.append("<p>Average Time: ").append(data.getAverageTime()).append(" ms</p>");
            html.append("<p>Longest Time: ").append(data.getLongestTime()).append(" ms</p>");
            html.append("<p>Shortest Time: ").append(data.getShortestTime()).append(" ms</p>");

            html.append("<h3>Durations:</h3>");
            for (DurationEntry durationEntry : data.getDurations()) {
                html.append("<p>").append(durationEntry.duration()).append(" ms - ").append(durationEntry.message()).append("</p>");
            }

            html.append("<h3>Counter Information:</h3>");
            html.append("<p>Count: ").append(data.getCount()).append("</p>");

            html.append("</body></html>");

            textPane.setText(html.toString());

            JScrollPane scrollPane = new JScrollPane(textPane);
            panel.add(scrollPane, BorderLayout.CENTER);

            tabbedPane.addTab(id, panel);
        }

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}// end class