package PasswordManager.Interface.Components;

import javax.swing.JLabel;

public class PMLabel extends JLabel {
    /**
     * Basic contructor. Builds a label of fixed size.
     * @param name
     */
    public PMLabel(String name) {
        super(name);
        this.setSize(100,30);
    }
}
