import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws Exception{
        String input = """
Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi                
                """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day12.txt"));

        List<String> list = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            if (line.isBlank()) continue;
            list.add(line);
        }

        int[] elevGrid = new int[list.size() * list.get(0).length()];

        int i = 0;
        int x = 0;
        int y = 0;

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        for (String s : list) {
            String[] letter = s.split("");
            for (String l : letter) {
                if (l.equals("S")) {
                    elevGrid[i] = 0;
                    startX = x;
                    startY = y;
                } else if (l.equals("E")) {
                    elevGrid[i] = 25;
                    endX = x;
                    endY = y;
                } else {
                    elevGrid[i] = l.getBytes()[0] - 97;
                }
                i++;
                x++;
            }
            x = 0;
            y++;
        }

        Collection<Path> currentPaths = new ArrayList<>();
        List<Path> finalPaths = new ArrayList<>();
        for (int y1 = 0; y1 < list.size(); y1++) {
            for (int x1 = 0; x1 < list.size(); x1++) {
                if (elevGrid[y1 * list.get(0).length() + x1] == 0) {
                    currentPaths.add(new Path(x1, y1, list.get(0).length(), list.size()));
                }
            }
        }

        Set<Integer> visited = new HashSet<>();
        while (!currentPaths.isEmpty()) {
            Map<Integer, Path> newPaths = new HashMap<>();
            for (Path p : currentPaths) {
                if (p.foundEnd(endX, endY)) {
                    finalPaths.add(p);
                    break;
                } else {
                    for (Path p1 : p.getNewPaths(elevGrid, visited)) {
                        newPaths.put(p1.getId(), p1);
                    }
                }
                visited.add(p.getId());
            }
            currentPaths = newPaths.values();
        }

        finalPaths.sort(Comparator.comparingInt(Path::getLength));

        System.out.println(finalPaths.get(0).getLength());
    }
}
