package PasswordManager.Generators;

import java.io.Serializable;

public class DataEntry implements Serializable {
    private String entryName;
    private String entryLogin;
    private PasswordEntry entryPassword;

    public String getName() {
        return entryName;
    }
    public void setName(String name) {
        this.entryName = name;
    }

    public String getLogin() {
        return entryLogin;
    }
    public void setLogin(String login) {
        this.entryLogin = login;
    }

    public PasswordEntry getPassword() {
        return entryPassword;
    }

    public void updatePassword() {
        entryPassword.generatePassword();
    }

    public void updatePassword(String newpass) {
        entryPassword.setPassword(newpass);
    }
}
