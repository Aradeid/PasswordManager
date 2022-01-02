package PasswordManager.Application;

import java.util.List;

import PasswordManager.Generators.DataEntry;

//object built to hanle all external libraries and their conflicts
public class DataManager implements GenericDataManager {
    private DatabaseManager dbMgr = new DatabaseManager();
    private FileManager fMgr = new FileManager();

    /**
     * prepares filemanager and databasemanager for work
     */
    @Override
    public void openLibrary() {
        dbMgr.openLibrary();
        fMgr.openLibrary();
    }

    /**
     * updates values of filemanager and databasemanager
     */
    @Override
    public void updateLibrary() {
        dbMgr.updateLibrary();
        fMgr.updateLibrary();
    }

    /**
     * closes filemanager and databasemanager
     */
    @Override
    public void closeLibrary() {
        dbMgr.closeLibrary();
        fMgr.closeLibrary();
    }

    /**
     * adds gives dataentry to filemanager and databasemanager for work
     * 
     * @param entry value to be added
     */
    @Override
    public void addDataEntry(DataEntry entry) {
        dbMgr.addDataEntry(entry);
        fMgr.addDataEntry(entry);
    }

    /**
     * removes gives dataentry to filemanager and databasemanager for work
     * 
     * @param entry value to be deleted
     */
    @Override
    public void removeDataEntry(DataEntry entry) {
        dbMgr.removeDataEntry(entry);
        fMgr.removeDataEntry(entry);
    }

    /**
     * updates gives dataentry to filemanager and databasemanager for work
     * 
     * @param entry value to be updated
     */
    @Override
    public void updateDataEntry(DataEntry entry) {
        dbMgr.updateDataEntry(entry);
        fMgr.updateDataEntry(entry);
    }

    /**
     * checks filemanager and databasemanager libraries and selects the more recent one
     * 
     * @return returns resolved library
     */
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
