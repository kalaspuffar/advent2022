import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sensor {
    private int x = 0;
    private int y = 0;
    private int beaconX = 0;
    private int beaconY = 0;
    private int manhattan = 0;

    private static Pattern sensorPattern = Pattern.compile(
        "Sensor at x=([\\d-]+), y=([\\d-]+): closest beacon is at x=([\\d-]+), y=([\\d-]+)"
    );

    //Sensor at x=20, y=1: closest beacon is at x=15, y=3
    public Sensor(String line) {
        Matcher m = sensorPattern.matcher(line);
        if (m.find()) {
            x = Integer.parseInt(m.group(1));
            y = Integer.parseInt(m.group(2));
            beaconX = Integer.parseInt(m.group(3));
            beaconY = Integer.parseInt(m.group(4));

            manhattan = Math.abs(x - beaconX) + Math.abs(y - beaconY);
        }
    }

    public int minX() {
        return x - manhattan;
    }

    public int maxX() {
        return x + manhattan;
    }

    public boolean shorterManhattan(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y) <= manhattan;
    }

    public boolean onLine(int x, int y) {
        return this.beaconX == x && this.beaconY == y;
    }

    public int yDistance(int y) {
        return Math.abs(this.y - y);
    }
}
