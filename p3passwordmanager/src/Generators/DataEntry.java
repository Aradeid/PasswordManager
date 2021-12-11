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
        return entryName;
    }
    public void setLogin(String login) {
        this.entryLogin = login;
    }

    public PasswordEntry getPassword() {
        return entryPassword;
    }
}
