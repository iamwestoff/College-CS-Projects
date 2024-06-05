import javax.swing.*;

/**
 * @author Easton Herbon
 * @version 12.11.23
 * @brief This program utilizes the observer, state, and decorator patterns to achieve
 * a very basic pong game between two windows. Players are able to decorate the ball
 * in various ways to change the balls behavior.
 */

public class Main {
    public static void main(String[] args) {
        TwoPlayerPongGame pongGame1 = new TwoPlayerPongGame();
        pongGame1.setVisible(true);

        TwoPlayerPongGame pongGame2 = new TwoPlayerPongGame();
        pongGame2.setVisible(true);

        pongGame1.addObserver(pongGame2);
        pongGame2.addObserver(pongGame1);
    }
}