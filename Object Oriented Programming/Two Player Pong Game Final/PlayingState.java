import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

// Used to create important aspects of the playing state and
// to update accordingly.

class PlayingState implements GameState {
    private final TwoPlayerPongGame game;

    private JButton resetButton;
    private JButton pauseButton;
    private JPanel buttonPanel;

    // Constructor
    public PlayingState(TwoPlayerPongGame game) {
        this.game = game;
        createMenu();
    }

    // Method to create the GUI for playingState
    private void createMenu() {
        // Create Reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> game.resetGame());

        // Create Pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> game.togglePauseGame());

        // Add buttons to a panel with BoxLayout
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        buttonPanel.add(pauseButton);
        buttonPanel.setVisible(false);

        game.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(TwoPlayerPongGame game) {
        game.notifyObservers();

        displayScores();

        game.repaint();
    }


    @Override
    public void handleKeyPress(TwoPlayerPongGame game, KeyEvent e) {
        game.handleKeyPress(e);
        game.notifyObservers();
    }


    @Override
    public void enterState(TwoPlayerPongGame game) {
        game.setCurrentState(this);
        game.initGame();
        game.notifyObservers();
        buttonPanel.setVisible(true);
        game.addKeyListener(game);
        game.requestFocus();
    }

    // Method to GUI display the scores on the JPanel
    private void displayScores() {
        Graphics g = game.getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString("Player 1: " + game.getPlayer1Score(), -100, -100);
        g.drawString("Player 2: " + game.getPlayer2Score(), game.getWidth() - 100, 20);
    }
}
