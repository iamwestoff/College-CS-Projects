// Curved ball decorator

public class CurvedShotDecorator extends BallDecorator {
    public CurvedShotDecorator(Ball ball) {
        super(ball);
    }

    @Override
    public void move() {
        // Apply curved shot behavior
        Ball decoratedBall = getDecoratedBall();
        decoratedBall.setPosition(decoratedBall.getX() + 2, decoratedBall.getY() + 1);
        super.move(); // Move in the updated position
    }

    @Override
    public void reflectX() {
        getDecoratedBall().reflectX();
    }

    @Override
    public void reflectY() {
        getDecoratedBall().reflectY();
    }
}