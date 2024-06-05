// Abstract Ball Decorator class for decorator pattern

public abstract class BallDecorator extends Ball {
    private final Ball decoratedBall;

    public BallDecorator(Ball ball) {
        super(ball.getX(), ball.getY(), ball.getXSpeed(), ball.getYSpeed());
        this.decoratedBall = ball;
    }

    // Used to move the ball around the screen
    // when the game is in play
    @Override
    public void move() {
        decoratedBall.move();
    }

    // Methods to reflect the ball back and forth
    // when the ball is decorated
    @Override
    public void reflectX() {
        decoratedBall.reflectX();
    }

    @Override
    public void reflectY() {
        decoratedBall.reflectY();
    }

    public Ball getDecoratedBall() {
        return decoratedBall;
    }
}