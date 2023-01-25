import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 {
    public static void main(String[] args) throws Exception{
        String stacksDesc = """
    [D]   
[N] [C]   
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
""";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day5.txt"));

        int stackIndexLine = 8;
        int numStacks = 9;

        Deque<String> stackLines = new ArrayDeque<>();

        List<Deque<String>> stacks = new ArrayList<>();

        int lineCount = 0;
        List<Integer> stackIdx = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : stacksDesc.split("\n")) {

            if (line.isBlank()) continue;

            if(lineCount < stackIndexLine) {
                stackLines.push(line);
                lineCount++;
                continue;
            }

            if (stackIdx.isEmpty()) {
                for (int i = 1; i < numStacks + 1; i++) {
                    stackIdx.add(line.indexOf("" + i));
                }
                lineCount++;
                continue;
            }

            if (stacks.isEmpty()) {
                for (int j = 0; j < numStacks; j++) {
                    stacks.add(new ArrayDeque<>());
                }

                String stackLine;
                do {
                    stackLine = stackLines.pop();
                    for (int j = 0; j < numStacks; j++) {
                        if (stackIdx.get(j) < stackLine.length()) {
                            String boxId = stackLine.substring(stackIdx.get(j), stackIdx.get(j) + 1);
                            if (!boxId.isBlank()) {
                                stacks.get(j).push(boxId);
                            }
                        }
                    }
                } while (!stackLines.isEmpty());
            }

            Pattern movePattern = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");
            Matcher m = movePattern.matcher(line);
            if (m.find()) {
                int numToMove = Integer.parseInt(m.group(1));
                int moveFrom = Integer.parseInt(m.group(2));
                int moveTo = Integer.parseInt(m.group(3));
                Deque<String> cratesMove = new ArrayDeque<>();
                for (int l = 0; l < numToMove; l++) {
                    cratesMove.push(stacks.get(moveFrom - 1).pop());
                }
                for (int l = 0; l < numToMove; l++) {
                    stacks.get(moveTo - 1).push(cratesMove.pop());
                }
            }
            lineCount++;
        }

        for (int k = 0; k < numStacks; k++) {
            System.out.print(stacks.get(k).pop());
        }

        System.out.println("");
    }
}
