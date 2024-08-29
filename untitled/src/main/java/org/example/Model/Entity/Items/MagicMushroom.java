package org.example.Model.Entity.Items;

import org.example.Controller.Handler;
import org.example.Model.Id;
import org.example.Model.Tile.Tile;
import org.example.Resources.Resources;
import java.awt.*;
public class MagicMushroom extends Item {
    public MagicMushroom(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getMushroomPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        long endTime = System.nanoTime()/1000000000L;
        if(endTime - getStartTime() >= 3){
            setVelX(2);
        }
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if (getBoundsBottom().intersects(tile.getBounds())) {
                setVelY(0);
                if (isFalling()) {
                    setFalling(false);
                }
            } else if (!isFalling()) {
                setGravity(0.8);
                setFalling(true);
            }
            if (getBoundsRight().intersects(tile.getBounds())) {
                setVelX(-2);
            }
            if (getBoundsLeft().intersects(tile.getBounds())) {
                setVelX(2);
            }
        }
        if (isFalling()) {
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }
    }
}
