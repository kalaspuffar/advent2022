import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public static void main(String[] args) throws Exception {
        String input = """
30373
25512
65332
33549
35390
""";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day8.txt"));

        List<String> rows = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            rows.add(line);
        }

        int numRows = rows.size();
        int numCols = rows.get(0).length();

        int[][] grid = new int[numRows][];

        int i = 0;
        for (String row : rows) {
            grid[i] = new int[numCols];
            String[] rowArr = row.split("");
            int j = 0;
            for (String colStr : rowArr) {
                grid[i][j] = Integer.parseInt(colStr);
                j++;
            }
            i++;
        }

        int count = 0;
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                count += visible(grid, x, y) ? 1 : 0;
            }
        }

        System.out.println(count);



        int largestScenic = 0;
        for (int y = 0; y < numRows; y++) {
            for (int x = 0; x < numCols; x++) {
                int newScenic = getScenic(grid, x, y);
                if (newScenic > largestScenic) {
                    largestScenic = newScenic;
                }
            }
        }

        System.out.println(largestScenic);
    }

    public static final int east = 0;
    public static final int west = 1;
    public static final int north = 2;
    public static final int south = 3;


    private static int getScenic(int[][] grid, int x, int y) {
        int eastVal = numSeen(grid, x - 1, y, grid[y][x], east);
        int westVal = numSeen(grid, x + 1, y, grid[y][x], west);
        int northVal = numSeen(grid, x, y - 1, grid[y][x], north);
        int southVal = numSeen(grid, x, y + 1, grid[y][x], south);

        return eastVal * westVal * northVal * southVal;
    }

    public static int numSeen(int[][] grid, int x, int y, int val, int dir) {
        switch (dir) {
            case east:
                if (x > -1) {
                    if (val > grid[y][x]) {
                        return 1 + numSeen(grid, x - 1, y, val, dir);
                    }
                    return 1;
                }
                return 0;
            case west:
                if (x < grid[0].length) {
                    if (val > grid[y][x]) {
                        return 1 + numSeen(grid, x + 1, y, val, dir);
                    }
                    return 1;
                }
                return 0;
            case north:
                if (y > -1) {
                    if (val > grid[y][x]) {
                        return 1 + numSeen(grid, x, y - 1, val, dir);
                    }
                    return 1;
                }
                return 0;
            case south:
                if (y < grid.length) {
                    if (val > grid[y][x]) {
                        return 1 + numSeen(grid, x, y + 1, val, dir);
                    }
                    return 1;
                }
                return 0;
        }
        return 0;
    }



    public static int largest(int[][] grid, int x, int y, int dir) {
        switch (dir) {
            case east:
                if (x > -1) {
                    int val = largest(grid, x - 1, y, dir);
                    if (val > grid[y][x]) {
                        return val;
                    } else {
                        return grid[y][x];
                    }
                } else {
                    return -1;
                }
            case west:
                if (x < grid[0].length) {
                    int val = largest(grid, x + 1, y, dir);
                    if (val > grid[y][x]) {
                        return val;
                    } else {
                        return grid[y][x];
                    }
                } else {
                    return -1;
                }
            case north:
                if (y > -1) {
                    int val = largest(grid, x, y - 1, dir);
                    if (val > grid[y][x]) {
                        return val;
                    } else {
                        return grid[y][x];
                    }
                } else {
                    return -1;
                }
            case south:
                if (y < grid.length) {
                    int val = largest(grid, x, y + 1, dir);
                    if (val > grid[y][x]) {
                        return val;
                    } else {
                        return grid[y][x];
                    }
                } else {
                    return -1;
                }
        }
        return -1;
    }

    public static boolean visible(int[][] grid, int x, int y) {
        if (largest(grid, x - 1, y, east) < grid[y][x]) {
            return true;
        }
        if (largest(grid, x + 1, y, west) < grid[y][x]) {
            return true;
        }
        if (largest(grid, x, y - 1, north) < grid[y][x]) {
            return true;
        }
        if (largest(grid, x, y + 1, south) < grid[y][x]) {
            return true;
        }
        return false;
    }
}
