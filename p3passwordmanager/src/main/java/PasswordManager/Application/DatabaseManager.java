package PasswordManager.Application;

import java.sql.Timestamp;
import java.util.List;

import PasswordManager.Database.Dao.DataEntryDao;
import PasswordManager.Database.Dao.PasswordEntryDao;
import PasswordManager.Generators.DataEntry;

public class DatabaseManager implements GenericDataManager {
    private boolean databaseEnabled = Settings.DatabaseEnabled;
    private DataEntryDao dataDao;
    private PasswordEntryDao passDao;
    private List<DataEntry> passLibrary;

    /**
     * Prepares libraries for usage
     */
    @Override
    public void openLibrary() {
        if (!databaseEnabled) {
            return;
        }
        dataDao = new DataEntryDao();
        passDao = new PasswordEntryDao();
        passLibrary = dataDao.getAll();        
    }


    /**
     * Checks entries for local-only ones
     */
    @Override
    public void updateLibrary() {
        passLibrary.forEach(entry -> {
            if (entry.getId() < 0) {
                dataDao.add(entry);
            }
        });
    }

    /**
     * does nothing, as database connection is closed on each transaction
     */
    @Override
    public void closeLibrary() {
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

    public void setLibrary(List<DataEntry> library) {
        passLibrary.forEach(entry -> {
            if (entry.getId() < 0) {
                dataDao.add(entry);
            } else {
                dataDao.update(entry);
            }
        });
    } 

    /**
     * Returns the latest timestamp. Used to see which manager is most up to date
     * 
     * @return biggest timestamp, or 0 if none found
     */
    public Timestamp getMostRecentTimestamp() {
        if (passLibrary == null) {
            return new Timestamp(0);
        }
        return new Timestamp(passLibrary.stream().mapToLong(entry -> entry.getTimeUpdated().getTime()).max().orElse(0));
    }
    
}
