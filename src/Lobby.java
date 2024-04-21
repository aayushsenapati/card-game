
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lobby extends JFrame {
    public Lobby() {
        setTitle("Game Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton blackjackButton = new JButton("Play Blackjack");
        JButton minesweeperButton = new JButton("Play Minesweeper");

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

    private void startBlackjack() {
        // Start Blackjack game
        BlackJackModel model = new BlackJackModel();
        BlackJackView view = new BlackJackView(model);
        BlackJackController controller = new BlackJackController(model, view);

        JFrame frame = new JFrame("BlackJack");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startMinesweeper() {
        // Start Minesweeper game
        MinesweeperGame game = new MinesweeperGame.Builder(70, 8, 10)
                                    .title("Minesweeper")
                                    .fontSize(30)
                                    .build();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Lobby();
            }
        });
    }
}
