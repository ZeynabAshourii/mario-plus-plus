package org.example.View.MainPanels;

import org.example.View.MainFrame;
import org.example.View.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
public class SignUpPanel extends JPanel implements ActionListener {
    private TextField textField1;
    private JButton button1;
    private boolean save1 = false;
    private TextField textField2;
    private JButton button2;
    private boolean save2 = false;
    private JButton start;
    private JButton back;
    private String username;
    private String password;
    public SignUpPanel(){
        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        textField1 = new TextField("username");
        this.add(textField1);
        textField1.setBounds(370,100,500,60);
        button1 = new JButton("save");
        this.add(button1);
        button1.setBounds(200,100,100,60);
        button1.addActionListener(this);

        textField2 = new TextField("password");
        this.add(textField2);
        textField2.setBounds(370,200,500,60);
        button2 = new JButton("save");
        this.add(button2);
        button2.setBounds(200,200,100,60);
        button2.addActionListener(this);

        back = new JButton("Back");
        this.add(back);
        back.setBounds(460 , 480 , 160 , 80);
        back.addActionListener(this);

        start = new JButton(" Start !");
        this.add(start);
        start.setBounds(460 , 590 , 160 , 80);
        start.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(button1)){
            username = textField1.getText();
            save1 = true;
        }
        else if(e.getSource().equals(button2)){
            password = textField2.getText();
            save2 = true;
        }
        else if(e.getSource().equals(start)){
            if(!save1){
                JOptionPane.showMessageDialog(this, "click on save for username");
            }
            else if(!save2){
                JOptionPane.showMessageDialog(this, "click on save for password");
            }
            else {
                try {
                    User user = new User(username, password);
                    User.writeUser(user);
                    MainFrame.getInstance().setContentPane(new UserStarterPanel(user));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if(e.getSource().equals(back)){;
            MainFrame.getInstance().setContentPane(new StartPanel());
        }
    }
}
