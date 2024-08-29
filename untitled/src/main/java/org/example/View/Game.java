package org.example.View;

import org.example.ConfigLoader.Level;
import org.example.Controller.*;
import org.example.Model.Entity.Entity;
import org.example.Model.Entity.Player;
import org.example.Model.Id;
import org.example.Model.PlayerState;
import org.example.Resources.Resources;
import org.example.View.MainPanels.CreateGamePanel;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Game extends JPanel implements Runnable , Serializable {
    private User user;
    private int lives;
    private int marioState;
    private int whichGame;
    private int score = 0;
    private int coin = 0;
    private int level = 0;
    private int section = 0;
    private int timeLimit;
    private int deathScreenTime = 0;
    private int showFinishedGamePic = 0;
    private Handler handler;
    private Camera camera;
    private boolean showDeathScreen = false;
    private boolean finishedGame = false;
    private boolean gameOver = false;
    private boolean paused = false;
    private boolean running = false;
    private boolean mute = false;
    private boolean isBossFight = false;
    private boolean continuation = false;
    private long startTime;
    private long endTime;
    private long elapsedTime;
    private long remainingTime;
    private long sigmaTime = 0;
    private LinkedList<Level> levels;

    public Game(User user , LinkedList<Level> levels , int lives , int marioState , int whichGame){
        this.user = user;
        this.levels = levels;
        this.lives = lives;
        this.marioState = marioState;
        this.whichGame = whichGame;
        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        this.start();
    }
    public void init(){
        Resources.setMute(false);
        if(!continuation) {
            handler = new Handler(this, levels, marioState);
            updateSection();
            camera = new Camera();
        }
        else {
            continuation = false;
        }
        addKeyListener(new KeyInput(handler));
    }
    public synchronized void start(){
       if(running){
           return;
       }
       running = true;
       Thread thread = new Thread(this , "Thread");
       thread.start();
    }
    public synchronized void stop(){
        setMute(true);
        if(!running){
            return;
        }
        running = false;
    }

    @Override
    public void run() {
        init();
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int updates = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while (delta >= 1){
                update();
                updates++;
                delta--;
            }
            repaint();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }

    @Override
    public void paint(Graphics g) {
        try {
            super.paint(g);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            if(!showDeathScreen && !gameOver && !finishedGame){
                alivePaint(g);
            }
            if(showDeathScreen){
                deathScreenPaint(g);
            }
            if(finishedGame && !gameOver){
                g.setColor(Color.green);
                g.drawString("VICTORY", 610, 270);
                g.drawString("Game Finished", 610, 370);
            }
            g.translate(camera.getX(), camera.getY());
            if(!showDeathScreen) handler.paint(g);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void alivePaint(Graphics g){
        g.setColor(Color.white);
        g.drawString( "Time : " + String.valueOf(remainingTime), 150 , 20 );
        g.drawImage(Resources.getCoinPic().getSheet(), 50, 20, 75, 75, null);
        g.setColor(Color.yellow);
        g.drawString("x" + coin, 100, 95);
        g.setColor(Color.red);
        g.drawString("Score : " + score, 150 , 40);
        g.setColor(Color.green);
        g.drawString("Level : " + (level+1) , 360 , 20);
        g.drawString("Section : " + (section+1) , 360 , 40);
        g.setColor(Color.orange);
        g.drawString(" L I V E S : " + lives , 180 , 70);
    }
    public void deathScreenPaint(Graphics g){
        if(!gameOver) {
            g.drawImage(Resources.getMarioPic()[0].getSheet(), 500, 300, 100, 100, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 50));
            g.drawString("x" + lives, 610, 370);
        }
        else{
            g.drawImage(Resources.getMarioPic()[0].getSheet(), 500, 300, 100, 100, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 50));
            g.drawString("Game Over", 610, 370);
        }
    }

    public void update(){
        if(!paused) {
            timeCalculation();
            for (Entity entity : handler.getEntities()) {
                if (entity.getId() == Id.player) {
                    camera.update(entity);
                }
            }
            if (showDeathScreen && !gameOver) {
                deathScreenTime++;
            }
            if (deathScreenTime >= 100) {
                endDeathTime();
            }
            if (finishedGame) {
                finishedGame();
            }
        }
        else{
            sigmaTime = elapsedTime;
            startTime = System.nanoTime()/1000000000L;
        }
    }
    public void timeCalculation(){
        handler.update();
        endTime = System.nanoTime()/1000000000L;
        elapsedTime = endTime - startTime + sigmaTime;
        remainingTime = timeLimit - elapsedTime;
        if(remainingTime < 0){
            endTime();
        }
    }
    public void finishedGame(){
        if (isBossFight()) {
            Resources.getBossFight().stop();
        } else {
            Resources.getThemeSong().stop();
        }
        if(!gameOver) Resources.getEndOfGame().play();
        user.getScores().add(score);
        user.setCoin(coin);
        user.getGames()[whichGame-1] = null;
        handler.clearLevel();
        showFinishedGamePic++;
        if(showFinishedGamePic >= 100){
            MainFrame.getInstance().setContentPane(new CreateGamePanel(whichGame , user));
            stop();
        }
    }
    public void endTime(){
        for(int i = 0; i < handler.getEntities().size(); i++){
            Entity entity = handler.getEntities().get(i);
            if(entity.getId() == Id.player){
                entity.die();
            }
        }
    }
    public void endDeathTime(){
        showDeathScreen = false;
        deathScreenTime = 0;
        elapsedTime = handler.getCheckPoint().getTime();
        sigmaTime = elapsedTime;
        startTime = System.nanoTime()/1000000000L;
        score = handler.getCheckPoint().getScore();
        coin = handler.getCheckPoint().getCoin();
        handler.backToCheckPoint();
        if(isBossFight()){
            Resources.getThemeSong().stop();
            Resources.getBossFight().play();
        }
        else {
            Resources.getThemeSong().play();
        }
    }
    public void switchLevel() {
        if(!finishedGame) {
            section++;
            if (section == levels.get(level).sections.size()) {
                Resources.getEndOfLevel().play();
                section = 0;
                level++;
            }
            if (level == levels.size()) {
                finishedGame = true;
            }
            updateSection();
        }
    }
    public void updateSection(){
        if(!finishedGame) {
            sigmaTime = 0;
            startTime = System.nanoTime() / 1000000000L;
            setMarioState();
            handler.clearLevel();
            handler.createLevel();
            if (isBossFight()) {
                Resources.getThemeSong().stop();
                Resources.getBossFight().play();
            } else {
                Resources.getThemeSong().play();
            }
        }
    }
    public void setMarioState(){
        for(int i = 0; i < handler.getEntities().size(); i++){
            if(handler.getEntities().get(i).getId() == Id.player){
                Player player = (Player) handler.getEntities().get(i);
                if(player.getState() == PlayerState.MINI){
                    handler.setMarioState(0);
                } else if (player.getState() == PlayerState.MEGA) {
                    handler.setMarioState(1);
                } else if (player.getState() == PlayerState.FIRE) {
                    handler.setMarioState(2);
                }
            }
        }
    }
    public long getElapsedTime() {
        return elapsedTime;
    }
    public int getSection() {
        return section;
    }
    public int getLevel() {
        return level;
    }
    public int getLives() {
        return lives;
    }
    public int getScore() {
        return score;
    }
    public int getCoin() {
        return coin;
    }
    public void setMute(boolean mute){
        this.mute = mute;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public void setShowDeathScreen(boolean showDeathScreen) {
        this.showDeathScreen = showDeathScreen;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public void setFinishedGame(boolean finishedGame) {
        this.finishedGame = finishedGame;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public void setCoin(int coin) {
        this.coin = coin;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    public boolean isBossFight() {
        return isBossFight;
    }
    public void setBossFight(boolean bossFight) {
        isBossFight = bossFight;
    }
    public boolean isShowDeathScreen() {
        return showDeathScreen;
    }
    public User getUser(){
        return user;
    }
    public Handler getHandler() {
        return handler;
    }
    public void setSigmaTime(long sigmaTime) {
        this.sigmaTime = sigmaTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }
    public void setContinuation(boolean continuation) {
        this.continuation = continuation;
    }
}