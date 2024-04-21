
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Lobby extends JFrame implements GameObserver {

    private int winCount = 0;
    private int lossCount = 0;
    private JLabel winLabel;
    private JLabel lossLabel;

    public Lobby() {
        setTitle("Game Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton blackjackButton = new JButton("Play Blackjack");
        JButton minesweeperButton = new JButton("Play Minesweeper");

        winLabel = new JLabel("Wins: " + winCount);
        lossLabel = new JLabel("Losses: " + lossCount);

        // Add labels to the panel
        panel.add(winLabel);
        panel.add(lossLabel);
        blackjackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBlackjack();
            }
        });

        minesweeperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMinesweeper();
            }
        });

        panel.add(blackjackButton);
        panel.add(minesweeperButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    @Override
    public void updateWinCount() {
        winCount++;
        winLabel.setText("Wins: " + winCount);
    }

    @Override
    public void updateLossCount() {
        lossCount++;
        lossLabel.setText("Losses: " + lossCount);
    }

    private void startBlackjack() {
        // Start Blackjack game
        BlackJackFactory factory = new BlackJackFactory();
        BlackJackModel model = factory.getModel();
        BlackJackView view = factory.getView(model);
        BlackJackController controller = factory.getController(view, model);
        model.addObserver(this);

    }

    private void startMinesweeper() {
        // Start Minesweeper game
        MinesweeperGame game = new MinesweeperGame.Builder(70, 8, 10)
                                    .title("Minesweeper")
                                    .fontSize(30)
                                    .build();
                                    game.addObserver(this);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lobby();
            }
        });
    }
}
