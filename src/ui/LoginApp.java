package ui;

import managers.ShelterManager;
import javax.swing.*;
import java.awt.*;

public class LoginApp {
    private ShelterManager shelterManager;

    public LoginApp() {
        shelterManager = new ShelterManager();
        initUI();
    }

    private void initUI() {
        JFrame frame = new JFrame("Animal Shelter Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Log in as:");
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label);

        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> {
            frame.dispose();
            ShelterApp adminApp = new ShelterApp(shelterManager, true);
            adminApp.initUI();
        });
        frame.add(adminButton);

        JButton userButton = new JButton("User");
        userButton.addActionListener(e -> {
            frame.dispose();
            ShelterApp userApp = new ShelterApp(shelterManager, false);
            userApp.initUI();
        });
        frame.add(userButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginApp::new);
    }
}

