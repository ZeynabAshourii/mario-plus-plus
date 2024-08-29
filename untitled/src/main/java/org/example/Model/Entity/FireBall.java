package org.example.Model.Entity;

import org.example.Controller.Handler;
import org.example.Model.Entity.Enemies.Bowser;
import org.example.Model.Id;
import org.example.Model.Tile.Tile;
import org.example.Resources.Resources;
import java.awt.*;
import java.util.Random;
public class FireBall extends Entity {
    private int firstX;
    public FireBall(int x, int y, int width, int height, Id id, Handler handler, int facing) {
        super(x, y, width, height, id, handler);
        this.firstX = x;
        switch (facing){
            case 0 :
                setVelX(-5);
                break;
            case 1:
                setVelX(5);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getFireBall().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        if(getX() - firstX >= 64*8 ) {
            die();
        }
        hitTiles();
        hitEntities();
        if(isJumping()){
            setGravity(getGravity()-0.17);
            setVelY((int)-getGravity());
            if(getGravity() <= 0.5){
                setJumping(false);
                setFalling(true);
            }
        }
        if(isFalling()){
            setGravity(getGravity()+0.17);
            setVelY((int)getGravity());
        }
    }
    public void hitTiles(){
        for (int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBoundsLeft().intersects(tile.getBounds()) || getBoundsRight().intersects(tile.getBounds())){
                die();
            }

            if(getBoundsBottom().intersects(tile.getBounds())){
                setJumping(true);
                setFalling(false);
                setGravity(4.0);
            } else if (!isFalling() && !isJumping()) {
                setFalling(true);
                setGravity(1.0);
            }
        }
    }
    public void hitEntities(){
        for(int i = 0 ; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(getBounds().intersects(entity.getBounds()) && !(entity.equals(this))) {
                if(entity instanceof Bowser){
                    int distance = Math.abs(firstX - entity.getX());
                    int random = new Random().nextInt(8);
                    if(random < distance){
                        ((Bowser) entity).hurt();
                    }
                }
                else{
                    entity.die();
                }
                die();
            }
        }
    }

}
