package PasswordManager.Interface;

import javax.swing.JFrame;

public class PrimaryWindow extends JFrame {

    public PrimaryWindow() {
        super("Password Manager");

        this.add(new LoginScreen());
    }
}
