package org.example.View.MainPanels;

import org.example.View.MainFrame;
import org.example.View.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SignInPanel extends JPanel implements ActionListener {
    private TextField textFieldUsername;
    private JButton buttonSaveUsername;
    private boolean saveUsername = false;
    private TextField textFieldPassword;
    private JButton buttonSavePassword;
    private boolean savePassword = false;
    private JButton start;
    private JButton back;
    private String username;
    private String password;
    public SignInPanel(){
        this.setSize(1080, 771);
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();

        textFieldUsername = new TextField("username");
        this.add(textFieldUsername);
        textFieldUsername.setBounds(370,100,500,60);
        buttonSaveUsername = new JButton("save");
        this.add(buttonSaveUsername);
        buttonSaveUsername.setBounds(200,100,100,60);
        buttonSaveUsername.addActionListener(this);

        textFieldPassword = new TextField("password");
        this.add(textFieldPassword);
        textFieldPassword.setBounds(370,200,500,60);
        buttonSavePassword = new JButton("save");
        this.add(buttonSavePassword);
        buttonSavePassword.setBounds(200,200,100,60);
        buttonSavePassword.addActionListener(this);

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
        if(e.getSource().equals(buttonSaveUsername)){
            username = textFieldUsername.getText();
            saveUsername = true;
        }
        else if(e.getSource().equals(buttonSavePassword)){
            password = textFieldPassword.getText();
            savePassword = true;
        }
        else if(e.getSource().equals(start)){
            if(!saveUsername){
                JOptionPane.showMessageDialog(this, "click on save for username");
            }
            else if(!savePassword){
                JOptionPane.showMessageDialog(this, "click on save for password");
            }
            else {
                nextPanel();
            }
        }
        else if(e.getSource().equals(back)){
            MainFrame.getInstance().setContentPane(new StartPanel());
        }
    }
    public void nextPanel(){
        boolean find = false;
        for(int i = 0; i < User.getUsers().size(); i++){
            User user = User.getUsers().get(i);
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                find = true;
                MainFrame.getInstance().setContentPane(new UserStarterPanel(user));
                break;
            }
        }
        if(!find){
            JOptionPane.showMessageDialog(this, "username or password is wrong");
        }
    }
}
