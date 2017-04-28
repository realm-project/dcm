package net.realmproject.dcm.stock.breakout.engine;

public abstract class Wall extends Sprite implements Bouncable {

    public static final int SIZE = 10;

    public Wall(float px, float py, float sx, float sy) {
        super(px, py, sx, sy);
    }

}
