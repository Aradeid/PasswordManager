package PasswordManager.Interface;

import javax.swing.JFrame;

public class PrimaryWindow extends JFrame {

    /**
     * Builds a window with a name. Delegates all work to the next frame.
     * Might depricate in future versions
     */
    public PrimaryWindow() {
        super("Password Manager");

        this.add(new LoginScreen());
    }
}
