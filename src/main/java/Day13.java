import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    public static void main(String[] args) throws Exception{
        String input = """
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]            
                """;

        BufferedReader br = new BufferedReader(new FileReader("inputs/day13.txt"));

        int index = 1;
        List val1 = null;
        List val2;
        List<Integer> indexList = new ArrayList<>();

        //String line;
        //while ((line = br.readLine()) != null) {
        for (String line : input.split("\n")) {
            if (line.isBlank()) continue;
            if (val1 == null) {
                val1 = new ArrayList();
                parseNumberList(val1, line);
                continue;
            }
            val2 = new ArrayList();
            parseNumberList(val2, line);
            Boolean res = compare(val1, val2);
            if (res == null || res.booleanValue()) {
                indexList.add(index);
            }
            val1 = null;
            index++;
        }

        List unSorted = new ArrayList();
        String line;
        while ((line = br.readLine()) != null) {
        //for (String line : input.split("\n")) {
            if (line.isBlank()) continue;
            List val = new ArrayList();
            parseNumberList(val, line);
            unSorted.add(val);
        }

        List val = new ArrayList();
        parseNumberList(val, "[[2]]");
        unSorted.add(val);
        val = new ArrayList();
        parseNumberList(val,"[[6]]");
        unSorted.add(val);


        unSorted.sort((a, b) -> {
            Boolean res = compare((List)a, (List)b);
            if (res == null) return 0;
            if (res.booleanValue()) return -1;
            if (!res.booleanValue()) return 1;
            return 0;
        });

        Integer sum = indexList.stream().reduce((a, b) -> a + b).get();

        int newIndex = 1;
        int twoIndex = 0;
        int sixIndex = 0;
        for (Object a : unSorted) {
            if (getIntegerFromList((List) a) == 2) {
                System.out.println("2 = " + newIndex);
                twoIndex = newIndex;
            }
            if (getIntegerFromList((List) a) == 6) {
                System.out.println("6 = " + newIndex);
                sixIndex = newIndex;
            }
            newIndex++;
        }

        System.out.println(sum);
        System.out.println(twoIndex * sixIndex);
    }

    private static Boolean compare(List val1, List val2) {
        for (int i = 0; i < val1.size(); i++) {
            if (val2.size() <= i) return Boolean.FALSE;
            if (val1.get(i) instanceof List || val2.get(i) instanceof List) {
                List innerVal1;
                List innerVal2;
                if (val1.get(i) instanceof Integer) {
                    innerVal1 = wrapList((Integer) val1.get(i));
                } else {
                    innerVal1 = (List)val1.get(i);
                }
                if (val2.get(i) instanceof Integer) {
                    innerVal2 = wrapList((Integer) val2.get(i));
                } else {
                    innerVal2 = (List)val2.get(i);
                }
                Boolean res = compare(innerVal1, innerVal2);
                if (res != null) {
                    return res;
                }
            } else {
                if ((int)val1.get(i) < (int)val2.get(i)) {
                    return Boolean.TRUE;
                }
                if ((int)val1.get(i) > (int)val2.get(i)) {
                    return Boolean.FALSE;
                }
            }
        }
        return val1.size() != val2.size() ? Boolean.TRUE : null;
    }

    private static int getIntegerFromList(List list) {
        if (list.isEmpty()) return -1;
        if (list.size() > 1) return -1;
        if (list.get(0) instanceof List) {
            return getIntegerFromList((List)list.get(0));
        }
        return (int)list.get(0);
    }

    private static List wrapList(Integer val1) {
        List newList = new ArrayList();
        newList.add(val1);
        return newList;
    }


    private static String parseNumberList(List list, String line) {
        Pattern number = Pattern.compile("^(\\d+)[^\\d]*");

        do {
            if (line.startsWith("[")) {
                List childs = new ArrayList();
                line = parseNumberList(childs, line.substring(1));
                list.add(childs);
            }
            if (line.startsWith("]")) {
                return line.substring(1);
            }
            Matcher m = number.matcher(line);
            if (m.find()) {
                Integer num = Integer.parseInt(m.group(1));
                list.add(num);
                line = line.substring(m.group(1).length());
            }
            if (line.startsWith(",")) {
                line = line.substring(1);
            }
        } while (!line.isBlank());

        return null;
    }

}
