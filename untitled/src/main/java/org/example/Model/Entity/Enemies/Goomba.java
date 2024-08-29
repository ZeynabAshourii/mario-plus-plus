package org.example.Model.Entity.Enemies;

import org.example.Controller.Handler;
import org.example.Model.Id;
import org.example.Resources.Resources;
import java.awt.*;
import java.util.Random;
public class Goomba extends EnemyGame {
    private Random random = new Random();
    public Goomba(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height , id, handler);
        switch (random.nextInt(2)){
            case 0 :
                setVelX(-2);
                setFacing(0);
                break;
            case 1 :
                setVelX(2);
                setFacing(1);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getGoomba().getSheet(), getX() , getY() , getWidth() , getHeight() , null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());

        antiFall();
        hitTile();
        hitItem();

        if(isFalling()){
            setGravity(getGravity()+0.1);
            setVelY((int) getGravity());
        }

    }
}
