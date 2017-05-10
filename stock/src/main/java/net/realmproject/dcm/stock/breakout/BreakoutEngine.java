package net.realmproject.dcm.stock.breakout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import net.realmproject.dcm.features.command.Arg;
import net.realmproject.dcm.features.command.CommandDevice;
import net.realmproject.dcm.features.command.CommandDispatcher;
import net.realmproject.dcm.features.command.CommandMethod;
import net.realmproject.dcm.parcel.flow.hub.ParcelHub;
import net.realmproject.dcm.stock.breakout.engine.Arena;
import net.realmproject.dcm.stock.breakout.engine.Axes;
import net.realmproject.dcm.stock.breakout.engine.Brick;
import net.realmproject.dcm.stock.breakout.engine.Sprite;
import net.realmproject.dcm.stock.camera.Camera;
import net.realmproject.dcm.util.DCMThreadPool;


public class BreakoutEngine extends Camera implements CommandDevice {

    BufferedImage image = new BufferedImage(Arena.WIDTH, Arena.HEIGHT, BufferedImage.TYPE_INT_RGB);
    CommandDispatcher dispatcher;

    Arena arena;

    public BreakoutEngine(String id, ParcelHub bus) {
    	this(id, bus, 0);
    }
    
    public BreakoutEngine(String id, ParcelHub bus, int delay) {
        super(id, bus);
        dispatcher = new CommandDispatcher(this, bus);
        arena = new Arena();
        quality = 0.5f;
        DCMThreadPool.getScheduledPool().scheduleAtFixedRate(this::tick, delay, 33, TimeUnit.MILLISECONDS);
    }

    private void tick() {

        try {

            arena.advance();

            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setStroke(new BasicStroke(5f));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            // RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
            // g.setRenderingHint(RenderingHints.KEY_RENDERING,
            // RenderingHints.VALUE_RENDER_SPEED);

            g.setColor(Color.black);
            g.fillRect(0, 0, Arena.WIDTH, Arena.HEIGHT);

            drawSprite(g, arena.wallLeft);
            drawSprite(g, arena.wallRight);
            drawSprite(g, arena.wallTop);
            drawSprite(g, arena.paddle);
            drawSprite(g, arena.ball);
            for (Brick brick : arena.bricks) {
                if (brick.visible) {
                    drawSprite(g, brick);
                }
            }

            frame.image = fromBufferedImage(image);
            publishState();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawSprite(Graphics2D g, Sprite sprite) {
        g.setColor(sprite.colour.toAwtColor());
        g.fillRect((int) sprite.xStart, (int) sprite.yStart, (int) sprite.xSize, (int) sprite.ySize);
    }

    @CommandMethod
    public void home() {
        arena = new Arena();
    }

    @CommandMethod
    public void move(@Arg("axes") Axes axes) {
        arena.setMovePaddle((float) axes.axis0 * 6);
    }

    @Override
    public CommandDispatcher getCommandDispatcher() {
        return dispatcher;
    }

}
