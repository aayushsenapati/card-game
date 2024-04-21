import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackJackController {
    private BlackJackModel model;
    private BlackJackView view;

    public BlackJackController(BlackJackModel model, BlackJackView view) {
        this.model = model;
        this.view = view;
        setupUI();
    }

    private void setupUI() {
        JFrame frame = new JFrame("Black Jack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add action listeners for buttons
        view.getHitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.hit();
                view.repaint();
            }
        });

        view.getStayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.stay();
                view.repaint();
            }
        });

        // Add action listener for restart button
        view.getRestartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.restartGame(); // Reset the game
                view.repaint();
            }
        });

        view.getExitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the game window
            }
        });
    }
}
