package org.example.Model.Entity.Items;

import org.example.Controller.Handler;
import org.example.Model.Id;
import org.example.Model.Tile.Tile;
import org.example.Resources.Resources;
import java.awt.*;
public class MagicStar extends Item{
    public MagicStar(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        setFalling(true);
        setGravity(0.1);
    }
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getStarPic().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        long endTime = System.nanoTime()/1000000000L;
        if(endTime - getStartTime() >= 3) {
            if(getVelX() == 0) {
                setVelX(2);
            }
            setX(getX() + getVelX());
            setY(getY() + getVelY());
            hitTiles();
            if (isJumping()) {
                setGravity(getGravity() - 0.1);
                setVelY((int) -getGravity());
                if (getGravity() <= 0.5) {
                    setJumping(false);
                    setFalling(true);
                }
            }
            if (isFalling()) {
                setGravity(getGravity() + 0.1);
                setVelY((int) getGravity());
            }
        }
    }
    public void hitTiles(){
        for (int i = 0; i < getHandler().getTiles().size(); i++) {
            Tile tile = getHandler().getTiles().get(i);
            if (getBoundsTop().intersects(tile.getBounds())) {
                setVelY(0);
                if (isJumping()) {
                    setJumping(false);
                    setGravity(1.0);
                    setFalling(true);
                }
            }
            if (getBoundsBottom().intersects(tile.getBounds())) {
                setJumping(true);
                setGravity(6.0);
                setVelY(0);
                if (isFalling()) {
                    setFalling(false);
                }
            }
            if (getBoundsRight().intersects(tile.getBounds())) {
                setVelX(-2);
            }
            if (getBoundsLeft().intersects(tile.getBounds())) {
                setVelX(2);
            }
        }
    }
}
