package org.example.Controller;

import org.example.ConfigLoader.*;
import org.example.Model.Entity.Enemies.*;
import org.example.Model.Entity.Entity;
import org.example.Model.Entity.Items.*;
import org.example.Model.Entity.Player;
import org.example.Model.Id;
import org.example.Model.PlayerState;
import org.example.Model.Tile.Blocks.*;
import org.example.Model.Tile.Flag;
import org.example.Model.Tile.Gate;
import org.example.Model.Tile.Pipes.PiranhaTelePipe;
import org.example.Model.Tile.Pipes.PiranhaTrapPipe;
import org.example.Model.Tile.Pipes.SimplePipe;
import org.example.Model.Tile.Pipes.TeleSimplePipe;
import org.example.Model.Tile.Tile;
import org.example.View.Game;
import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Handler implements Cloneable , Serializable {
    private int deathY = 0;
    private int length;
    private int marioState;
    private int numberOfCheckPoint;
    private Game game;
    private Level currentLevel;
    private Section currentSection;
    private CheckPoint checkPoint;
    private LinkedList<Entity> entities = new LinkedList<>();
    private LinkedList<Tile> tiles = new LinkedList<>();
    private LinkedList<Level> levels;

    public Handler(Game game , LinkedList<Level> levels , int marioState){
        this.game = game;
        this.levels = levels;
        this.marioState = marioState;
    }
    public void paint(Graphics g){
        for(int i = 0 ; i < entities.size(); i++){
            Entity entity = entities.get(i);
            entity.paint(g);
        }
        for(int i = 0 ; i < tiles.size(); i++){
            Tile tile = tiles.get(i);
            tile.paint(g);
        }
    }
    public void update() {
        for(int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            entity.update();
        }
        for(int i = 0; i < tiles.size(); i++){
            Tile tile = tiles.get(i);
            tile.update();
        }
    }
    public void createLevel() {
        deathY = 0;
        numberOfCheckPoint = -1;
        currentLevel = levels.get(game.getLevel());
        loadSection(currentLevel.sections.get(game.getSection()));
        new CheckPoint(this);
    }
    public void clearLevel(){
        entities.clear();
        tiles.clear();
    }
    public void loadSection(Section section){
        currentSection = section;
        length = currentSection.length;
        int timeLimit = currentSection.time;
        game.setTimeLimit(timeLimit);

        loadEnemies(section.enemies , 0 , -1);
        loadBlocks(section.blocks , 0 , 0);
        loadPipes(section.pipes , 0 , 0);

        if(!game.isBossFight()) {
            addTile(new Flag((length+1) * 64, (-3) * 64, 64, 48, Id.flag, this));
        }
        addTile(new Gate((length)*32 , (-9)*64 ,64 , 64 , Id.gate , this));

        if(marioState == 0){
            addEntity(new Player(64 , -2*64 , 64 , 64 , Id.player , this , PlayerState.MINI));
        } else if (marioState == 1) {
            addEntity(new Player(64 , -2*64 , 64 , 128 , Id.player , this , PlayerState.MEGA));
        } else if (marioState == 2) {
            addEntity(new Player(64 , -2*64 , 64 , 128 , Id.player , this , PlayerState.FIRE));
        }
    }
    public void loadEnemies(LinkedList<Enemy> enemies , int x , int y){
        for(int i = 0; i < enemies.size() ; i++){
            Enemy enemy = enemies.get(i);
            if(enemy.type.equals("GOOMBA")){
                addEntity(new Goomba((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.goomba , this));
            } else if (enemy.type.equals("KOOPA")) {
                addEntity(new Koopa((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.koopa , this));
            } else if (enemy.type.equals("SPINY")) {
                addEntity(new Spiny((x + enemy.x)*64 , (y - enemy.y )*(64) , 64 , 64 , Id.spiny, this));
            } else if (enemy.type.equals("BOWSER")) {
                game.setBossFight(true);
                addEntity(new Bowser((x + enemy.x)*64 , (y - enemy.y )*(64)-64*4 , 64 , 64*5 , Id.bowser , this));
            }
        }
    }
    public void loadBlocks(LinkedList<Block> blocks , int x , int y){
        loadNormalBlock(x , y);

        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.type.equals("SIMPLE")) {
                addTile(new SimpleBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.simpleBlock, this));
            } else if (block.type.equals("COIN")) {
                addTile(new OneCoinBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.oneCoinBlock, this));
            } else if (block.type.equals("COINS")) {
                addTile(new MultiCoinsBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.multiCoinsBlock, this));
            } else if (block.type.equals("EMPTY")) {
                addTile(new EmptyBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.emptyBlock, this));
            } else if (block.type.equals("QUESTION")) {
                Item item = null;
                if (block.item == null) {
                    int random = new Random().nextInt(10);
                    if(random == 0){
                        item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.coin, this);
                    } else if (random == 1 || random == 2) {
                        item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicStar, this);
                    } else if (random == 3 || random == 4 || random == 5) {
                        item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicMushroom, this);
                    } else if (random == 6 || random == 7 || random == 8 || random == 9) {
                        item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicFlower, this);
                    }
                }
                else if (block.item.equals("COIN")||block.item.equals("COINS")) {
                    item = new Coin((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.coin, this);
                } else if (block.item.equals("STAR")) {
                    item = new MagicStar((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicStar, this);
                } else if (block.item.equals("MUSHROOM")) {
                    item = new MagicMushroom((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicMushroom, this);
                } else if (block.item.equals("FLOWER")) {
                    item = new MagicFlower((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.magicFlower, this);
                }
                addTile(new QuestionBlock((x + block.x) * 64, (y-block.y) * 64, 64, 64, Id.questionBlock, this, item));
            }
        }
    }
    public void loadNormalBlock(int x , int y){
        if(game.isBossFight()){
            for(int i = 0; i < length ; i++){
                addTile(new EmptyBlock((x-1)*64 , (y-1-i)*64 , 64 , 64 , Id.emptyBlock , this));
            }
            for(int i = 0 ; i < length; i++){
                addTile(new EmptyBlock(64*(length+1+x) , (y-1-i)*64 , 64 , 64 , Id.emptyBlock , this));
            }
        }

        for(int i = -1; i <= length+1; i++){
            addTile(new EmptyBlock((x+i)*64 , y*64 , 64 , 64 , Id.emptyBlock , this));
        }
    }
    public void loadPipes(LinkedList<Pipe> pipes , int x , int y){
        Pipe hideSectionPipe = null;
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            if(pipe.section != null) {
                hideSectionPipe = pipe;
                break;
            }
        }
        if(hideSectionPipe == null){
            for(int i = 0; i < pipes.size(); i++){
                Pipe pipe = pipes.get(i);
                if(pipe.type.equals("SIMPLE")){
                    addTile(new SimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64, 64 , 64*3 , Id.simplePipe , this ));
                } else if (pipe.type.equals("TELE_SIMPLE")) {
                    addTile(new TeleSimplePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.teleSimplePipe , this , 2));
                } else if (pipe.type.equals("PIRANHA_TRAP")) {
                    addTile(new PiranhaTrapPipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.piranhaTrap , this));
                }else if (pipe.type.equals("TELE_PIRANHA")) {
                    addTile(new PiranhaTelePipe((x+pipe.x)*64 , (y-pipe.y-1)*64 , 64 , 64*3 , Id.piranhaTele , this , 2));
                }
            }
        }
        else {
            loadHideSection(hideSectionPipe , x , y);
        }
    }
    public void loadHideSection(Pipe hideSectionPipe , int x , int y){
        if (hideSectionPipe.type.equals("SIMPLE") || hideSectionPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.teleSimplePipe, this, 2));
        } else if (hideSectionPipe.type.equals("PIRANHA_TRAP") || hideSectionPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+hideSectionPipe.x) * 64, (y-hideSectionPipe.y-1)*64, 64, 64 * 3, Id.piranhaTele, this, 2));
        }
        Section hideSection = hideSectionPipe.section;
        Pipe spawnPipe = hideSection.spawnPipe;
        if (spawnPipe.type.equals("SIMPLE") || spawnPipe.type.equals("TELE_SIMPLE")) {
            addTile(new TeleSimplePipe(spawnPipe.x * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.teleSimplePipe, this, 0));
        } else if (spawnPipe.type.equals("PIRANHA_TRAP") || spawnPipe.type.equals("TELE_PIRANHA")) {
            addTile(new PiranhaTelePipe((x+spawnPipe.x) * 64, (y-spawnPipe.y-1)*64, 64, 64 * 3, Id.piranhaTele, this, 0));
        }
        int distance = Math.abs(spawnPipe.x - hideSectionPipe.x) + 1;
        int left = (hideSection.length - distance)/2 - 3;

        loadEnemies(hideSection.enemies , left-8 , 6);
        loadBlocks(hideSection.blocks , left , 7);
        loadPipes(hideSection.pipes , left , 8);
    }
    public void backToCheckPoint(){
        clearLevel();
        for(int i = 0; i < checkPoint.getEntities().size(); i++){
            Entity entity = checkPoint.getEntities().get(i).clone();
            if(entity.getId() == Id.player){
                Player player = (Player) entity;
                player.setState(PlayerState.MINI);
                player.setHeight(64);
            }
            entities.add(entity);
        }
        for(int i = 0; i < checkPoint.getTiles().size(); i++){
            Tile tile = checkPoint.getTiles().get(i).clone();
            tiles.add(tile);
        }
        new CheckPoint(this);
    }
    @Override
    public Handler clone() {
        try {
            Handler clone = (Handler) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public int getDeathY() {
        for(int i =0 ; i < tiles.size(); i++){
            if(tiles.get(i).getY() > deathY){
                deathY = tiles.get(i).getY();
            }
        }
        return deathY;
    }
    public void addEntity(Entity entity){
        entities.add(entity);
    }
    public void removeEntity(Entity entity){
        entities.remove(entity);
    }
    public void addTile(Tile tile){
        tiles.add(tile);
    }
    public void removeTile(Tile tile){
        tiles.remove(tile);
    }
    public int getNumberOfCheckPoint() {
        return numberOfCheckPoint;
    }
    public void setNumberOfCheckPoint(int numberOfCheckPoint) {
        this.numberOfCheckPoint = numberOfCheckPoint;
    }
    public CheckPoint getCheckPoint() {
        return checkPoint;
    }
    public void setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
    }
    public LinkedList<Entity> getEntities() {
        return entities;
    }

    public LinkedList<Tile> getTiles() {
        return tiles;
    }

    public Game getGame() {
        return game;
    }
    public Level getCurrentLevel() {
        return currentLevel;
    }
    public void setMarioState(int marioState) {
        this.marioState = marioState;
    }
    public int getLength() {
        return length;
    }
}

