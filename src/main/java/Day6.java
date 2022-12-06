import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 {
    public static void main(String[] args) throws Exception{
        String input = """
mjqjpqmgbljsphdztnvjfqwrcgsmlb
bvwbjplbgvbhsrlpgdmjqwftvncz
nppdvjthqldpwncqszvftbrmjlhg
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw
""";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day6.txt"));

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            String[] characters = line.split("");

            List<String> buffer = new ArrayList<>();

            int idx = 0;
            for (String s : characters) {
                if (buffer.size() > 13) {
                    if (buffer.size() == new HashSet<>(buffer).size()) {
                        break;
                    }
                    buffer.add(s);
                    buffer.remove(0);
                } else {
                    buffer.add(s);
                }
                idx++;
            }

            System.out.println(idx);
        }
    }
}
