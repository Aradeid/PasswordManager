package PasswordManager.Interface.Components;

import javax.swing.JButton;

public class PMButton extends JButton {
    /**
     * Basic contructor. Builds a button of fixed size.
     * @param name
     */
    public PMButton(String name) {
        super(name);
        this.setSize(100,30);
    }
}
