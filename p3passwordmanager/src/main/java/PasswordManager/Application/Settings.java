package PasswordManager.Application;

public class Settings {
    final static public int WindowHeight = 400;
    final static public int WindowWidth = 600;
    final static public boolean DatabaseEnabled = true;
    final static public String LibraryFilePath = "passfileset.pmg";
    final static public String BackupLibraryFilePath = "passfileset.pmg.bak";
    final static public String DatabaseUrl = "jdbc:mysql://localhost:3306/passwordmanager?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    final static public String DatabaseUser = "root";
    final static public String UserLogin = "TastyTesty";
    final static public String UserPass = "TastyPass";
}
