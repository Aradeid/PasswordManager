package PasswordManager.Generators;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;

public class DataEntry implements Serializable {
    private int entryId; //part of the database to file conversion, still unsure on how to implement
    private String entryName;
    private String entryLogin;
    private Timestamp timeAdded;
    private Timestamp timeUpdated;
    private PasswordEntry entryPassword;

    private static final long serialVersionUID = 84727283795663L;

    /**
    * Basic contructor, generates a new entry from name, login, and password
    * Used for creating date entries locally
    *    
    * @param  name to be used as identifier among passwords
    * @param  login saved as the login for the given entry
    * @param  password string to be saved for password
    */
    public DataEntry(String name, String login, String password) {
        this.entryId = -1;
        this.entryName = name;
        this.entryLogin = login;
        this.entryPassword = new PasswordEntry(password);
        this.timeAdded = new Timestamp(System.currentTimeMillis());
        this.timeUpdated = timeAdded;
    }

    /**
    * Basic contructor, generates a new entry from name, login, and password
    * Used for creating date entries locally
    *    
    * @param  name to be used as identifier among passwords
    * @param  login saved as the login for the given entry
    * @param  password safe value to be saved for password
    */
    public DataEntry(String name, String login, PasswordEntry password) {
        this.entryId = -1;
        this.entryName = name;
        this.entryLogin = login;
        this.entryPassword = password;
        this.timeAdded = new Timestamp(System.currentTimeMillis());
        this.timeUpdated = timeAdded;
    }

    /**
    * Full contructor, generates a new entry from id, name, login, and passwordEntry, addTime, updateTime
    * Used to generate fully fledged entries from database
    *    
    * @param  name to be used as identifier among passwords
    * @param  login saved as the login for the given entry
    * @param  password PasswordEntry used a a complete password object
    */
    public DataEntry(int id, String name, String login, PasswordEntry password, Timestamp addTime, Timestamp updDateTime) {
        this.entryId = id;
        this.entryName = name;
        this.entryLogin = login;
        this.entryPassword = password;
        this.timeAdded = addTime;
        this.timeUpdated = updDateTime;
    }

    /**
    * Returns entry id. Used to keep track of its database value
    *    
    * @return id of this entry
    */
    public int getId() {
        return entryId;
    }

    /**
    * Sets entry id. Used to keep track of its database value
    *    
    * @param id of this entry
    */
    public void setId(int id) {
        this.entryId = id;
    }

    /**
    * Returns entry name. Used as an identifier among entries
    *    
    * @return name of this entry
    */
    public String getName() {
        return entryName;
    }

    /**
    * Sets entry name. Used as an identifier among entries
    *    
    * @param name of this entry
    */
    public void setName(String name) {
        this.entryName = name;
        this.updateTimeUpdated();
    }

    /**
    * Returns entry login/email/nickname
    *    
    * @return login of this entry
    */
    public String getLogin() {
        return entryLogin;
    }

    /**
    * Sets entry login
    *    
    * @param login value used for login/nickname/email
    */
    public void setLogin(String login) {
        this.entryLogin = login;

    }

    public PasswordEntry getPassword() {
        return entryPassword;
    }

    /**
    * Returns value of saved password as a String
    *    
    * @return password value as string
    */
    public String getPasswordValue() {
        return entryPassword.getPassword();
    }

    /**
    * Generates a new secure password, according to the given rules
    */
    public void updatePassword() {
        entryPassword.generatePassword();
        updateTimeUpdated();
    }

    /**
    * Sets entry password from given string
    *    
    * @param newpass password value to be set
    */
    public void setPassword(String newpass) {
        entryPassword.setPassword(newpass);
        updateTimeUpdated();
    }

    /**
    * Returns previous saved password. Both passwords are kept in object, in case of accident
    *    
    * @return true if a previous password exists, and and was therefore successful, false if no previous password 
    */
    public boolean recoverPassword() {
        updateTimeUpdated();
        return entryPassword.restorePassword();
    }

    /**
    * Returns entry creation datetime
    *    
    * @return time when entry was created
    */
    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    /**
    * Sets entry creation datetime
    *    
    * @param time value to be set
    */
    public void setTimeAdded(Timestamp time) {
        this.timeAdded = time;
    }

    /**
    * Returns entry update datetime
    *    
    * @return time when entry was updated
    */
    public Timestamp getTimeUpdated() {
        return timeUpdated;
    }

    /**
    * Sets entry update datetime
    *    
    * @param time value to be set
    */
    public void setTimeUpdated(Timestamp time) {
        this.timeUpdated = time;
    }

    /**
    * Sets entry update time to current time
    */
    public void updateTimeUpdated() {
        this.timeUpdated = new Timestamp(System.currentTimeMillis());
    }
       

    /**
    * Returns DataEntry as string
    *    
    * @return output is similar to json, with a String password, but censored
    */
    @Override
    public String toString() {
        return "DataEntry["
            + "Name=" + getName()
            + ",Login=" + getLogin()
            + ",Password=" + "*".repeat(getPasswordValue().length())
            + ",Updated=" + getTimeUpdated()
            + "]";
    }

    /**
    * Returns object keys, primarily for table display
    *    
    * @return Vector of Strings with all key names for class
    */
    public static Vector<String> getKeys() {
        Vector<String> returnArray = new Vector<>(4);
        returnArray.add("Name");
        returnArray.add("Login");
        returnArray.add("Password");
        returnArray.add("Last Updated");
        return returnArray;
    }

    /**
    * Basic contructor, generates a new entry from name, login, and password
    *    
    * @return Vector of Strings values for each field in object
    */
    public Vector<String> toArray() {
        Vector<String> returnArray = new Vector<>(4);
        returnArray.add(getName());
        returnArray.add(getLogin());
        returnArray.add(getPasswordValue());
        returnArray.add(getTimeUpdated().toString());
        return returnArray;
    }
}
