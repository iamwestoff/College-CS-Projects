import javax.swing.*;
import java.awt.event.KeyEvent;

// Add a new GameState for the winner state
class WinnerState implements GameState {
    private final int winner;

    public WinnerState(int winner) {
        this.winner = winner;
    }

    @Override
    public void update(TwoPlayerPongGame game) {
    }

    @Override
    public void handleKeyPress(TwoPlayerPongGame game, KeyEvent e) {
    }

    @Override
    public void enterState(TwoPlayerPongGame game) {
        JOptionPane.showMessageDialog(game, "Player " + winner + " wins!");
        game.setCurrentState(new MenuState());
    }
}
