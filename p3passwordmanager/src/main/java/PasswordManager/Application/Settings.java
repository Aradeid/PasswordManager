package PasswordManager.Application;

public class Settings {
    //value used a window height
    final static public int WindowHeight = 400;
    //value used as window width
    final static public int WindowWidth = 600;
    //togglable value to enable offline mode. Should be loaded from file
    final static public boolean DatabaseEnabled = true;
    //file path for file manager. Should be user dependent
    final static public String LibraryFilePath = "passfileset.pmg";
    //file path for backup if file manager fails. Should be user dependent
    final static public String BackupLibraryFilePath = "passfileset.pmg.bak";
    //database connection string
    final static public String DatabaseUrl = "jdbc:mysql://localhost:3306/passwordmanager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    //database user, no pass for my current database
    final static public String DatabaseUser = "root";
    //only local user. Should be removed completely
    final static public String UserLogin = "TastyTesty";
    //noly user pass. Should be removed completely
    final static public String UserPass = "TastyPass";
}
