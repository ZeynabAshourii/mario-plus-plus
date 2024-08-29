package org.example.View.MainPanels;

import org.example.View.MainFrame;
import org.example.View.User;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserStarterPanel extends JPanel implements ActionListener {
    private JButton startNewGame;
    private JButton continuationPreviousGame;
    private JButton highestResults;
    private JButton profile;
    private JButton back;
    public User user;
    public UserStarterPanel(User user){
        this.user = user;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        startNewGame = new JButton("Start New game");
        this.add(startNewGame);
        startNewGame.setBounds(400,60,240, 80);
        startNewGame.setFocusable(false);
        startNewGame.addActionListener(this);

        continuationPreviousGame = new JButton("Continuation of the Previous Game");
        this.add(continuationPreviousGame);
        continuationPreviousGame.setBounds(400,180,240, 80);
        continuationPreviousGame.setFocusable(false);
        continuationPreviousGame.addActionListener(this);

        highestResults = new JButton("Highest Results");
        this.add(highestResults);
        highestResults.setBounds(400 , 300 , 240 , 80);
        highestResults.setFocusable(false);
        highestResults.addActionListener(this);

        profile = new JButton("Profile");
        this.add(profile);
        profile.setBounds(400 , 420 , 240 , 80);
        profile.setFocusable(false);
        profile.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(400 , 540 , 240 , 80);
        back.setFocusable(false);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(startNewGame)){
            MainFrame.getInstance().setContentPane(new StartNewGamePanel(user));
        }
        else if(e.getSource().equals(continuationPreviousGame)){
            MainFrame.getInstance().setContentPane(new ContinuationPreviousGamePanel(user));
        }
        else if(e.getSource().equals(highestResults)){
            MainFrame.getInstance().setContentPane(new HighestResultsPanel(user));
        }
        else if (e.getSource().equals(profile)) {
            MainFrame.getInstance().setContentPane(new ProfilePanel(user));

        } else if (e.getSource().equals(back)) {
            MainFrame.getInstance().setContentPane(new StartPanel());
        }
    }


}

