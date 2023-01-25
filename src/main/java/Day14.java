import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day14 {
    private static int offsetX = 200;
    private static int maxX = 600;
    private static int maxY = 200;
    private static int[] map = new int[maxX * maxY];


    private static int findMaxY = 0;

    public static void main(String[] args) throws Exception{
        String input = """
498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
                    """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day14.txt"));
        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            String[] coords = line.trim().split(" -> ");
            for (int i = 0; i < coords.length - 1; i++) {
                drawCoord(coords[i], coords[i+1]);
            }
        }

        System.out.println(findMaxY);
        //drawCoord("400,11", "599,11");
        drawCoord(offsetX + ",176", (offsetX + maxX - 1) + ",176");

        int sandCount = 0;
        while (true) {
            int x = 500 - offsetX;
            int y = 0;
            boolean atRest = false;
            while (!atRest && x > 0 && x < maxX && y < 199) {
                if (map[(y + 1) * maxX + x] == 0) {
                    y++;
                } else if (map[(y + 1) * maxX + x - 1] == 0) {
                    y++;
                    x--;
                } else if (map[(y + 1) * maxX + x + 1] == 0) {
                    y++;
                    x++;
                } else {
                    atRest = true;
                }
            }

            if (x > 0 && x < maxX && y > 0 && y < 199) {
                map[y * maxX + x] = 2;
            } else {
                break;
            }
            sandCount++;

        }

        drawMap();

        System.out.println(sandCount);
    }

    private static void drawMap() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                String draw = ".";
                if (map[(y * maxX) + x] == 1) {
                    draw = "#";
                } else if (map[(y * maxX) + x] == 2) {
                    draw = "o";
                }
                System.out.print(draw);
            }
            System.out.println();
        }
    }

    private static void drawCoord(String from, String to) {
        String[] fromXY = from.trim().split(",");
        String[] toXY = to.trim().split(",");

        int fromX = Math.min(Integer.parseInt(fromXY[0]), Integer.parseInt(toXY[0]));
        int fromY = Math.min(Integer.parseInt(fromXY[1]), Integer.parseInt(toXY[1]));
        int toX = Math.max(Integer.parseInt(fromXY[0]), Integer.parseInt(toXY[0]));
        int toY = Math.max(Integer.parseInt(fromXY[1]), Integer.parseInt(toXY[1]));

        findMaxY = Math.max(findMaxY, toY);

        if (fromX == toX) {
            for (int i = fromY; i < toY + 1; i++) {
                map[(i * maxX) + (fromX - offsetX)] = 1;
            }
        } else {
            for (int i = fromX; i < toX + 1; i++) {
                map[(fromY * maxX) + (i - offsetX)] = 1;
            }
        }
    }


}
