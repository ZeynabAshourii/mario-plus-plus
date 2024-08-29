package org.example.View.MainPanels;

import org.example.View.MainFrame;
import org.example.View.User;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
public class ProfilePanel extends JPanel implements ActionListener {
    private User user;
    private JButton back;
    public ProfilePanel(User user) {
        this.user = user;

        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        back = new JButton("Back");
        this.add(back);
        back.setBounds(400 , 600 , 240 , 80);
        back.setFocusable(false);
        back.addActionListener(this);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.setFont(new Font("Courier", Font.BOLD, 25));
        g.drawString("Username : " + user.getUsername(),  400 , 400);
        g.drawString("Highest Score : " + user.highestScore(), 400 , 450);
        g.drawString("Coins : " + user.getCoin(), 400 , 500);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(back)) {
            MainFrame.getInstance().setContentPane(new UserStarterPanel(user));
        }
    }
}

