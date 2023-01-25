import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultiRunner implements Runnable {
    private static int maxValue = 4_000_001;
    //private static int maxValue = 21;
    private List<Sensor> sensors;
    private int y;

    public MultiRunner(List<Sensor> sensors, int y) {
        this.sensors = sensors;
        this.y = y;
    }

    @Override
    public void run() {

        List<Sensor> sortedSensors = new ArrayList<>();
        sortedSensors.addAll(sensors);
        sortedSensors.sort(Comparator.comparingInt(a -> a.yDistance(y)));

        main2:
        for (int x = 0; x < maxValue; x++) {
            for (Sensor s : sortedSensors) {
                if (s.onLine(x, y) || s.shorterManhattan(x, y)) {
                    continue main2;
                }
            }
            System.out.println(x + " x " + y);
            System.out.println(4000000 * x + y);
        }
//        System.out.print(".");
        if (y % 1000 == 0) {
            System.out.print(".");
        }
    }
}

