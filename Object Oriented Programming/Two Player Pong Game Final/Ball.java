// Create the ball and all respective parameters

public class Ball {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public Ball(int initialX, int initialY, int xSpeed, int ySpeed) {
        this.x = initialX;
        this.y = initialY;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(int i) {
        this.xSpeed = i;
    }

    protected int getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(int i) {
        this.ySpeed = i;
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public void reflectX() {
        xSpeed = -xSpeed;
    }

    public void reflectY() {
        ySpeed = -ySpeed;
    }
}
