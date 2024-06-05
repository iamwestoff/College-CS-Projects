// Class used to create the paddle

public class Paddle {
    private final int SPEED;
    private int y;

    // Constructor
    public Paddle(int initialY, int speed) {
        this.y = initialY;
        this.SPEED = speed;
    }

    public int getY() {
        return y;
    }

    // All methods to aid in the movement of the
    // players paddles
    public void moveUp() {
        y -= SPEED;
    }

    public void moveDown() {
        y += SPEED;
    }

    public void moveUpOrDownTo(int newY) {
        this.y = newY;
    }
}