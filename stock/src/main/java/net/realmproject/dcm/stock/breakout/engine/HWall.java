package net.realmproject.dcm.stock.breakout.engine;

public class HWall extends Wall {

    public HWall(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
    }

    @Override
    public void bounce(Ball ball) {
        if (!collision(ball)) { return; }
        ball.vy = -ball.vy;
    }

}
