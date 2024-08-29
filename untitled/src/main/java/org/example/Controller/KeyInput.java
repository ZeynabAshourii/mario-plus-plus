package org.example.Controller;

import org.example.Model.Entity.Enemies.Bowser;
import org.example.Model.Entity.Entity;
import org.example.Model.Entity.FireBall;
import org.example.Model.Entity.Player;
import org.example.Model.Id;
import org.example.Model.PlayerState;
import org.example.Model.Tile.Pipes.PiranhaTelePipe;
import org.example.Model.Tile.Pipes.TeleSimplePipe;
import org.example.Model.Tile.Tile;
import org.example.View.MainFrame;
import org.example.View.StopPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyInput implements KeyListener , Serializable {
    public Handler handler;
    private long startTimeFireBall;
    public KeyInput(Handler handler) {
        this.handler = handler;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(shouldAction()) return;
        switch (key) {
            case KeyEvent.VK_W:
                player().setSwordW(true);
                if(!player().isSwordS()) {
                    if (!player().isGrabAttack() && !player().isJumpAttack()) {
                        wKey();
                    } else if (player().isJumpAttack()) {
                        dKey();
                    }
                }
                break;
            case KeyEvent.VK_S:
                player().setSwordS(true);
                if(!player().isSwordW()) {
                    if (!player().isGrabAttack() && !player().isJumpAttack()) {
                        sKey();
                    } else if (player().isJumpAttack()) {
                        aKey();
                    }
                }
                break;
            case KeyEvent.VK_D:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    dKey();
                } else if (player().isJumpAttack()) {
                    wKey();
                }
                break;
            case KeyEvent.VK_A:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    aKey();
                } else if (player().isJumpAttack()) {
                    sKey();
                }
                break;
            case KeyEvent.VK_ENTER:
                if(!player().isGrabAttack()) {
                    enterKey();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                handler.getGame().setPaused(true);
                MainFrame.getInstance().setContentPane(new StopPanel(handler.getGame()));
                break;

            case KeyEvent.VK_X:
                xKey();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (shouldAction()) return;
        switch (key) {
            case KeyEvent.VK_W:
                player().setSwordW(false);
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelY(0);
                } else if (player().isJumpAttack()) {
                    player().setVelX(0);
                }
                break;
            case KeyEvent.VK_S:
                player().setSwordS(false);
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelY(0);
                    if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                        player().setY(player().getY() - 64);
                        player().setHeight(128);
                        player().setSitting(false);
                    }
                }else if (player().isJumpAttack()) {
                    player().setVelX(0);
                }
                break;
            case KeyEvent.VK_D:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelX(0);
                }else if(player().isGrabAttack()) {
                    player().setNumberOfAD(player().getNumberOfAD()+1);
                } else if (player().isJumpAttack()) {
                    player().setVelY(0);
                }
                break;
            case KeyEvent.VK_A:
                if(!player().isGrabAttack() && !player().isJumpAttack()) {
                    player().setVelX(0);
                }else if (player().isGrabAttack()){
                    player().setNumberOfAD(player().getNumberOfAD()+1);
                } else if (player().isJumpAttack()) {
                    player().setVelY(0);
                    if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                        player().setY(player().getY() - 64);
                        player().setHeight(128);
                        player().setSitting(false);
                    }
                }
                break;
        }

    }
    public void wKey(){
        if (!player().isJumping() && !player().isSitting()) {
            player().setJumping(true);
            player().setGravity(10.0);
        }
    }
    public void sKey(){
        for (int j = 0; j < handler.getTiles().size(); j++) {
            Tile tile = handler.getTiles().get(j);
            if (tile.getId() == Id.teleSimplePipe) {
                TeleSimplePipe teleSimplePipe = (TeleSimplePipe) tile;
                if (teleSimplePipe.getFacing() == 2 && player().getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player().isGoingDownPipe()) {
                        player().setGoingDownPipe(true);
                    }
                }
            }
            if (tile.getId() == Id.piranhaTele) {
                PiranhaTelePipe piranhaTelePipe = (PiranhaTelePipe) tile;
                if (piranhaTelePipe.getFacing() == 2 && player().getBoundsBottom().intersects(tile.getBounds())) {
                    if (!player().isGoingDownPipe()) {
                        player().setGoingDownPipe(true);
                    }
                }
            }
            if (player().getState() != PlayerState.MINI && !player().isJumping()) {
                if (!player().isSitting()) {
                    player().setY(player().getY() + 64);
                    player().setSitting(true);
                }
                player().setHeight(64);
            }
        }
    }
    public void aKey(){
        player().setVelX(-5);
        player().setFacing(0);
    }
    public void dKey(){
        player().setVelX(5);
        player().setFacing(1);
    }
    public void xKey(){
        for(int j = 0; j < handler.getEntities().size(); j++){
            if(handler.getEntities().get(j).getId() == Id.bowser){
                Bowser bowser = (Bowser) handler.getEntities().get(j);
                if(bowser.isCutScene()) {
                    bowser.setxKey(true);
                }
            }
        }
    }
    public void enterKey(){
        if (player().getState() == PlayerState.FIRE) {
            if(System.nanoTime() / 1000000000L - startTimeFireBall >= 2 ) {
                switch (player().getFacing()) {
                    case 0:
                        startTimeFireBall = System.nanoTime() / 1000000000L;
                        handler.addEntity(new FireBall(player().getX() - 24, player().getY() + 16, 24, 24, Id.fireBall, handler, player().getFacing()));
                        break;
                    case 1:
                        startTimeFireBall = System.nanoTime() / 1000000000L;
                        handler.addEntity(new FireBall(player().getX() + player().getWidth(), player().getY() + 16, 24, 24, Id.fireBall, handler, player().getFacing()));
                        break;
                }
            }
        }
    }
    public Player player(){
        Player player = null;
        for(int i = 0; i < handler.getEntities().size(); i++){
            Entity entity = handler.getEntities().get(i);
            if(entity.getId() == Id.player){
                player = (Player) entity;
            }
        }
        return player;
    }
    public boolean shouldAction(){
        if(player() == null) {
            return true;
        } else if (player().isGoingDownPipe() || player().isCutScene()) {
            return true;
        }
        return false;
    }
}
