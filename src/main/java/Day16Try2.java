import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16Try2 {
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

        for (int i = 30; i > 0; i--) {
            BufferedReader br2 = new BufferedReader(new FileReader(dataPath + "/" + i + ".txt"));
            BufferedWriter wr2 = new BufferedWriter(new FileWriter(dataPath + "/" + (i - 1) + ".txt"));

//            int minOpened = (int)((31d - (double) i) / 5d);
            int minOpened = (int)((31d - (double) i) / 4d);

            String pos;
            while ((pos = br2.readLine()) != null) {
                int opened = Integer.parseInt(pos.substring(0, pos.indexOf("@")));
                if (opened < minOpened) continue;
                pos = pos.substring(pos.indexOf("@") + 1);

                if (opened >= 15) {
                    wr2.write(opened +"@"+ pos + "\n");
                    continue;
                }

                String valveId = pos.substring(pos.length() - 2);
                if (pos.endsWith("0")) {
                    valveId = pos.substring(pos.length() - 3, pos.length() - 1);
                }

                List<String> tunnels = valveMap.get(valveId).getTunnels();
                for (String tunnel : tunnels) {
                    if (
                        pos.length() > 3 &&
                        !pos.endsWith("0") &&
                        tunnel.equals(pos.substring(pos.length() - 5, pos.length() - 3))
                    ) continue;

                    Valve v = valveMap.get(tunnel);
                    if (!v.isBroken()) {
                        if (!pos.contains(tunnel + "0")) {
                            wr2.write(opened +"@"+ pos + "|" + v.getId() + "\n");
                            wr2.write((opened + 1) +"@"+ pos + "|" + v.getId() + "0\n");
                        } else {
                            wr2.write(opened +"@"+ pos + "|" + v.getId() + "\n");
                        }
                    } else {
                        wr2.write(opened +"@"+ pos + "|" + v.getId() + "\n");
                    }
                }
            }
            br2.close();
            wr2.close();
            System.out.println(i);
        }
    }
}
