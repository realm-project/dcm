package net.realmproject.dcm.stock.breakout.engine;

public class VWall extends Wall {

    public VWall(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
    }

    @Override
    public void bounce(Ball ball) {
        if (!collision(ball)) { return; }
        ball.vx = -ball.vx;
    }

}
