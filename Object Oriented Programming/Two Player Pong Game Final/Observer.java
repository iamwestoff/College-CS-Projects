// Observer interface for observer pattern

public interface Observer {
    // Used to update the observers information
    // about the paddles, ball, and current state
    void update(int paddle1Y, int paddle2Y, int ballX, int ballY, GameState currentState);

    // Method to keep track of the game pause
    void updateGamePause(boolean gamePaused, GameState currentState);
}