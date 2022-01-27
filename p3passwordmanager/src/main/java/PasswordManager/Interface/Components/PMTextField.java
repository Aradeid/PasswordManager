package PasswordManager.Interface.Components;

import javax.swing.JTextField;

public class PMTextField extends JTextField {
    /**
     * Basic contructor. Builds a field of fixed character count.
     * @param name
     */
    public PMTextField() {
        this.setColumns(10);
    }
}
