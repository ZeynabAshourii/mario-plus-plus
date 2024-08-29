package org.example.Model.Tile.Pipes;

import org.example.Controller.Handler;
import org.example.Model.Id;
import java.awt.*;
public class TeleSimplePipe extends PipeGame {
    private int facing;
    public TeleSimplePipe(int x, int y, int width, int height, Id id, Handler handler , int facing) {
        super(x, y, width, height, id, handler);
        this.facing = facing;
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(128 , 128 , 128));
        g.fillRect(getX() , getY() , getWidth() , getHeight());
    }
    @Override
    public void update() {

    }
    public int getFacing() {
        return facing;
    }
}
