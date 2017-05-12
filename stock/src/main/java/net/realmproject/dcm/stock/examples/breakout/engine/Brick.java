package net.realmproject.dcm.stock.examples.breakout.engine;

import java.util.List;

import net.realmproject.dcm.stock.examples.breakout.engine.Ball.BallCollision;



public class Brick extends Sprite implements Bouncable {

    public static int HEIGHT = 10;
    public static int WIDTH = 60;
    public static int GAP = 2;

    public Brick(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
    }

    @Override
    public void bounce(Ball ball) {
        if (!collision(ball)) { return; }
        if (!visible) { return; }
        visible = false;
    }

    public static void bounce(Ball ball, List<Brick> bricks) {

        BallCollision collision = ball.ballCollision(bricks);

        if (collision == BallCollision.HORIZONTAL || collision == BallCollision.DIAGONAL) {
            ball.yBounce = true;
        }

        if (collision == BallCollision.VERTICAL || collision == BallCollision.DIAGONAL) {
            ball.xBounce = true;
        }

        for (Brick brick : bricks) {
            brick.bounce(ball);
        }

    }

}
