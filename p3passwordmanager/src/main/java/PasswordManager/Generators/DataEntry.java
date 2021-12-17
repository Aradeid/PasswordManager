package PasswordManager.Generators;

import java.io.Serializable;
import java.util.Vector;

public class DataEntry implements Serializable {
    private String entryName;
    private String entryLogin;
    private PasswordEntry entryPassword;

    private static final long serialVersionUID = 84727283795663L;

    public DataEntry(String name, String login, String password) {
        this.entryName = name;
        this.entryLogin = login;
        this.entryPassword = new PasswordEntry(password);
    }

    public DataEntry(String name, String login, PasswordEntry password) {
        this.entryName = name;
        this.entryLogin = login;
        this.entryPassword = password;
    }

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

    public String getPasswordValue() {
        return entryPassword.getPassword();
    }

    public void updatePassword() {
        entryPassword.generatePassword();
    }

    public void setPassword(String newpass) {
        entryPassword.setPassword(newpass);
    }

    public boolean recoverPassword() {
        return entryPassword.restorePassword();
    }

    @Override
    public String toString() {
        return "DataEntry["
            + "Name=" + getName()
            + ",Login=" + getLogin()
            + ",Password=" + "*".repeat(getPasswordValue().length())
            + "]";
    }

    public static Vector<String> getKeys() {
        Vector<String> returnArray = new Vector<>(3);
        returnArray.add("Name");
        returnArray.add("Login");
        returnArray.add("Password");
        return returnArray;
    }

    public Vector<String> toArray() {
        Vector<String> returnArray = new Vector<>(3);
        returnArray.add(getName());
        returnArray.add(getLogin());
        returnArray.add(getPasswordValue());
        return returnArray;
    }
}
