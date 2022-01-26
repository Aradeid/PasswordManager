package PasswordManager.Interface;

import javax.swing.JFrame;

import PasswordManager.Application.Settings;

public class PrimaryWindow extends JFrame {

    public PrimaryWindow() {
        super("Password Manager");

        this.add(new LoginScreen());
    }
}
