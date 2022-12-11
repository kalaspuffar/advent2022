import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Monkey {
    private final int id;
    private int multi = -1;
    private int addition = -1;
    private int test = -1;
    private int trueValue;
    private int falseValue;
    private Deque<Long> items = new ArrayDeque();
    private int inspections = 0;

    public Monkey(String group) {
        this.id = Integer.parseInt(group);
    }

    public void addMultiplyValue(String group) {
        this.multi = Integer.parseInt(group);
    }

    public void addAdditionValue(String group) {
        this.addition = Integer.parseInt(group);
    }

    public void addTestValue(String group) {
        this.test = Integer.parseInt(group);
    }

    public void addTrueValue(String group) {
        this.trueValue = Integer.parseInt(group);
    }

    public void addFalseValue(String group) {
        this.falseValue = Integer.parseInt(group);
    }


    public Integer getId() {
        return this.id;
    }

    public void addItem(String item) {
        this.items.add(Long.parseLong(item));
    }

    public void addItem(long item) {
        this.items.add(item);
    }

    public void round(Map<Integer, Monkey> monkeyMap) {
        while (!this.items.isEmpty()) {
            long item = this.items.pop();
            if (multi == -2) {
                item *= item;
            } else if (multi != -1) {
                item *= multi;
            } else if (addition != -1) {
                item += addition;
            }
            inspections++;

            item %= 9699690;

            if (item % test == 0) {
                monkeyMap.get(trueValue).addItem(item);
            } else {
                monkeyMap.get(falseValue).addItem(item);
            }
        }
    }

    public void multiSelf() {
        this.multi = -2;
    }

    public Integer getInspections() {
        return this.inspections;
    }

    public void printInspections() {
        System.out.println("Monkey " +id+ " inspected items " +inspections+ " times.");
    }
}
