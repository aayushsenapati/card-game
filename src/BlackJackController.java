
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BlackJackController {
    private BlackJackModel model;
    private BlackJackView view;

    public BlackJackController(BlackJackModel model, BlackJackView view) {
        this.model = model;
        this.view = view;
        setupUI();
    }

    private void setupUI() {
        JButton hitButton = new JButton("Hit");
        JButton stayButton = new JButton("Stay");

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.hit();
                view.repaint();
            }
        });

        stayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.stay();
                view.repaint();
            }
        });

        // Add buttons to the view
        view.add(hitButton);
        view.add(stayButton);
    }
}