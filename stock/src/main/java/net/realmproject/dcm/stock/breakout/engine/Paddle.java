package net.realmproject.dcm.stock.breakout.engine;

import net.realmproject.dcm.stock.breakout.engine.Ball.BallCollision;

public class Paddle extends Sprite implements Bouncable {

    public static final int WIDTH = 60;

    public Paddle(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
    }

    public void bounce(Ball ball) {

        if (!collision(ball)) { return; }

        BallCollision collision = ball.ballCollision(this);

        if (collision == BallCollision.HORIZONTAL || collision == BallCollision.DIAGONAL) {

            float offset = ball.xCenter() - xCenter();

            // on formula vx^2 + vy^2 = speed^2, we determine vx from offset and
            // solve for vy
            float vx = offset / 6.5f;
            float s2minusvx2 = (float) Math.abs(Math.pow(ball.speed, 2) - Math.pow(vx, 2));
            float vy = (float) Math.sqrt(s2minusvx2);

            ball.vx = vx;
            ball.vy = -Math.abs(vy);

            // ball.vy = -ball.vy;
        }

        if (collision == BallCollision.VERTICAL) {
            ball.vx = -ball.vx;
        }

    }

}
