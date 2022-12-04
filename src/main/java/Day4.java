import java.io.BufferedReader;
import java.io.FileReader;

public class Day4 {
    public static void main(String[] args) throws Exception{
        String testdata = """
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
                        """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day4.txt"));

        int count = 0;
        int no_overlap_count = 0;
        int all = 0;

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : testdata.split("\n")) {
            String[] elfassign = line.split(",");

            String[] assign1 = elfassign[0].split("-");
            String[] assign2 = elfassign[1].split("-");

            int assign1low = Integer.parseInt(assign1[0]);
            int assign1high = Integer.parseInt(assign1[1]);
            int assign2low = Integer.parseInt(assign2[0]);
            int assign2high = Integer.parseInt(assign2[1]);

            if (assign1low <= assign2low && assign1high >= assign2high) {
                count++;
            } else if (assign1low >= assign2low && assign1high <= assign2high) {
                count++;
            }

            if (assign1low > assign2high) {
                no_overlap_count++;
            } else if (assign2low > assign1high) {
                no_overlap_count++;
            }

            all++;
        }

        System.out.println(count);
        System.out.println(no_overlap_count);
        System.out.println(all - no_overlap_count);
    }
}
