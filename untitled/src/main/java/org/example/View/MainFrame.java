package org.example.View;

import org.example.Resources.Resources;
import org.example.View.MainPanels.StartPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener {
    private static MainFrame instance;
    private StartPanel startPanel;
    public static final int MENU_WIDTH = 1080;
    public static final int MENU_HEIGHT = 771;
    public MainFrame() {
        try {
            User.readUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Resources.init();
        this.setSize(1080 , 771);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        startPanel = new StartPanel();
        this.setContentPane(startPanel);
        instance = this;
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public static MainFrame getInstance() {
        return instance;
    }

}


