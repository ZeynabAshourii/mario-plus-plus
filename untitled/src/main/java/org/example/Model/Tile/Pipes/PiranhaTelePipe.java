package org.example.Model.Tile.Pipes;

import org.example.Controller.Handler;
import org.example.Model.Entity.Enemies.Plant;
import org.example.Model.Id;
import java.awt.*;
public class PiranhaTelePipe extends PipeGame {
    private int facing;
    public PiranhaTelePipe(int x, int y, int width, int height, Id id, Handler handler , int facing) {
        super(x, y, width, height, id, handler);
        this.facing = facing;
        handler.addEntity( new Plant(x , y - 64 , 64 , 64 , Id.plant , handler) );
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

    public void setFacing(int facing) {
        this.facing = facing;
    }
}
