package net.realmproject.dcm.stock.examples.breakout.engine;

import java.util.Collections;
import java.util.List;


public class Ball extends Sprite {

    public enum BallCollision {
        VERTICAL, HORIZONTAL, NONE, DIAGONAL;
    }

    // vx and vy should be components of speed.
    // ie (vx^2 + vy^2)^0.5 = speed
    float vx, vy, speed;

    boolean xBounce = false;
    boolean yBounce = false;

    public Ball(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
        speed = 5f;
        vx = -(float) Math.sqrt(Math.pow(speed, 2) / 2);
        vy = vx;
    }

    public void move() {
        xStart += vx;
        yStart += vy;
    }

    public void doBounce() {
        if (xBounce) {
            vx = -vx;
            xBounce = false;
        }

        if (yBounce) {
            vy = -vy;
            yBounce = false;
        }
    }

    public BallCollision ballCollision(Sprite other) {
        return ballCollision(Collections.singletonList(other));
    }

    public BallCollision ballCollision(List<? extends Sprite> others) {

        boolean n = false, e = false, s = false, w = false, nw = false, ne = false, sw = false, se = false;

        for (Sprite other : others) {

            if (other.visible == false) {
                continue;
            }

            // sides
            n |= other.inside(xCenter(), yStart);
            e |= other.inside(xEnd(), yCenter());
            s |= other.inside(xCenter(), yEnd());
            w |= other.inside(xStart, yCenter());

            // corners
            nw |= other.inside(xStart, yStart);
            ne |= other.inside(xEnd(), yStart);
            sw |= other.inside(xStart, yEnd());
            se |= other.inside(xEnd(), yEnd());
        }

        // all three along an edge
        if (nw && n && ne || sw && s && se) { return BallCollision.HORIZONTAL; }
        if (nw && w && sw || ne && e && se) { return BallCollision.VERTICAL; }

        // check for large overlap diagonals first
        if (n && ne && e || e && se && s || s && sw && w || w && nw && n) { return BallCollision.DIAGONAL; }

        // two out of three along one edge is pretty good
        if (nw && n || n && ne || sw && s || s && se) { return BallCollision.HORIZONTAL; }
        if (nw && w || w && sw || ne && e || e && se) { return BallCollision.VERTICAL; }

        // single corner collision
        if (nw || ne || sw || se) { return BallCollision.DIAGONAL; }

        // no collision
        return BallCollision.NONE;

        //
        // if (!collision(other)) { return Collision.NONE; }
        //
        // // counter-intuitive, maybe hackish. If the center of the other
        // sprite
        // // is inside this one, then it's not the axis which *just* collided
        // if (xInside(other.xCenter())) { return Collision.HORIZONTAL; }
        // if (yInside(other.yCenter())) { return Collision.VERTICAL; }
        // return Collision.DIAGONAL;
    }

}
