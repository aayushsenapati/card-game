package BlackJackGame.src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BlackJackView extends JPanel {
    private BlackJackModel model;
    private BufferedImage hiddenCardImage;

    public BlackJackView(BlackJackModel model) {
        this.model = model;
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.GREEN);
        try {
            hiddenCardImage = ImageIO.read(new File("./cards/BACK.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        // Draw hidden card
        g.drawImage(hiddenCardImage, 20, 20, 110, 154, null);

        // Draw dealer's hand
        for (int i = 0; i < model.getDealerHand().size(); i++) {
            Card card = model.getDealerHand().get(i);
            BufferedImage cardImage = null;
            try {
                cardImage = ImageIO.read(new File(card.getImagePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (cardImage != null) {
                g.drawImage(cardImage, 140 + (120 + 5) * i, 20, 110, 154, null);
            }
        }

        // Draw player's hand
        for (int i = 0; i < model.getPlayerHand().size(); i++) {
            Card card = model.getPlayerHand().get(i);
            BufferedImage cardImage = null;
            try {
                cardImage = ImageIO.read(new File(card.getImagePath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (cardImage != null) {
                g.drawImage(cardImage, 20 + (120 + 5) * i, 320, 110, 154, null);
            }
        }

        // Display game result if game is over
        if (!model.isGameInProgress()) {
            String result = model.getGameResult();
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.setColor(Color.white);
            g.drawString(result, 220, 250);
        }
    }
}