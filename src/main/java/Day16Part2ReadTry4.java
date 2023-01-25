import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class Day16Part2ReadTry4 {
    public static void main(String[] args) throws Exception {
        String input = """
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II                        
                        """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day16.txt"));

        Map<String, Valve> valveMap = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //String line;
        //while ((line = br.readLine()) != null) {
        for (String line : input.split("\n")) {
            Valve v = new Valve(line);
            valveMap.put(v.getId(), v);
        }

        String dataPath = "data";

        for (int file = 0; file > -1; file--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + file + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));

            int numOpen = 0;
            long largest = 0;
            String largestStr = "";
            String pos;

            while ((pos = br2.readLine()) != null) {
                long size = Long.parseLong(pos.split("\\|")[0]);

                if (size > largest) {
                    largest = size;
                    largestStr = pos;
                }
            }
            br2.close();

            System.out.println("---------------------------------");
            System.out.println("Time      : " + sdf.format(new Date()));
            System.out.println("Filenumber: " + file);
            System.out.println("NumberOpen: " + numOpen);
            System.out.println("Largest   : " + largest);
            System.out.println("LargestStr: " + largestStr);
        }
    }
}
