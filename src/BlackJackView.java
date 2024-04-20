import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BlackJackView extends JPanel {
    private BlackJackModel model;
    private JButton hitButton;
    private JButton stayButton;
    private JButton restartButton;

    public BlackJackView(BlackJackModel model) {
        this.model = model;
        setPreferredSize(new Dimension(600, 600));
        setBackground(new Color(53, 101, 77));
        setLayout(new BorderLayout());

        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        restartButton = new JButton("Restart");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(restartButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public JButton getStayButton() {
        return stayButton;
    }

    public JButton getRestartButton() {
        return restartButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // Draw dealer's hand
            for (int i = 0; i < model.getDealerHand().size(); i++) {
                Card card = model.getDealerHand().get(i);
                BufferedImage cardImg = ImageIO.read(new File(card.getImagePath()));
                g.drawImage(cardImg, 110 + 25 + (110 + 5) * i, 20, 110, 154, null);
            }
    
            // Draw hidden card
            if (!model.isGameInProgress()) {
                Card hiddenCard = model.getHiddenCard();
                BufferedImage hiddenCardImg = ImageIO.read(new File(hiddenCard.getImagePath()));
                g.drawImage(hiddenCardImg, 20, 20, 110, 154, null);
            } else {
                BufferedImage hiddenCardImg = ImageIO.read(new File("./cards/BACK.png"));
                g.drawImage(hiddenCardImg, 20, 20, 110, 154, null);
            }
    
            // Draw player's hand
            for (int i = 0; i < model.getPlayerHand().size(); i++) {
                Card card = model.getPlayerHand().get(i);
                BufferedImage cardImg = ImageIO.read(new File(card.getImagePath()));
                g.drawImage(cardImg, 20 + (110 + 5) * i, 320, 110, 154, null);
            }
    
            // Display game result only if game is over
            if (!model.isGameInProgress()) {
                int dealerSum = model.reduceDealerAce();
                int playerSum = model.reducePlayerAce();
                String message = "";
                if (playerSum > 21) {
                    message = "You Lose!";
                } else if (dealerSum > 21) {
                    message = "You Win!";
                } else if (playerSum == dealerSum) {
                    message = "Tie!";
                } else if (playerSum > dealerSum) {
                    message = "You Win!";
                } else if (playerSum < dealerSum) {
                    message = "You Lose!";
                }
    
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString(message, 220, 250);
    
                // Disable hit and stay buttons, enable restart button
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                restartButton.setEnabled(true);
            } else {
                // Enable hit and stay buttons, disable restart button
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                restartButton.setEnabled(false);
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
