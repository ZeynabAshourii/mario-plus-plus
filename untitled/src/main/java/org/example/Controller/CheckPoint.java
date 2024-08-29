package org.example.Controller;

import org.example.ConfigLoader.Level;
import org.example.Model.Entity.Enemies.EnemyGame;
import org.example.Model.Entity.Entity;
import org.example.Model.Id;
import org.example.Model.Tile.Gate;
import org.example.Model.Tile.Tile;
import java.io.Serializable;
import java.util.LinkedList;
public class CheckPoint implements Cloneable , Serializable {
    private long time;
    private int PR;
    private int score;
    private int coin;
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private Level level;
    private Handler handler;
    public CheckPoint(Handler currentHandler){
        entities.clear();
        tiles.clear();
        this.handler = currentHandler.clone();
        this.level = handler.getCurrentLevel();
        this.time = handler.getGame().getElapsedTime();
        this.score = handler.getGame().getScore();
        this.coin = handler.getGame().getCoin();
        enemyCopy();
        gateCopy();
        PR = handler.getGame().getCoin()*dx()/level.length();
        currentHandler.setCheckPoint(this);
        currentHandler.setNumberOfCheckPoint(currentHandler.getNumberOfCheckPoint()+1);
    }
    public void enemyCopy(){
        for(int i = 0; i < handler.getEntities().size(); i++){
            Entity entity = handler.getEntities().get(i).clone();
            if(handler.getEntities().get(i) instanceof EnemyGame){
                ((EnemyGame) handler.getEntities().get(i)).setEnemyCopy((EnemyGame) entity);
            }
            entities.add(entity);
        }
    }
    public void gateCopy(){
        for(int i = 0; i < handler.getTiles().size(); i++){
            Tile tile = handler.getTiles().get(i).clone();
            if(handler.getTiles().get(i) instanceof Gate){
                ((Gate) handler.getTiles().get(i)).setGateCopy((Gate)tile);
            }
            tiles.add(tile);
        }
    }
    public int dx(){
        int dx = 0;
        for(int i = 0; i < handler.getGame().getSection(); i++){
            dx += level.sections.get(i).length;
        }
        for (int i = 0; i < entities.size(); i++){
            if(entities.get(i).getId() == Id.player){
                dx += (entities.get(i).getX()-64);
            }
        }
        return dx;
    }

    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public long getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public int getCoin() {
        return coin;
    }
    public int getPR() {
        return PR;
    }
}
