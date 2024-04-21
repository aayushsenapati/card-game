import java.util.ArrayList;
import java.util.Random;

public class BlackJackModel {
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerHand;
    private Card hiddenCard;
    private int dealerSum;
    private int dealerAceCount;
    private int playerSum;
    private int playerAceCount;
    private Random random = new Random();
    private boolean gameInProgress = false;
    private ArrayList<GameObserver> observers = new ArrayList<>();


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    private void notifyWin() {
        for (GameObserver observer : observers) {
            observer.updateWinCount();
        }
    }
    
    private void notifyLoss() {
        for (GameObserver observer : observers) {
            observer.updateLossCount();
        }
    }
    
    public BlackJackModel() {
        Deck.reset();
        startGame();
    }


    public void startGame() {
        dealerHand = new ArrayList<>();
        dealerSum = 0;
        Deck dealerDeck = Deck.getInstance();
        dealerDeck.shuffle();
        dealerAceCount = 0;

        
        hiddenCard = dealerDeck.draw();
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = dealerDeck.draw();
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;
        Deck playerDeck = Deck.getInstance();

        for (int i = 0; i < 2; i++) {
            card = playerDeck.draw();
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        gameInProgress = true;
    }

    public void hit() {
        Deck playerDeck = Deck.getInstance();
        Card card = playerDeck.draw();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        
        while(playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount--;
        }
        if (playerSum > 21) {
            gameInProgress = false;
            notifyLoss();
        }
        else if(playerSum == 21) {
            stay();
        }
    }

    public void stay() {
        Deck dealerDeck = Deck.getInstance();
        while (dealerSum < 17) {

            // Add a card to the dealer's hand
            Card card = dealerDeck.draw();
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
        
        }
        
        if (dealerSum > 21) {
            notifyWin();
        }
        else if(playerSum < 21 && dealerSum > playerSum) {
            notifyLoss();
        }
        else if(playerSum <= 21 && dealerSum < playerSum) {
            notifyWin();
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
        Deck.reset();
        Deck.getInstance().shuffle();
        startGame();
    }
}
