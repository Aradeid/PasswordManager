package PasswordManager.Application;

public class Settings {
    private int windowHeight;
    private int windowWidth;
    private boolean databaseEnabled;
    private String libraryFilePath;
    private String backupLibraryFilePath;
    public Settings() {
        //TODO load settings from file

        windowHeight = 400;
        windowWidth = 600;
        databaseEnabled = false;
        libraryFilePath = "passfileset.pmg";
        backupLibraryFilePath = "passfileset.pmg.bak";
    }

    private void updateSettingsFile() {
        //TODO implement file handling
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int height) {
        //TODO save to file
        windowHeight = height;
        updateSettingsFile();
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int width) {
        //TODO save to file
        windowWidth = width;
        updateSettingsFile();
    }

    public boolean getDatabaseEnabled() {
        return databaseEnabled;
    }

    public void setDatabaseEnabled(boolean value) {
        //TODO save to file
        databaseEnabled = value;
        updateSettingsFile();
    }

    public String getLibraryFilePath() {
        return libraryFilePath;
    }

    public void setLibraryFilePath(String path) {
        this.libraryFilePath = path;
    }

    public String getBackupLibraryFilePath() {
        return backupLibraryFilePath;
    }

    public void setBackupLibraryFilePath(String path) {
        this.backupLibraryFilePath = path;
    }
}
