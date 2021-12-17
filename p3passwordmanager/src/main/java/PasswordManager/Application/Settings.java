package PasswordManager.Application;

public class Settings {
    private int windowHeight;
    private int windowWidth;
    private boolean databaseEnabled;
    public Settings() {
        //TODO load settings from file

        windowHeight = 800;
        windowWidth = 1000;
        databaseEnabled = false;
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
}
