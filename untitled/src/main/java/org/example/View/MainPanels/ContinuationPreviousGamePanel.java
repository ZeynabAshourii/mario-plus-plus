package org.example.View.MainPanels;

import org.example.Resources.Resources;
import org.example.View.Game;
import org.example.View.MainFrame;
import org.example.View.User;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ContinuationPreviousGamePanel extends JPanel implements ActionListener {
    private JButton game1;
    private JButton game2;
    private JButton game3;
    private JButton back;
    public User user;
    public ContinuationPreviousGamePanel(User user){
        this.user = user;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        game1 = new JButton("Game 1");
        this.add(game1);
        game1.setBounds(450,100,160, 80);
        game1.setFocusable(false);
        game1.addActionListener(this);
        game2 = new JButton("Game 2");
        this.add(game2);
        game2.setBounds(450 , 240 , 160 , 80);
        game2.setFocusable(false);
        game2.addActionListener(this);
        game3 = new JButton("Game 3");
        this.add(game3);
        game3.setBounds(450 , 380 , 160 , 80);
        game3.setFocusable(false);
        game3.addActionListener(this);
        back = new JButton("Back");
        this.add(back);
        back.setBounds(450 , 550 , 160 , 80);
        back.setFocusable(false);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(game1)){
            if(user.getGames()[0] == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }
            else {
                Game game = user.getGames()[0];
                startGame(game);
            }
        }
        else if(e.getSource().equals(game2)){
            if(user.getGames()[1] == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }else{
                Game game = user.getGames()[1];
                startGame(game);
            }
        }
        else if(e.getSource().equals(game3)){
            if(user.getGames()[2] == null){
                JOptionPane.showMessageDialog(this, "this game isn't exist");
            }else{
                Game game = user.getGames()[2];
                startGame(game);
            }
        } else if (e.getSource().equals(back)) {
            MainFrame.getInstance().setContentPane(new UserStarterPanel(user));
        }
    }
    public void startGame(Game game){
        game.setRunning(false);
        game.setPaused(false);
        game.setContinuation(true);
        game.setSigmaTime(game.getElapsedTime());
        game.setStartTime(System.nanoTime()/1000000000L);
        game.start();
        MainFrame.getInstance().setContentPane(game);
        game.setFocusable(true);
        game.requestFocus();
        game.requestFocusInWindow();
        if(game.isBossFight()){
            Resources.getBossFight().play();
        }
        else {
            Resources.getThemeSong().play();
        }
    }
}
