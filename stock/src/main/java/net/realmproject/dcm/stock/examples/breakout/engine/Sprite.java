package net.realmproject.dcm.stock.examples.breakout.engine;

public class Sprite {

    public float xSize, ySize;
    public float xStart, yStart;
    public boolean visible = true;
    public SpriteColour colour = SpriteColour.GRAY;

    public Sprite(float px, float py, float sx, float sy) {
        this.xStart = px;
        this.yStart = py;
        this.xSize = sx;
        this.ySize = sy;
    }

    public float xEnd() {
        return xStart + xSize;
    }

    public float yEnd() {
        return yStart + ySize;
    }

    public float xCenter() {
        return xStart + xSize / 2f;
    }

    public float yCenter() {
        return yStart + ySize / 2f;
    }

    public boolean xInside(float x) {
        return xStart < x && x < xEnd();
    }

    public boolean yInside(float y) {
        return yStart < y && y < yEnd();
    }

    public boolean inside(float x, float y) {
        return xInside(x) && yInside(y);
    }

    public boolean xCollision(Sprite other) {
        // right edge of other is too far left to collide with left edge of this
        if (other.xEnd() < xStart) { return false; }

        // left edge of other is too far right to collide with right edge of
        // this
        if (other.xStart > xEnd()) { return false; }

        return true;
    }

    public boolean yCollision(Sprite other) {
        // bottom of other is too high up to collide with top of this
        if (other.yEnd() < yStart) { return false; }

        // top of other is too low down to collide with bottom of this
        if (other.yStart > yEnd()) { return false; }

        return true;
    }

    public boolean collision(Sprite other) {
        return xCollision(other) && yCollision(other);
    }

}
