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
    final static String passLibraryPath = "passfileset.pmg"; //TODO move to settings
    final static String passLibraryBackupPath = "passfileset.pmg.bak"; //TODO move to settings

    private static List<DataEntry> passLibrary;

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

    public List<DataEntry> getLibrary() {
        return passLibrary;
    }

    @Override
    public void addDataEntry(DataEntry entry) {
        passLibrary.add(entry);
        updateLibrary();
    }

    @Override
    public void removeDataEntry(DataEntry entry) {
        passLibrary.remove(entry);
        updateLibrary();
    }

    @Override
    public void updateDataEntry(DataEntry entry) {
        //no way to recognize entry
        //it is assumed that the 'entry' is already part of library
        updateLibrary();
    }
}
