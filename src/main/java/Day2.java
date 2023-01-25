import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day2 {
    static final int rock = 1;
    static final int paper = 2;
    static final int scissors = 3;

    static final int loss = 0;
    static final int draw = 3;
    static final int win = 6;

    public static void main(String[] args) throws Exception {
        Map<String, Integer> shapes = new HashMap<>();
        shapes.put("A", rock);
        shapes.put("B", paper);
        shapes.put("C", scissors);
        shapes.put("X", rock);
        shapes.put("Y", paper);
        shapes.put("Z", scissors);

        String testdata = """
A Y
B X
C Z
                        """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day2.txt"));

        int score = 0;

        int predscore = 0;

        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : testdata.split("\n")) {
            String[] match = line.trim().split(" ");

            if (match.length != 2) continue;

            if (shapes.get(match[0]) == shapes.get(match[1])) {
                score += draw;
            } else if (
                shapes.get(match[0]) == rock &&
                shapes.get(match[1]) == paper
            ) {
                score += win;
            } else if (
                shapes.get(match[0]) == paper &&
                shapes.get(match[1]) == scissors
            ) {
                score += win;
            } else if (
                shapes.get(match[0]) == scissors &&
                shapes.get(match[1]) == rock
            ) {
                score += win;
            } else {
                score += loss;
            }

            score += shapes.get(match[1]);

            if (match[1].equals("X")) {
                predscore += loss;

                if (shapes.get(match[0]) == rock) {
                    predscore += scissors;
                }
                if (shapes.get(match[0]) == paper) {
                    predscore += rock;
                }
                if (shapes.get(match[0]) == scissors) {
                    predscore += paper;
                }
            }
            if (match[1].equals("Y")) {
                predscore += draw;
                predscore += shapes.get(match[0]);
            }
            if (match[1].equals("Z")) {
                predscore += win;

                if (shapes.get(match[0]) == rock) {
                    predscore += paper;
                }
                if (shapes.get(match[0]) == paper) {
                    predscore += scissors;
                }
                if (shapes.get(match[0]) == scissors) {
                    predscore += rock;
                }
            }
        }

        System.out.println(score);
        System.out.println(predscore);
    }
}
