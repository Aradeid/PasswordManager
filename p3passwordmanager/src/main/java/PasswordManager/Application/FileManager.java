package PasswordManager.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import PasswordManager.Generators.DataEntry;

public class FileManager implements GenericDataManager {
    final static String passLibraryPath = Settings.LibraryFilePath;
    final static String passLibraryBackupPath = Settings.BackupLibraryFilePath;

    private static List<DataEntry> passLibrary;

    /**
     * prepares file library for work
     * opens correct file and returns values
     */
    public void openLibrary() {
        try {
            FileInputStream fis = new FileInputStream(passLibraryPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            passLibrary = (ArrayList)ois.readObject();
            
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            createLibraryFile();
        } catch (ClassNotFoundException e) {
            restoreBackup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * restores library from file backup
     */
    public void restoreBackup() {
        try {
            //File passLibraryFile = new File(passLibraryPath);
            FileInputStream fis = new FileInputStream(passLibraryBackupPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            passLibrary = (ArrayList)ois.readObject();
            ois.close();
            fis.close();

            updateLibrary(); //called in case original file was damaged or lost
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * generates new library file
     */
    private void createLibraryFile() {
        try {
            File passLibraryFile = new File(passLibraryPath);
            passLibraryFile.createNewFile();

            File passLibraryBackupFile = new File(passLibraryBackupPath);
            if (passLibraryBackupFile.exists()) {
                restoreBackup();
                return;
            } else {
                passLibrary = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.print(e);
        }
    }

    private void createLibraryBackupFile() {
        try {
            File passLibraryBackupFile = new File(passLibraryBackupPath);
            passLibraryBackupFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.print(e);
        }
    }

    /**
     * Writes whole library object to file 
     */
    @Override
    public void updateLibrary() {
        try {
            FileOutputStream fos = new FileOutputStream(passLibraryPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(passLibrary);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Saves library object and creates a backup file
     */
    @Override
    public void closeLibrary() {
        updateLibrary();

        try {
            FileOutputStream fos = new FileOutputStream(passLibraryBackupPath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(passLibrary);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Returns library collection from file manager
     * 
     * @return library object
     */
    public List<DataEntry> getLibrary() {
        return passLibrary;
    }

    /**
     * Saves given object to the library, and then updates library file
     * 
     * @param entry value to be added
     */
    @Override
    public void addDataEntry(DataEntry entry) {
        passLibrary.add(entry);
        updateLibrary();
    }

    /**
     * Removes given object from library, then updates library file
     * 
     * @param entry value to be removed
     */
    @Override
    public void removeDataEntry(DataEntry entry) {
        passLibrary.remove(entry);
        updateLibrary();
    }

    /**
     * Updates a given data entry; as the entry is already part of the library, only the library file should be updated
     * 
     * @param entry object with new values
     */
    @Override
    public void updateDataEntry(DataEntry entry) {
        //no way to recognize entry
        //it is assumed that the 'entry' is already part of library
        updateLibrary();
    }
}
