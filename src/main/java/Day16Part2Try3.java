import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Day16Part2Try3 {
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
            valveMap.put(v.getId().toLowerCase(), v);
        }

        String dataPath = "data";

        Pattern findLastPattern = Pattern.compile("(\\d+)*([a-zA-Z]{2}:[a-zA-Z]{2})$");

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
                int paddingNum = 0;

                String valveId = pos;
                Matcher m = findLastPattern.matcher(pos);
                if (m.find()) {
                    if (m.start(1) > 0) {
                        prepend = pos.substring(0, m.start(1));
                    }
                    if (m.group(1) != null) {
                        paddingNum = Integer.parseInt(m.group(1));
                    }
                    valveId = m.group(2);
                }

                if (paddingNum > 5) continue;

                String[] ids = valveId.split("\\:");

                String ids0upper = ids[0].toUpperCase();
                String ids1upper = ids[1].toUpperCase();

                if (ids0upper.equals(ids[0]) && ids1upper.equals(ids[1])) {
                    prepend += paddingNum + ids[0] + ":" + ids[1];
                    meOpen = true;
                    elOpen = true;
                    paddingNum = 1;
                } else if (ids0upper.equals(ids[0])) {
                    prepend += paddingNum + ids[0];
                    meOpen = true;
                    paddingNum = 1;
                } else if (ids1upper.equals(ids[1])) {
                    prepend += paddingNum + ":" + ids[1];
                    elOpen = true;
                    paddingNum = 1;
                } else {
                    paddingNum++;
                }

                Valve v = valveMap.get(ids[0]);
                Valve ve = valveMap.get(ids[1]);
                if (
                    !meOpen && !elOpen &&
                    !v.isBroken() && !ve.isBroken() &&
                    !pos.contains(ids0upper) && !pos.contains(ids1upper) &&
                    !ids[0].equals(ids[1])
                ) {
                    wr2.write(prepend + paddingNum + v.getId() + ":" + ve.getId() + "\n");
                } else if (
                    !elOpen &&
                    !ve.isBroken() && !pos.contains(ids1upper)
                ) {
                    List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                    for (String tunnel : tunnels) {
                        wr2.write(prepend + paddingNum + tunnel  + ":" + ve.getId() + "\n");
                    }
                } else if (
                    !meOpen &&
                    !v.isBroken() && !pos.contains(ids0upper)
                ) {
                    List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                    for (String tunnel : tunnelsElefant) {
                        wr2.write(prepend + paddingNum + v.getId()  + ":" + tunnel + "\n");
                    }
                }

                List<String> tunnels = valveMap.get(ids[0]).getTunnels();
                List<String> tunnelsElefant = valveMap.get(ids[1]).getTunnels();
                for (String tunnel : tunnels) {
                    for (String tunnelElefant : tunnelsElefant) {
                        wr2.write(prepend + paddingNum + tunnel + ":" + tunnelElefant + "\n");
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
