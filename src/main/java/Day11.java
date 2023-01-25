import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {

    public static void main(String[] args) throws Exception{
        String input = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1                
                """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day11.txt"));

        Monkey monkey = null;

        List<Monkey> monkeys = new ArrayList<>();

        Pattern monkeyPattern = Pattern.compile("Monkey (\\d):");
        Pattern multiplyPattern = Pattern.compile("Operation: new = old \\* (\\d+)");
        Pattern additionPattern = Pattern.compile("Operation: new = old \\+ (\\d+)");
        Pattern testPattern = Pattern.compile("Test: divisible by (\\d+)");
        Pattern truePattern = Pattern.compile("If true: throw to monkey (\\d+)");
        Pattern falsePattern = Pattern.compile("If false: throw to monkey (\\d+)");
        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            String trimmed = line.trim();
            Matcher monkeyMatch = monkeyPattern.matcher(trimmed);
            if (monkeyMatch.find()) {
                monkey = new Monkey(monkeyMatch.group(1));
                monkeys.add(monkey);
            }

            Matcher multiplyMatch = multiplyPattern.matcher(trimmed);
            if (multiplyMatch.find()) {
                monkey.addMultiplyValue(multiplyMatch.group(1));
            }

            Matcher additionMatch = additionPattern.matcher(trimmed);
            if (additionMatch.find()) {
                monkey.addAdditionValue(additionMatch.group(1));
            }

            Matcher testMatch = testPattern.matcher(trimmed);
            if (testMatch.find()) {
                monkey.addTestValue(testMatch.group(1));
            }

            Matcher trueMatch = truePattern.matcher(trimmed);
            if (trueMatch.find()) {
                monkey.addTrueValue(trueMatch.group(1));
            }

            Matcher falseMatch = falsePattern.matcher(trimmed);
            if (falseMatch.find()) {
                monkey.addFalseValue(falseMatch.group(1));
            }

            if (line.trim().startsWith("Starting items: ")) {
                String[] items = line.trim().substring("Starting items: ".length()).split(", ");
                for (String item : items) {
                    monkey.addItem(item);
                }
            }

            if (line.trim().equals("Operation: new = old * old")) {
                monkey.multiSelf();
            }
        }

        Map<Integer, Monkey> monkeyMap = new HashMap<>();
        for (Monkey monkey1 : monkeys) {
            monkeyMap.put(monkey1.getId(), monkey1);
        }

        for (int i = 0; i < 10000; i++) {
            if (i == 1 || i == 20 || i == 1000 ||
                i == 2000 || i == 3000 || i == 4000 ||
                i == 5000 || i == 6000 || i == 7000 ||
                i == 8000 || i == 9000 || i == 10000
            ) {
                System.out.println("== After round " + i + " ==");
                for (Monkey monkey1 : monkeys) {
                    monkey1.printInspections();
                }
                System.out.println();
            }

            for (Monkey monkey1 : monkeys) {
                monkey1.round(monkeyMap);
            }
        }

        List<Integer> inspections = new ArrayList<>();
        for (Monkey monkey1 : monkeys) {
            inspections.add(monkey1.getInspections());
        }

        inspections.sort((a, b) -> b - a);

        System.out.println(BigInteger.valueOf(inspections.get(0)).multiply(BigInteger.valueOf(inspections.get(1))));
    }
}
/*
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

 */
