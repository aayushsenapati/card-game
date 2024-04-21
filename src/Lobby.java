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

interface Game {
    void start();
}

class BlackJackGame implements Game {

    @Override
    public void start() {
        // Implement BlackJack game start logic here
    }
}

class MinesweeperGame implements Game {

    public MinesweeperGame(int rows, int columns, int mines) {
        // Implement Minesweeper game initialization here
    }

    @Override
    public void start() {
        // Implement Minesweeper game start logic here
    }

    static class Builder {
        private int rows;
        private int columns;
        private int mines;
        private String title;
        private int fontSize;

        public Builder(int rows, int columns, int mines) {
            this.rows = rows;
            this.columns = columns;
            this.mines = mines;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder fontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public MinesweeperGame build() {
            return new MinesweeperGame(rows, columns, mines, title, fontSize);
        }
    }

    private MinesweeperGame(int rows, int columns, int mines, String title, int fontSize) {
        // Implement Minesweeper game initialization here
    }
}

class GameFactory {

    public static Game createGame(String name, int rows, int columns, int mines) {
        switch (name.toLowerCase()) {
            case "blackjack":
                return new BlackJackGame();
            case "minesweeper":
                return new MinesweeperGame.Builder(rows, columns, mines)
                        .title("Minesweeper")
                        .fontSize(30)
                        .build();
            default:
                throw new IllegalArgumentException("Invalid game name: " + name);
        }
    }
}
