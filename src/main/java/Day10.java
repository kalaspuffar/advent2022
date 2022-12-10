import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws Exception{
        String input = """
addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop                
                """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day10.txt"));

        List<Integer> stops = new ArrayList<>();
        stops.add(20);
        stops.add(60);
        stops.add(100);
        stops.add(140);
        stops.add(180);
        stops.add(220);

        long sum = 0;
        int cycleCounter = 0;
        long currentValue = 1;

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            if ("noop".equals(line)) {
                cycleCounter++;
                if (stops.contains(cycleCounter)) {
                    sum += cycleCounter * currentValue;
                }
                drawCycle(cycleCounter, currentValue);
            }
            if (line.startsWith("addx")) {
                cycleCounter ++;
                if (stops.contains(cycleCounter)) {
                    sum += cycleCounter * currentValue;
                }
                drawCycle(cycleCounter, currentValue);
                cycleCounter ++;
                if (stops.contains(cycleCounter)) {
                    sum += cycleCounter * currentValue;
                }
                drawCycle(cycleCounter, currentValue);
                currentValue += Integer.parseInt(line.split(" ")[1]);
            }
        }
        System.out.println(sum);
    }

    private static void drawCycle(int cycleCounter, long currentValue) {
        int currentPos = (cycleCounter % 40);

        if (currentPos < currentValue || currentPos > currentValue + 2) {
            System.out.print(".");
        } else {
            System.out.print("#");
        }
        if (currentPos == 0) {
            System.out.println();
        }
    }
}
