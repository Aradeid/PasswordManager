package PasswordManager.Application;

import java.util.List;

import PasswordManager.Generators.DataEntry;

public interface GenericDataManager {
    //prepares library for usage; fills up the password lsit object
    public void openLibrary();
    //synchronises the local object with the external library
    public void updateLibrary();
    //disconnects external library; may require backups
    public void closeLibrary();
    //adds new data entry to library
    public void addDataEntry(DataEntry entry);
    //removes existing data entry from library
    public void removeDataEntry(DataEntry entry);
    //updates existing data entry in library
    public void updateDataEntry(DataEntry entry);
    //returns saved data entry
    public List<DataEntry> getLibrary();
}
