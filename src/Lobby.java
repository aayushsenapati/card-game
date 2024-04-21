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
        Game game = GameFactory.createGame("Minesweeper", 70, 8, 10);
        game.start();
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
