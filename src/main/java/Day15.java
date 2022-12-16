import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Day15 {
    public static void main(String[] args) throws Exception{
        String input = """
Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
                    """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day15.txt"));

        List<Sensor> sensors = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            sensors.add(new Sensor(line));
        }

        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (Sensor s : sensors) {
            minX = Math.min(s.minX(), minX);
            maxX = Math.max(s.maxX(), maxX);
        }
/*
        int count = 0;
        //int y = 10;
        int y = 2_000_000;

        main:
        for (int x = minX - 1; x < maxX + 1; x++) {
            for (Sensor s : sensors) {
                if (s.onLine(x, y)) {
                    //System.out.print(".");
                    continue main;
                }
            }

            boolean found = false;
            for (Sensor s : sensors) {
                if (s.shorterManhattan(x, y)) {
                    found = true;
                    count++;
                    break;
                }
            }
            //System.out.print(found ? "#" : ".");
        }

        System.out.println(count);
*/
        int maxValue = 4000000;
        //int maxValue = 21;

        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int y1 = maxValue; y1 > -1; y1--) {
            es.submit(new MultiRunner(sensors, y1));
        }
        es.wait();

/*
        for (int y1 = 0; y1 < maxValue; y1++) {
            main2:
            for (int x = 0; x < maxValue; x++) {
                for (Sensor s : sensors) {
                    if (s.onLine(x, y1)) {
                        continue main2;
                    }
                }
                boolean found = false;
                for (Sensor s : sensors) {
                    if (s.shorterManhattan(x, y1)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(x + " x " + y1);
                    System.out.println(4000000 * x + y1);
                    break main2;
                }
            }
            System.out.print(".");
        }
*/

    }
}
