package org.example.View;

import org.example.Controller.CheckPoint;
import org.example.Controller.Handler;
import org.example.Model.Id;
import org.example.Model.Tile.Gate;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CheckPointPanel extends JPanel implements ActionListener {
    public Handler handler;
    public Gate gate;
    private JButton yes;
    private JButton no;
    public CheckPointPanel(Handler handler, Gate gate){
        this.handler = handler;
        this.gate = gate;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        yes = new JButton("yes");
        this.add(yes);
        yes.setBounds(460 , 320 , 160 , 80);
        yes.setFocusable(false);
        yes.addActionListener(this);
        no = new JButton("no");
        this.add(no);
        no.setBounds(460 , 440 , 160 , 80);
        no.setFocusable(false);
        no.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(yes)){
            new CheckPoint(handler);
            handler.getGame().setCoin(handler.getGame().getCoin() - handler.getCheckPoint().getPR());
            resumeGame(handler.getGame());
        }
        else if(e.getSource().equals(no)){
            gate.die();
            handler.getGame().setCoin(handler.getGame().getCoin() + PR()/4);
            resumeGame(handler.getGame());
        }
    }
    public int dx(){
        int dx = 0;
        for(int i = 0; i < handler.getGame().getSection(); i++){
            dx += handler.getCurrentLevel().sections.get(i).length;
        }
        for (int i = 0; i < handler.getEntities().size(); i++){
            if(handler.getEntities().get(i).getId() == Id.player){
                dx += (handler.getEntities().get(i).getX()-64);
            }
        }
        return dx;
    }
    public int PR(){
         return handler.getGame().getCoin()*dx()/handler.getCurrentLevel().length();
    }
    public void resumeGame(Game game){
        game.setPaused(false);
        MainFrame.getInstance().setContentPane(game);
        game.setFocusable(true);
        game.requestFocus();
        game.requestFocusInWindow();
    }
}

