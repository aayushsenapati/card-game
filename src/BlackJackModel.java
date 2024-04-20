
import java.util.ArrayList;
import java.util.Random;

public class BlackJackModel {
    private ArrayList<Card> deck;
    private Random random = new Random();

    // Dealer
    private Card hiddenCard;
    private ArrayList<Card> dealerHand;
    private int dealerSum;
    private int dealerAceCount;

    // Player
    private ArrayList<Card> playerHand;
    private int playerSum;
    private int playerAceCount;

    private boolean gameInProgress = true;
    private String gameResult = "";

    public BlackJackModel() {
        buildDeck();
        shuffleDeck();
        startGame();
    }

    private void buildDeck() {
        deck = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (String type : types) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    private void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    private void startGame() {
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
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public void hit() {
        Card card = deck.remove(deck.size() - 1);
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
    }

    public void stay() {
        while (dealerSum < 17) {
            Card card = deck.remove(deck.size() - 1);
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
        }
        gameInProgress = false;
        determineGameResult();
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public String getGameResult() {
        return gameResult;
    }

    private void determineGameResult() {
        if (playerSum > 21) {
            gameResult = "You Lose!";
        } else if (dealerSum > 21) {
            gameResult = "You Win!";
        } else if (playerSum == dealerSum) {
            gameResult = "Tie!";
        } else if (playerSum > dealerSum) {
            gameResult = "You Win!";
        } else if (playerSum < dealerSum) {
            gameResult = "You Lose!";
        }
    }
}