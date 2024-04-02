import java.util.HashMap;

public class Card {
    private final String name;
    private int value;
    protected static final HashMap<String, Integer> valuesTable;

    /* Initializing the table of cards' values. Default value of the Aces is 1 */
    static {
        valuesTable = new HashMap<>();
        String[] namesList = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        Integer[] valuesList = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 1};

        for(int i = 0; i < 13; ++i) {
            valuesTable.put(namesList[i], valuesList[i]);
        }
    }

    public Card(String name) {
        this.name = name;
        this.value = (Integer)valuesTable.get(name);
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

}