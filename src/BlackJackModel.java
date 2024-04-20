import java.util.ArrayList;
import java.util.Random;

public class BlackJackModel {
    private ArrayList<Card> deck;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private Card hiddenCard;
    private int dealerSum;
    private int dealerAceCount;
    private int playerSum;
    private int playerAceCount;
    private Random random = new Random();
    private boolean gameInProgress = false;

    public BlackJackModel() {
        buildDeck();
        shuffleDeck();
        startGame();
    }

    public void buildDeck() {
        deck = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (String type : types) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public void startGame() {
        dealerHand = new ArrayList<>();
        dealerSum = 0;
        dealerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        gameInProgress = true;
    }

    public void hit() {
        Card card = deck.remove(deck.size() - 1);
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);

        if (playerSum > 21) {
            gameInProgress = false;
        }
    }

    public void stay() {
        while (dealerSum < 17) {
            Card card = deck.remove(deck.size() - 1);
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);

            if (dealerSum > 21) {
                gameInProgress = false;
                break;
            }
        }
        gameInProgress = false; // End the game after the dealer's turn
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public Card getHiddenCard() {
        return hiddenCard;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void restartGame() {
        deck = new ArrayList<>();
        buildDeck();
        shuffleDeck();
        startGame();
    }
}
