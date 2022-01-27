package PasswordManager.Interface.Components;

import javax.swing.JPasswordField;

public class PMPasswordField extends JPasswordField {
    /**
     * Basic contructor. Builds a password field of fixed character count.
     * @param name
     */
    public PMPasswordField() {
        this.setColumns(10);
    }
}