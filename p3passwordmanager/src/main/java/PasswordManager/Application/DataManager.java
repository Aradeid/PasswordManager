package PasswordManager.Application;

import java.util.List;

import PasswordManager.Generators.DataEntry;

public class DataManager implements GenericDataManager {
    private DatabaseManager dbMgr = new DatabaseManager();
    private FileManager fMgr = new FileManager();

    @Override
    public void openLibrary() {
        dbMgr.openLibrary();
        fMgr.openLibrary();
    }

    @Override
    public void updateLibrary() {
        dbMgr.updateLibrary();
        fMgr.updateLibrary();
    }

    @Override
    public void closeLibrary() {
        dbMgr.closeLibrary();
        fMgr.closeLibrary();
    }

    @Override
    public void addDataEntry(DataEntry entry) {
        dbMgr.addDataEntry(entry);
        fMgr.addDataEntry(entry);
    }

    @Override
    public void removeDataEntry(DataEntry entry) {
        dbMgr.removeDataEntry(entry);
        fMgr.removeDataEntry(entry);
    }

    @Override
    public void updateDataEntry(DataEntry entry) {
        dbMgr.updateDataEntry(entry);
        fMgr.updateDataEntry(entry);
    }

    @Override
    public List<DataEntry> getLibrary() {
        List<DataEntry> dbLib = dbMgr.getLibrary();
        //TODO resolve 2 libraries conflict

        List<DataEntry> fLib = fMgr.getLibrary();
        if (fLib == null) {
            this.openLibrary();
            fLib = fMgr.getLibrary();
        }
        return fLib;
    }
    
}
