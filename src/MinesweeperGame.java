import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class MinesweeperGame {
    private class MineTile extends JButton {
        int r;
        int c;

        public MineTile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private int tileSize;
    private int numRows;
    private int numCols;
    private int boardWidth;
    private int boardHeight;
    private int mineCount;

    private JFrame frame;
    private JLabel textLabel;
    private JPanel textPanel;
    private JPanel boardPanel;
    private MineTile[][] board;
    private ArrayList<MineTile> mineList;
    private Random random;
    private int tilesClicked;
    private boolean gameOver;

    public static class Builder {
        // Required parameters
        private int tileSize;
        private int numRows;
        private int numCols;
        private int mineCount;

        // Optional parameters - initialized to default values
        private String title = "Minesweeper";
        private int fontSize = 25;

        public Builder(int tileSize, int numRows, int mineCount) {
            this.tileSize = tileSize;
            this.numRows = numRows;
            this.numCols = numRows;
            this.mineCount = mineCount;
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
            return new MinesweeperGame(this);
        }
    }

    private MinesweeperGame(Builder builder) {
        this.tileSize = builder.tileSize;
        this.numRows = builder.numRows;
        this.numCols = builder.numCols;
        this.boardWidth = this.numCols * this.tileSize;
        this.boardHeight = this.numRows * this.tileSize;
        this.mineCount = builder.mineCount;

        this.frame = new JFrame(builder.title);
        this.textLabel = new JLabel();
        this.textPanel = new JPanel();
        this.boardPanel = new JPanel();
        this.board = new MineTile[numRows][numCols];

        this.mineList = new ArrayList<>();
        this.random = new Random();
        this.tilesClicked = 0;
        this.gameOver = false;

        initializeGUI(builder.title,builder.fontSize);
        setMines();
    }

    private void initializeGUI(String title,int fontSize) {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText(title +": "+ Integer.toString(mineCount));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(numRows, numCols));
        frame.add(boardPanel);

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) {
                            return;
                        }
                        MineTile tile = (MineTile) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText().isEmpty()) {
                                if (mineList.contains(tile)) {
                                    revealMines();
                                } else {
                                    checkMine(tile.r, tile.c);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText().isEmpty() && tile.isEnabled()) {
                                tile.setText("X");
                            } else if (tile.getText().equals("X")) {
                                tile.setText("");
                            }
                        }
                    }
                });
                boardPanel.add(tile);
            }
        }

        frame.setVisible(true);
    }

    private void setMines() {
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);
            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft--;
            }
        }
    }

    private void revealMines() {
        for (MineTile tile : mineList) {
            tile.setText("!");
        }
        gameOver = true;
        textLabel.setText("Game Over!");
    }

    private void checkMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return;
        }
        MineTile tile = board[r][c];
        if (!tile.isEnabled()) {
            return;
        }
        tile.setEnabled(false);
        tilesClicked++;
        int minesFound = 0;

        minesFound += countMine(r - 1, c - 1);
        minesFound += countMine(r - 1, c);
        minesFound += countMine(r - 1, c + 1);
        minesFound += countMine(r, c - 1);
        minesFound += countMine(r, c + 1);
        minesFound += countMine(r + 1, c - 1);
        minesFound += countMine(r + 1, c);
        minesFound += countMine(r + 1, c + 1);

        if (minesFound > 0) {
            tile.setText(Integer.toString(minesFound));
        } else {
            tile.setText("");
            checkMine(r - 1, c - 1);
            checkMine(r - 1, c);
            checkMine(r - 1, c + 1);
            checkMine(r, c - 1);
            checkMine(r, c + 1);
            checkMine(r + 1, c - 1);
            checkMine(r + 1, c);
            checkMine(r + 1, c + 1);
        }

        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!");
        }
    }

    private int countMine(int r, int c) {
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) {
            return 0;
        }
        if (mineList.contains(board[r][c])) {
            return 1;
        }
        return 0;
    }

}
