import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {
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


        //String line;
        //while ((line = br.readLine()) != null) {
        for (String line : input.split("\n")) {
            Valve v = new Valve(line);
            valveMap.put(v.getId(), v);
        }

        Map<String, Integer> pressureMap = new HashMap<>();
        Map<String, Integer> openMap = new HashMap<>();
        pressureMap.put("AA", 0);
        for (int i = 29; i > 0; i--) {
            Map<String, Integer> newPressureMap = new HashMap<>();
            for (String pos : openMap.keySet()) {
                String valveId = pos.substring(pos.length() - 2);
                newPressureMap.put(pos + "0", openMap.get(pos) + valveMap.get(valveId).getPPM() * i);
                newPressureMap.put(pos, openMap.get(pos));
            }
            openMap = new HashMap<>();
            for (String pos : pressureMap.keySet()) {
                String valveId = pos.substring(pos.length() - 2);
                if (pos.endsWith("0")) {
                    valveId = pos.substring(pos.length() - 3, pos.length() - 1);
                }
                List<String> tunnels = valveMap.get(valveId).getTunnels();
                for (String tunnel : tunnels) {
                    if (pos.length() > 5 && pos.contains(pos.substring(pos.length() - 5) + "|" + tunnel)) continue;
                    Valve v = valveMap.get(tunnel);
                    if (!v.isBroken()) {
                        if (pos.contains(tunnel + "0")) continue;
                        openMap.put(pos + "|" + v.getId(), pressureMap.get(pos));
                    } else {
                        newPressureMap.put(pos + "|" + v.getId(), pressureMap.get(pos));
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortList = new ArrayList<>();
            for (Map.Entry entry : newPressureMap.entrySet()) {
                sortList.add(entry);
            }

            sortList.sort((a, b) -> b.getValue() - a.getValue());
            if (sortList.size() > 1000000) {
                //sortList = sortList.subList(0, 1000000);
            }

            if (sortList.isEmpty() && openMap.isEmpty()) break;
            pressureMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : sortList) {
                pressureMap.put(entry.getKey(), entry.getValue());
            }

            System.out.println(i);
        }

        int highest = 0;
        String highStr = "";
        for (Map.Entry<String, Integer> entry : pressureMap.entrySet()) {
            if (entry.getValue() > highest) {
                highest = entry.getValue();
                highStr = entry.getKey();
            }
        }

        System.out.println(highStr + " = " + highest);
    }
}
