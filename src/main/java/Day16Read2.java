import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16Read2 {
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

        BufferedReader br2 = new BufferedReader(new FileReader("data/0.txt"));

        long largest = 0;
        String largestStr = "";
        String pos;
        while ((pos = br2.readLine()) != null) {
            int i = 30;
            long size = 0;
/*
            int opened = Integer.parseInt(pos.substring(0, pos.indexOf("@")));
            if (opened != 15) {
                continue;
            }
 */
            pos = pos.substring(pos.indexOf("@") + 1);

            for (String valveId : pos.split("\\|")) {
                if (valveId.endsWith("0")) {
                    i--;
                    size += (long)valveMap.get(valveId.substring(0, 2)).getPPM() * i;
                }
                i--;
            }
            if (size > largest) {
                largest = size;
                largestStr = pos;
            }
        }
        br2.close();

        System.out.println(largest);
        System.out.println(largestStr);
    }
}
