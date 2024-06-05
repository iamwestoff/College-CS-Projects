import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

// Implements the initial menu state and updates
// accordingly for other states
class MenuState implements GameState {
    private JPanel menuPanel;

    @Override
    public void update(TwoPlayerPongGame game) {
        // do nothing
    }

    @Override
    public void handleKeyPress(TwoPlayerPongGame game, KeyEvent e) {
        // do nothing
    }

    @Override
    public void enterState(TwoPlayerPongGame game) {
        createMenuPanel(game);
        game.setContentPane(menuPanel);
        game.revalidate();
        game.repaint();
        game.notifyObservers();
    }

    // Method to create the GUI for the MenuPanel
    // and update necessary things for next states.
    private void createMenuPanel(TwoPlayerPongGame game) {
        menuPanel = new JPanel();

        JLabel titleLabel = new JLabel("Pong Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));

        JButton controlsButton = new JButton("Controls");
        controlsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(game, "Player 1 Controls: W and S\nPlayer 2 Controls: Up and Down");
        });


        JButton playButton = new JButton("Play");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(e -> {
            game.initGame();
            game.setCurrentState(new PlayingState(game));

            game.notifyObservers();

            menuPanel.remove(exitButton);
            menuPanel.remove(controlsButton);
            menuPanel.remove(titleLabel);
            menuPanel.remove(playButton);
            game.revalidate();
            game.repaint();
        });
        menuPanel.add(playButton);
        menuPanel.add(controlsButton);
        menuPanel.add(exitButton);

        game.add(menuPanel, BoxLayout.X_AXIS);
    }
}