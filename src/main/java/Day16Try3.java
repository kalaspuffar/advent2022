import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day16Try3 {
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

        for (Valve current : valveMap.values()) {
            int len = 0;
            Set<String> connections = new HashSet<>();
            connections.add(current.getId());
            while (current.connectionSize() < valveMap.size()) {
                current.addConnection(len, connections);
                Set<String> newConnections = new HashSet<>();
                for (String conn : connections) {
                    newConnections.addAll(valveMap.get(conn).getTunnels());
                }
                connections = newConnections;
                len++;
            }
        }

        int minutes = 30;
        Set<String> visited = new HashSet<>();
        String current = "AA";
        int value = 0;
        while (minutes > 0) {
            visited.add(current);
            String nextBest = valveMap.get(current).getNextBest(valveMap, visited, minutes);
            if (nextBest == null) break;
            int steps = valveMap.get(current).getSteps(nextBest);
            minutes -= steps + 1;
            value += valveMap.get(current).getPPM() * minutes;
            current = nextBest;
        }
        System.out.println(value);
    }

}
