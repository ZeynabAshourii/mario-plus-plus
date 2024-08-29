package org.example.Model.Entity.Enemies;

import org.example.Controller.Handler;
import org.example.Model.Entity.Entity;
import org.example.Model.Entity.Player;
import org.example.Model.Id;
import org.example.Model.Tile.Tile;
import org.example.Resources.Resources;
import java.awt.*;
public class FireBallAttack extends Entity {
    public FireBallAttack(int x, int y, int width, int height, Id id, Handler handler , int facing) {
        super(x, y, width, height, id, handler);
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
            g.drawImage(Resources.getCircleFire().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){}
    }

    @Override
    public void update() {
        setX(getX() + getVelX());
        setY(getY() + getVelY());
        hitTile();
        hitPlayer();
    }
    public void hitTile(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBounds().intersects(tile.getBounds())){
                die();
            }
        }
    }
    public void hitPlayer(){
        for(int i = 0; i < getHandler().getEntities().size(); i++){
            Entity entity = getHandler().getEntities().get(i);
            if(getBounds().intersects(entity.getBounds())) {
                if (entity.getId() == Id.player) {
                    Player player = (Player) entity;
                    die();
                    player.hitPlayer(this);
                }
                if(entity.getId() == Id.sword){
                    die();
                    entity.die();
                }
            }
        }
    }
}
