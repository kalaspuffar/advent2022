import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Day16Part2Try4 {
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

        String openedIndex = "abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ1234567890";

        BufferedReader br = new BufferedReader(new FileReader("inputs/day16.txt"));

        Map<String, Valve> valveMap = new HashMap<>();

        int j = 0;
        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            Valve v = new Valve(line, String.valueOf(openedIndex.charAt(j++)));
            valveMap.put(v.getId(), v);
            valveMap.put(v.getId().toLowerCase(), v);
        }

        String dataPath = "data";

        for (int i = 2; i > 0; i--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + i + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));
            ZipOutputStream zwr2 = new ZipOutputStream(new FileOutputStream(dataPath + "/" + (i - 1) + ".zip"));
            zwr2.putNextEntry(new ZipEntry("data"));
            BufferedWriter wr2 = new BufferedWriter(new OutputStreamWriter(zwr2));

            Set<String> seen1 = new HashSet<>(1_000_010, 1);
            Set<String> seen2 = new HashSet<>(1_000_010, 1);

            boolean seen1Current = true;

            long test = 0;
            String pos;
            while ((pos = br2.readLine()) != null) {
                String[] posArr = pos.split("\\|");
                int score = Integer.parseInt(posArr[0]);

                if (score < 2200) continue;

                if (seen1.contains(pos) || seen2.contains(pos)) continue;

                if (seen1Current) {
                    seen1.add(pos);
                } else {
                    seen2.add(pos);
                }
                if (test > 1_000_000) {
                    if (seen1Current) {
                        seen2 = new HashSet<>(1_000_010, 1);
                    } else {
                        seen1 = new HashSet<>(1_000_010, 1);
                    }
                    seen1Current = !seen1Current;
                    test = 0;
                }
                test++;

                boolean meOpen = false;
                boolean elOpen = false;

                String openedStr = posArr[1];
                List<String> opened = new ArrayList<>();
                opened.addAll(Arrays.stream(openedStr.split("")).toList());

                String prepend = "";

                String valveId = posArr[2];
                String[] ids = valveId.split("\\:");

                String ids0upper = ids[0].toUpperCase();
                String ids1upper = ids[1].toUpperCase();

                if (ids0upper.equals(ids[0]) && ids1upper.equals(ids[1])) {
                    Valve v = valveMap.get(ids[0]);
                    Valve ve = valveMap.get(ids[1]);
                    opened.add(v.getOpenedId());
                    opened.add(ve.getOpenedId());
                    score += v.getPPM() * i;
                    score += ve.getPPM() * i;
                    meOpen = true;
                    elOpen = true;
                } else if (ids0upper.equals(ids[0])) {
                    Valve v = valveMap.get(ids[0]);
                    opened.add(v.getOpenedId());
                    score += v.getPPM() * i;
                    meOpen = true;
                } else if (ids1upper.equals(ids[1])) {
                    Valve ve = valveMap.get(ids[1]);
                    opened.add(ve.getOpenedId());
                    score += ve.getPPM() * i;
                    elOpen = true;
                }

                if (elOpen || meOpen) {
                    opened.sort(String::compareTo);
                    openedStr = "";
                    for (String s : opened) {
                        openedStr += s;
                    }
                }

                prepend = score + "|" + openedStr + "|";

                Valve v = valveMap.get(ids[0]);
                Valve ve = valveMap.get(ids[1]);
                if (
                    !meOpen && !elOpen &&
                    !v.isBroken() && !ve.isBroken() &&
                    !opened.contains(v.getOpenedId()) &&
                    !opened.contains(ve.getOpenedId()) &&
                    !ids[0].equals(ids[1])
                ) {
                    wr2.write(prepend + v.getId() + ":" + ve.getId() + "\n");
                } else if (
                    !elOpen &&
                    !ve.isBroken() && !opened.contains(ve.getOpenedId())
                ) {
                    List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                    for (String tunnel : tunnels) {
                        wr2.write(prepend + tunnel  + ":" + ve.getId() + "\n");
                    }
                } else if (
                    !meOpen &&
                    !v.isBroken() && !opened.contains(v.getOpenedId())
                ) {
                    List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                    for (String tunnel : tunnelsElefant) {
                        wr2.write(prepend + v.getId()  + ":" + tunnel + "\n");
                    }
                }

                List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                for (String tunnel : tunnels) {
                    for (String tunnelElefant : tunnelsElefant) {
                        wr2.write(prepend + tunnel + ":" + tunnelElefant + "\n");
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
