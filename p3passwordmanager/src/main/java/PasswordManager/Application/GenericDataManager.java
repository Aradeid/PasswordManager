package PasswordManager.Application;

import java.util.List;

import PasswordManager.Generators.DataEntry;

public interface GenericDataManager {
    public void openLibrary();
    public void updateLibrary();
    public void closeLibrary();
    public void addDataEntry(DataEntry entry);
    public void removeDataEntry(DataEntry entry);
    public void updateDataEntry(DataEntry entry);
    public List<DataEntry> getLibrary();
}
