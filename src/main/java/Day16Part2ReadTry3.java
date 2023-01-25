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

public class Day16Part2ReadTry3 {
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

        for (int file = 26; file > 0; file--) {
            ZipInputStream zbr2 = new ZipInputStream(new FileInputStream(dataPath + "/" + file + ".zip"));
            zbr2.getNextEntry();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(zbr2));

            int numOpen = 0;
            long largest = 0;
            String largestStr = "";
            String pos;

            Pattern findPattern = Pattern.compile("(\\d+)([a-zA-Z]{2})*(:[a-zA-Z]{2})*");

            while ((pos = br2.readLine()) != null) {
                int me = 26;
                int elefant = 26;
                long size = 0;

                Matcher m = findPattern.matcher(pos);

                while (m.find()) {
                    int paddingNum = Integer.parseInt(m.group(1));
                    me -= paddingNum;
                    elefant -= paddingNum;
                    if (m.group(2) != null && m.group(2).toUpperCase().equals(m.group(2))) {
                        size += (long) valveMap.get(m.group(2)).getPPM() * me;
                    }
                    if (m.group(3) != null && m.group(3).toUpperCase().equals(m.group(3))) {
                        size += (long) valveMap.get(m.group(3).substring(1)).getPPM() * elefant;
                    }
                }
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
