import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Day16Part2 {
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

        int[] minVal = new int[] {
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,8,7,6,5,
            4,3,2,1,1,0,0
        };

        for (int i = 26; i > 0; i--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + i + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));
            ZipOutputStream zwr2 = new ZipOutputStream(new FileOutputStream(dataPath + "/" + (i - 1) + ".zip"));
            zwr2.putNextEntry(new ZipEntry("data"));
            BufferedWriter wr2 = new BufferedWriter(new OutputStreamWriter(zwr2));

//            int minOpened = (int)((27d - (double) i) / 5d);
//            int minOpened = (int) ((27d - (double) i) / 2.5d);
            int minOpened = (int) Math.floor((26d - (double) i) / 1.6d);
            minOpened = Math.min(13, minOpened);

            String pos;
            while ((pos = br2.readLine()) != null) {
                int opened = Integer.parseInt(pos.substring(0, pos.indexOf("@")));
                if (opened < minOpened) continue;
                pos = pos.substring(pos.indexOf("@") + 1);

                if (opened >= 15) {
                    wr2.write(opened +"@"+ pos + "\n");
                    continue;
                }

                String valveId = pos.substring(pos.lastIndexOf("|") + 1);
                String[] ids = valveId.split("\\:");
                if (ids[0].endsWith("0")) {
                    ids[0] = ids[0].substring(0, 2);
                }
                if (ids[1].endsWith("0")) {
                    ids[1] = ids[1].substring(0, 2);
                }

                List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                for (String tunnel : tunnels) {
                    for (String tunnelElefant : tunnelsElefant) {
                        String[] path = pos.split("\\|");
                        String[] previous = null;
                        String[] previous2 = null;
                        if (path.length > 2) {
                            previous = path[path.length - 1].split(":");
                            previous2 = path[path.length - 2].split(":");
                        }
                        if (                          path.length > 2 &&
                            previous[0].endsWith("0") &&
                            tunnel.equals(previous2[0])
                        ) continue;
                        if (
                            path.length > 2 &&
                            previous[1].endsWith("0") &&
                            tunnelElefant.equals(previous2[1])
                        ) continue;

//                        if (pos.split(tunnel).length > 6) continue;
//                        if (pos.split(tunnelElefant).length > 6) continue;
                        if (pos.split(tunnel).length > 3) continue;
                        if (pos.split(tunnelElefant).length > 3) continue;

                        Valve v = valveMap.get(tunnel);
                        Valve ve = valveMap.get(tunnelElefant);
                        if (
                            !v.isBroken() && !ve.isBroken() &&
                            !pos.contains(tunnel + "0") && !pos.contains(tunnelElefant + "0") &&
                            !tunnel.equals(tunnelElefant)
                        ) {
                            wr2.write(opened + "@" + pos + "|" + v.getId() + ":" + ve.getId() + "\n");
                            wr2.write((opened + 1) + "@" + pos + "|" + v.getId() + "0:" + ve.getId() + "\n");
                            wr2.write((opened + 1) + "@" + pos + "|" + v.getId() + ":" + ve.getId() + "0\n");
                            wr2.write((opened + 2) + "@" + pos + "|" + v.getId() + "0:" + ve.getId() + "0\n");
                        } else if (!ve.isBroken() && !pos.contains(tunnelElefant + "0")) {
                            wr2.write(opened + "@" + pos + "|" + v.getId() + ":" + ve.getId() + "\n");
                            wr2.write((opened + 1) + "@" + pos + "|" + v.getId()  + ":" + ve.getId() + "0\n");
                        } else if (!v.isBroken() && !pos.contains(tunnel + "0")) {
                            wr2.write(opened + "@" + pos + "|" + v.getId() + ":" + ve.getId() + "\n");
                            wr2.write((opened + 1) + "@" + pos + "|" + v.getId() + "0:" + ve.getId() + "\n");
                        } else {
                            wr2.write(opened + "@" + pos + "|" + v.getId() + ":" + ve.getId() + "\n");
                        }
                    }
                }
                wr2.flush();
            }
            br2.close();
            wr2.close();
            System.out.println(i);
        }
    }
}
