package net.realmproject.dcm.stock.breakout.engine;


import java.util.ArrayList;
import java.util.List;


public class Arena {

    public static int HEIGHT = 480;
    public static int WIDTH = 640;

    public List<Brick> bricks = new ArrayList<>();
    public Paddle paddle;
    public Ball ball;
    private float paddleMove = 0f;

    public Wall wallTop, wallLeft, wallRight;

    public Arena() {
        wallTop = new HWall(0, 0, WIDTH, 10);
        wallLeft = new VWall(0, 0, 10, HEIGHT);
        wallRight = new VWall(WIDTH - 10, 0, 10, HEIGHT);

        paddle = new Paddle(320 - (Paddle.WIDTH / 2), 440, Paddle.WIDTH, 10);
        ball = new Ball((Arena.WIDTH - 10) / 2, 400, 10, 10);

        for (int r = 0; r < 5; r++) { // row
            int x = 11;

            for (int i = 0; i < 10; i++) {
                Brick brick = new Brick(x, 12 + (r + 4) * 12, Brick.WIDTH, Brick.HEIGHT);
                brick.colour = SpriteColour.values()[r];
                x += Brick.WIDTH + Brick.GAP;
                bricks.add(brick);
            }

        }
    }

    public void advance() {

        ball.move();
        movePaddle();

        Brick.bounce(ball, bricks);

        paddle.bounce(ball);

        wallTop.bounce(ball);
        wallLeft.bounce(ball);
        wallRight.bounce(ball);

        ball.doBounce();

        // ball fell off the screen+ delay
        if (ball.yStart > HEIGHT + 100) {
            ball = new Ball((Arena.WIDTH - 10) / 2, 400, 10, 10);
        }

    }

    public void setMovePaddle(float x) {
        paddleMove = x;
    }

    public void movePaddle() {
        float oldx = paddle.xStart;
        paddle.xStart += paddleMove;
        if (paddle.collision(wallLeft) || paddle.collision(wallRight)) {
            paddle.xStart = oldx;
        }
        paddleMove = 0f;
    }

}
