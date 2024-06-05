import java.awt.event.KeyEvent;

// Game state interface for state pattern
public interface GameState {

    // Update the update and updateGamePause methods in the Observer interface
    void update(TwoPlayerPongGame game);

    // Method to handle the key presses input by the players.
    void handleKeyPress(TwoPlayerPongGame game, KeyEvent e);

    // Used to enter the next state
    void enterState(TwoPlayerPongGame game);
}