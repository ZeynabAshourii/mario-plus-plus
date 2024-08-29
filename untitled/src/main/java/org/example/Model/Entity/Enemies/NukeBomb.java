package org.example.Model.Entity.Enemies;

import org.example.Controller.Handler;
import org.example.Model.Entity.Entity;
import org.example.Model.Entity.Player;
import org.example.Model.Id;
import org.example.Model.Tile.Tile;
import org.example.Resources.Resources;
import java.awt.*;
public class NukeBomb extends Entity {
    private boolean active = false;
    public NukeBomb(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        setVelY(5);
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(Resources.getCircleFire().getSheet(), getX(), getY(), getWidth(), getHeight(), null);
        }
        catch (Exception e){

        }
    }

    @Override
    public void update() {
        setY(getY() + getVelY());
        if(active){
            die();
        }
        hitTiles();
        hitEntities();
    }
    public void hitTiles(){
        for(int i = 0; i < getHandler().getTiles().size(); i++){
            Tile tile = getHandler().getTiles().get(i);
            if(getBounds().intersects(tile.getBounds())){
                if(!active) {
                    hit();
                }
            }
        }
    }
    public void hitEntities(){
        for(int i = 0; i < getHandler().getEntities().size(); i++) {
            Entity entity = getHandler().getEntities().get(i);
            if (getBounds().intersects(entity.getBounds())) {
                if (entity.getId() == Id.bowser) {
                    if (!active) {
                        hit();
                    }
                    ((Bowser) entity).hurt();
                }
                if (entity.getId() == Id.player) {
                    if (!active) {
                        hit();
                    }
                    ((Player) entity).hitPlayer(this);
                }
                if ((entity.getId() != Id.player) && (entity.getId() != Id.bowser) && (entity.getId() != Id.nukeBomb)) {
                    if (!active) {
                        hit();
                    }
                    entity.die();
                }
            }
        }
    }
    public void hit(){
        setVelY(0);
        setX(getX() - 64);
        setHeight(64*3);
        setY(getY() - 64);
        setWidth(64*3);
        active = true;
    }
}
