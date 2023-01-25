import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valve {
    private static final Pattern pattern = Pattern.compile(
            "Valve ([A-Z][A-Z]) has flow rate=([\\d-]+); tunnel[s]* lead[s]* to valve[s]* ([A-Z, ]+)"
    );
    private String openId;

    private String id;
    private int ppm;
    private List<String> tunnels = new ArrayList<>();
    private Map<String, Integer> connection = new HashMap<>();

    public Valve(String line, String openId) {
        this(line);
        this.openId = openId;
    }

    public Valve(String line) {
        Matcher m = pattern.matcher(line);
        if (m.find()) {
            id = m.group(1);
            ppm = Integer.parseInt(m.group(2));
            for (String tunnel : m.group(3).split(", ")) {
                tunnels.add(tunnel.toLowerCase());
            }
        }
    }

    public String getId() {
        return id;
    }

    public boolean isBroken() {
        return ppm < 1;
    }

    public int getPPM() {
        return ppm;
    }

    public List<String> getTunnels() {
        return tunnels;
    }

    public int connectionSize() {
        return connection.size();
    }

    public void addConnection(int len, Set<String> connections) {
        for (String conn : connections) {
            if (!this.connection.containsKey(conn)) {
                this.connection.put(conn, len);
            }
        }
    }

    public int getSteps(String nextBest) {
        return connection.get(nextBest);
    }

    public String getNextBest(Map<String,Valve> valveMap, Set<String> visited, int minutes) {
        int bestValue = 0;
        String bestOption = null;
        for (Map.Entry<String, Integer> conn : connection.entrySet()) {
            if (visited.contains(conn.getKey())) continue;
            int newMinutes = minutes - (conn.getValue() + 1);
            int valueToTest = valveMap.get(conn.getKey()).getPPM() * newMinutes;
            if (valueToTest > bestValue) {
                bestOption = conn.getKey();
                bestValue = valueToTest;
            }
        }
        return bestOption;
    }

    public String getOpenedId() {
        return this.openId;
    }
}
