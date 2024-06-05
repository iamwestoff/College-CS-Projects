// Powershot decorator

public class PowerShotDecorator extends BallDecorator {
    public PowerShotDecorator(Ball ball) {
        super(ball);
    }

    @Override
    public void move() {
        // Apply power shot behavior
        Ball decoratedBall = getDecoratedBall();
        decoratedBall.setPosition(decoratedBall.getX() + 20, decoratedBall.getY() + 20);
        super.move(); // Move in the updated position
    }

    // Decorator function to reflectX respectively
    @Override
    public void reflectX() {
        getDecoratedBall().reflectX();
    }

    // Decorator function to reflectY respectively
    @Override
    public void reflectY() {
        getDecoratedBall().reflectY();
    }
}