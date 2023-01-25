import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

public class Day16Part2ReadTry2 {
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


        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            Valve v = new Valve(line);
            valveMap.put(v.getId(), v);
        }

        String dataPath = "data";

        for (int file = 26; file > 6; file--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + file + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));

            int numOpen = 0;
            long largest = 0;
            String largestStr = "";
            String pos;
            while ((pos = br2.readLine()) != null) {
                int me = 26;
                int elefant = 26;
                long size = 0;

                for (String valveId : pos.split("\\|")) {
                    if (valveId.isBlank()) {
                        elefant--;
                        me--;
                        continue;
                    }
                    String[] ids = valveId.split("\\:");
                    if (ids[0].endsWith("0")) {
                        size += (long) valveMap.get(ids[0].substring(0, 2)).getPPM() * me;
                    }
                    if (ids.length > 1 && ids[1].endsWith("0")) {
                        size += (long) valveMap.get(ids[1].substring(0, 2)).getPPM() * elefant;
                    }
                    elefant--;
                    me--;
                }
                if (size > largest) {
                    largest = size;
                    largestStr = pos;
                }
            }
            br2.close();

            System.out.println("---------------------------------");
            System.out.println("Filenumber: " + file);
            System.out.println("NumberOpen: " + numOpen);
            System.out.println("Largest   : " + largest);
            System.out.println("LargestStr: " + largestStr);
        }
    }
}
