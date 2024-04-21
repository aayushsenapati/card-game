import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> deck;
    private Random random = new Random();

    static Deck instance = null;

    private Deck() {
        deck = new ArrayList<>();
        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] types = { "C", "D", "H", "S" };

        for (String type : types) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    public static void reset() {
        if (instance == null) {
            return;
        }
        instance.deck.clear();
        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] types = { "C", "D", "H", "S" };

        for (String type : types) {
            for (String value : values) {
                instance.deck.add(new Card(value, type));
            }
        }
    }

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public void shuffle() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public Card draw() {
        return deck.remove(deck.size() - 1);
    }

    public int size() {
        return deck.size();
    }

}
