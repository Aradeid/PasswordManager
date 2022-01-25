package PasswordManager.Application;

import java.util.List;

import PasswordManager.Database.Dao.DataEntryDao;
import PasswordManager.Database.Dao.PasswordEntryDao;
import PasswordManager.Generators.DataEntry;

public class DatabaseManager implements GenericDataManager {
    private boolean databaseEnabled = Settings.DatabaseEnabled;
    private DataEntryDao dataDao;
    private PasswordEntryDao passDao;
    private static List<DataEntry> passLibrary;

    @Override
    public void openLibrary() {
        if (!databaseEnabled) {
            return;
        }
        dataDao = new DataEntryDao();
        passDao = new PasswordEntryDao();
        passLibrary = dataDao.getAll();        
    }


    @Override
    public void updateLibrary() {
        //does nothing, as database is managed at entry level
    }

    @Override
    public void closeLibrary() {
        //does nothing, as database connection is closed on each transaction
    }

    @Override
    public void addDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        if (dataDao != null) {
            dataDao.add(entry);
        }        
    }

    @Override
    public void removeDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        if (dataDao != null) {
            dataDao.remove(entry);
        }
    }

    @Override
    public void updateDataEntry(DataEntry entry) {
        if (!databaseEnabled) {
            return;
        }
        if (dataDao != null) {
            dataDao.update(entry);
        }
    }

    @Override
    public List<DataEntry> getLibrary() {
        if (!databaseEnabled) {
            return null;
        }
        return passLibrary;
    }
    
}
