import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Day16Part2Try2 {
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

        String dataPath = "data";

        for (int i = 26; i > 0; i--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + i + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));
            ZipOutputStream zwr2 = new ZipOutputStream(new FileOutputStream(dataPath + "/" + (i - 1) + ".zip"));
            zwr2.putNextEntry(new ZipEntry("data"));
            BufferedWriter wr2 = new BufferedWriter(new OutputStreamWriter(zwr2));

            Set<String> seen1 = new HashSet<>();
            Set<String> seen2 = new HashSet<>();

            boolean seen1Current = true;

            long test = 0;
            String pos;
            while ((pos = br2.readLine()) != null) {

                if (seen1.contains(pos) || seen2.contains(pos)) continue;

                if (seen1Current) {
                    seen1.add(pos);
                } else {
                    seen2.add(pos);
                }
                if (test > 1_000_000) {
                    if (seen1Current) {
                        seen2 = new HashSet<>();
                    } else {
                        seen1 = new HashSet<>();
                    }
                    seen1Current = !seen1Current;
                    test = 0;
                }
                test++;

                boolean meOpen = false;
                boolean elOpen = false;

                String prepend = "";
                if (pos.lastIndexOf("|") != -1) {
                    prepend = pos.substring(0, pos.lastIndexOf("|") + 1);
                }
                String valveId = pos.substring(pos.lastIndexOf("|") + 1);
                String[] ids = valveId.split("\\:");
                if (ids[0].endsWith("0") && ids[1].endsWith("0")) {
                    ids[0] = ids[0].substring(0, 2);
                    ids[1] = ids[1].substring(0, 2);
                    prepend = pos;
                    meOpen = true;
                    elOpen = true;
                } else if (ids[0].endsWith("0")) {
                    prepend += ids[0];
                    ids[0] = ids[0].substring(0, 2);
                    meOpen = true;
                } else if (ids[1].endsWith("0")) {
                    prepend += ":" + ids[1];
                    ids[1] = ids[1].substring(0, 2);
                    elOpen = true;
                }

                Valve v = valveMap.get(ids[0]);
                Valve ve = valveMap.get(ids[1]);
                if (
                    !meOpen && !elOpen &&
                    !v.isBroken() && !ve.isBroken() &&
                    !pos.contains(ids[0] + "0") && !pos.contains(ids[1] + "0") &&
                    !ids[0].equals(ids[1])
                ) {
                    wr2.write(prepend + "|" + v.getId() + "0:" + ve.getId() + "0\n");
                } else if (
                    !elOpen &&
                    !ve.isBroken() && !pos.contains(ids[1] + "0")
                ) {
                    List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                    for (String tunnel : tunnels) {
                        wr2.write(prepend + "|" + tunnel  + ":" + ve.getId() + "0\n");
                    }
                } else if (
                    !meOpen &&
                    !v.isBroken() && !pos.contains(ids[0] + "0")
                ) {
                    List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                    for (String tunnel : tunnelsElefant) {
                        wr2.write(prepend + "|" + v.getId()  + "0:" + tunnel + "\n");
                    }
                }

                List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                for (String tunnel : tunnels) {
                    for (String tunnelElefant : tunnelsElefant) {
                        wr2.write(prepend + "|" + tunnel + ":" + tunnelElefant + "\n");
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
