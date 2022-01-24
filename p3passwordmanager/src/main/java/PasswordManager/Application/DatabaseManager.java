package PasswordManager.Application;

import java.util.List;

import PasswordManager.Generators.DataEntry;

public class DatabaseManager implements GenericDataManager {
    private boolean databaseEnabled = Settings.DatabaseEnabled;

    @Override
    public void openLibrary() {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateLibrary() {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeLibrary() {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<DataEntry> getLibrary() {
        if (!databaseEnabled) {// the illusion of choice
            return null;
        }
        // TODO Auto-generated method stub
        return null;
    }
    
}
