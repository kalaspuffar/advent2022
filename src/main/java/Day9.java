import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {


    public static final int gridSize = 1000;

    public static int[] grid = new int[gridSize * gridSize];

    public static int[] posRopX = new int[10];
    public static int[] posRopY = new int[10];

    public static void main(String[] args) throws Exception{

        Arrays.fill(posRopX, 500);
        Arrays.fill(posRopY, 500);

        String input = """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
""";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day9.txt"));

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            String[] move = line.split(" ");
            move(move[0], Integer.parseInt(move[1]));
        }

        int amount = 0;
        for (int i : grid) {
            if (i > 0) {
                amount++;
            }
        }

        System.out.println(amount);
    }


    private static void move(String dir, int amount) {
        for (int i = 0; i < amount; i++) {
            moveAStep(dir);
        }
    }

    private static void moveAStep(String dir) {
        switch (dir) {
            case "U":
                posRopY[0] -= 1;
                break;
            case "D":
                posRopY[0] += 1;
                break;
            case "L":
                posRopX[0] -= 1;
                break;
            case "R":
                posRopX[0] += 1;
                break;
        }

        for (int i = 0; i < posRopX.length - 1; i++) {
            boolean moveX = Math.abs(posRopX[i] - posRopX[i + 1]) > 1;
            boolean moveY = Math.abs(posRopY[i] - posRopY[i + 1]) > 1;
            boolean moveXY = Math.abs(posRopX[i] - posRopX[i + 1]) + Math.abs(posRopY[i] - posRopY[i + 1]) > 2;

            if (moveXY) {
                if (posRopX[i] > posRopX[i + 1]) {
                    posRopX[i + 1]++;
                } else {
                    posRopX[i + 1]--;
                }
                if (posRopY[i] > posRopY[i + 1]) {
                    posRopY[i + 1]++;
                } else {
                    posRopY[i + 1]--;
                }
            } else if (moveX) {
                if (posRopX[i] > posRopX[i + 1]) {
                    posRopX[i + 1]++;
                } else {
                    posRopX[i + 1]--;
                }
            } else if (moveY) {
                if (posRopY[i] > posRopY[i + 1]) {
                    posRopY[i + 1]++;
                } else {
                    posRopY[i + 1]--;
                }
            }

            /*
            if (moveX && moveY) {
                posRopY[i + 1] = posRopY[i];
            } else if (moveX) {
                if (i == 0 && "L".equals(dir)) {
                    posRopX[i + 1] -= 1;
                } else {
                    posRopX[i + 1] += 1;
                }
                posRopY[i + 1] = posRopY[i];
            } else if (moveY) {
                if (i == 0 && "U".equals(dir)) {
                    posRopY[i + 1] -= 1;
                } else {
                    posRopY[i + 1] += 1;
                }
                posRopX[i + 1] = posRopX[i];
            }
            */
        }
        grid[posRopY[9] * gridSize + posRopX[9]] += 1;

        //printGrid();
        //System.out.println("");
    }

    private static void printGrid() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                System.out.print(grid[y * gridSize + x]);
            }
            System.out.println();
        }
    }
}
