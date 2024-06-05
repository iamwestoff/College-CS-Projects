import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Class for handling large amount of the logic involved
// in the game of Pong.

public class TwoPlayerPongGame extends JFrame implements KeyListener, Observer {
    // FINAL STATIC VARIABLES
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 60;
    private static final int BALL_SIZE = 15;
    private final List<Observer> observers = new ArrayList<>();
    private final Paddle paddle1;
    private final Paddle paddle2;
    private final Timer timer;

    // PRIVATE VARIABLES
    private boolean powerShotActive = false;
    private boolean curvedShotActive = false;
    private boolean decoratorApplied = false; // to make sure its only applied once
    private boolean gamePaused = false;
    private int playerWhoActivatedShot = 0; // 0: no player, 1: player 1, 2: player 2
    private int player1Score = 0;
    private int player2Score = 0;
    private Ball ball;
    private final JButton resetButton;
    private final JButton pauseButton;
    private final JPanel buttonPanel;
    private GameState currentState;

    public TwoPlayerPongGame() {
        setTitle("Two Player Pong Game");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize game-related settings
        paddle1 = new Paddle(getHeight() / 2 - PADDLE_HEIGHT / 2, 10);
        paddle2 = new Paddle(getHeight() / 2 - PADDLE_HEIGHT / 2, 10);
        ball = new Ball(getWidth() / 2 - BALL_SIZE / 2, getHeight() / 2 - BALL_SIZE / 2, 2, 2);

        // Create Reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());

        // Create Pause button
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> togglePauseGame());

        // Add buttons to a panel
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(resetButton);
        buttonPanel.add(pauseButton);
        buttonPanel.setVisible(false);

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(16, e -> gameLoop());

        currentState = new MenuState();

        // Call setCurrentState once with the initial state
        setCurrentState(currentState);

    }

    // Helper method used to initialize the Two Player Pong game
    public void initGame() {
        timer.start();
        repaint();
        revalidate();
    }

    // Setter state to set the current state of the program
    public void setCurrentState(GameState state) {
        this.currentState = state;
        state.enterState(this);
        revalidate();
        repaint();
    }

    // Method used to keep the game loop going
    // Check for if the game is paused and if
    // not the game continues
    void gameLoop() {
        if (!gamePaused) {
            update();
            notifyObservers();
            repaint();
        }
    }

    // Update method for the reflection of the ball
    // for the pong game. When the ball reaches a
    // specific X value, the values reflect on their
    // respective axis
    private void update() {
        if (!gamePaused) {
            applyDecorators();

            ball.move();

            if (ball.getY() <= 0 || ball.getY() >= HEIGHT - BALL_SIZE) {
                ball.reflectY();
            }

            if (ball.getX() <= PADDLE_WIDTH && ball.getY() >= paddle1.getY() && ball.getY() <= paddle1.getY() + PADDLE_HEIGHT) {
                ball.reflectX();
                resetShotTypes();
            }

            if (ball.getX() >= WIDTH - PADDLE_WIDTH - BALL_SIZE && ball.getY() >= paddle2.getY() && ball.getY() <= paddle2.getY() + PADDLE_HEIGHT) {
                ball.reflectX();
                resetShotTypes();
            }

            if (ball.getX() < 0 || ball.getX() > WIDTH) {
                // Ball out of bounds, reset position
                resetGame();
                resetShotTypes();
                if (ball.getX() > WIDTH) {
                    incrementPlayer1Score();
                } else if (ball.getX() < 0) {
                    incrementPlayer2Score();
                }
            }

            // Check if the ball's X position is 0 and randomly change its value
            if (ball.getX() == 0) {
                Random random = new Random();
                int randomXSpeed = random.nextInt(5) - 2;  // Random number between -2 and 2
                int randomYSpeed = random.nextInt(5) - 2;  // Random number between -2 and 2
                ball.setXSpeed(random.nextBoolean() ? randomXSpeed : -randomXSpeed);
                ball.setYSpeed(randomYSpeed);
            }

            resetShotTypes();

            if (ball.getX() < 0 || ball.getX() > WIDTH) {
                // Ball out of bounds, reset position
                resetGame();
                resetShotTypes();
            }
        }
    }

    // Method to reset all shot types after
    // a shot type has been decorated
    private void resetShotTypes() {
        powerShotActive = false;
        curvedShotActive = false;
    }

    // Method for resetting the game.
    void resetGame() {
        // Set the ball's position to the center of the window
        ball.setPosition(WIDTH / 2 - BALL_SIZE / 2, HEIGHT / 2 - BALL_SIZE / 2);

        // Generate random speeds between -2 and 2
        Random random = new Random();
        int randomXSpeed = random.nextInt(5) - 2;  // Random number between -2 and 2
        int randomYSpeed = random.nextInt(5) - 2;  // Random number between -2 and 2

        // Set the ball's initial speeds
        ball.setXSpeed(random.nextBoolean() ? randomXSpeed : -randomXSpeed);
        ball.setYSpeed(randomYSpeed);

        notifyObservers();

        // Reset any special shot types
        resetShotTypes();
    }

    // Method for adding observers
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Method to notify the observers of changes
    // currently happening in the program
    void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(paddle1.getY(), paddle2.getY(), ball.getX(), ball.getY(), currentState);
            observer.updateGamePause(gamePaused, currentState);  // Notify about the game pause state
        }
    }

    // Method used for player control input
    void handleKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && paddle1.getY() > 0) {
            paddle1.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && paddle1.getY() < HEIGHT - PADDLE_HEIGHT) {
            paddle1.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_W && paddle2.getY() > 0) {
            paddle2.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S && paddle2.getY() < HEIGHT - PADDLE_HEIGHT) {
            paddle2.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            powerShotActive = true;
            playerWhoActivatedShot = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            curvedShotActive = true;
            playerWhoActivatedShot = 2;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            powerShotActive = true;
            playerWhoActivatedShot = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            curvedShotActive = true;
            playerWhoActivatedShot = 1;
        }
    }

    // Helper method to toggle the game pause state
    void togglePauseGame() {
        gamePaused = !gamePaused;
        notifyObservers();
        if (gamePaused) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }

    // Method used to apply all the decorators when
    // various conditions are met.
    private void applyDecorators() {
        if (powerShotActive && !decoratorApplied) {
            int activatingPlayer = getActivatingPlayer();
            if (activatingPlayer == 1 && collidesWithPaddle(paddle1)) {
                ball = new PowerShotDecorator(ball);
                decoratorApplied = true;
                System.out.println("Power Shot Applied to Player 1");
                powerShotActive = false;
            } else if (activatingPlayer == 2 && collidesWithPaddle(paddle2)) {
                ball = new PowerShotDecorator(ball);
                decoratorApplied = true;
                System.out.println("Power Shot Applied to Player 2");
                powerShotActive = false;
            }
        } else if (curvedShotActive && !decoratorApplied) {
            int activatingPlayer = getActivatingPlayer();  // Get the player who activated the shot
            if (activatingPlayer == 1 && collidesWithPaddle(paddle1)) {
                ball = new CurvedShotDecorator(ball);
                decoratorApplied = true;
                System.out.println("Curved Shot Applied to Player 1");
                curvedShotActive = false;
            } else if (activatingPlayer == 2 && collidesWithPaddle(paddle2)) {
                ball = new CurvedShotDecorator(ball);
                decoratorApplied = true;
                System.out.println("Curved Shot Applied to Player 2");
                curvedShotActive = false;
            }
        } else {
            decoratorApplied = false;  // Reset decoratorApplied if no decorator is applied
        }
    }

    // Helper method to get the player who activated the shot
    private int getActivatingPlayer() {
        if (powerShotActive) {
            return playerWhoActivatedShot;
        } else if (curvedShotActive) {
            return playerWhoActivatedShot;
        } else {
            return 0;  // No activating player
        }
    }

    // Helper method to check if the ball collides with a paddle
    private boolean collidesWithPaddle(Paddle paddle) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int paddleY = paddle.getY();

        return (ballX + BALL_SIZE >= WIDTH - PADDLE_WIDTH && ballY + BALL_SIZE >= paddleY &&
                ballY <= paddleY + PADDLE_HEIGHT);
    }

    // Methods used to increment the winner state
    // (deprecated | removed due to NOT WORKING)
    public void incrementPlayer1Score() {
        player1Score++;
    }

    public void incrementPlayer2Score() {
        player2Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw paddles
        g.setColor(Color.BLUE);
        g.fillRect(0, paddle1.getY(), PADDLE_WIDTH, PADDLE_HEIGHT);

        g.setColor(Color.RED);
        g.fillRect(WIDTH - PADDLE_WIDTH, paddle2.getY(), PADDLE_WIDTH, PADDLE_HEIGHT);

        // Draw ball
        g.setColor(Color.BLACK);
        g.fillOval(ball.getX(), ball.getY(), BALL_SIZE, BALL_SIZE);

        // Display scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Player 1: " + getPlayer1Score(), 20, 20);
        g.drawString("Player 2: " + getPlayer2Score(), getWidth() - 100, 20);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gamePaused) {
            currentState.handleKeyPress(this, e);
            notifyObservers();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void update(int paddle1Y, int paddle2Y, int ballX, int ballY, GameState currentState) {
        paddle1.moveUpOrDownTo(paddle1Y);
        paddle2.moveUpOrDownTo(paddle2Y);
        ball.setPosition(ballX, ballY);
        this.currentState = currentState;
        repaint();
    }

    @Override
    public void updateGamePause(boolean gamePaused, GameState currentState) {
        this.gamePaused = gamePaused;
        if (gamePaused) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
        repaint();

        for (Observer observer : observers) {
            observer.updateGamePause(gamePaused, currentState);
        }
    }
}