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

public class FileManager {
    final static String passLibraryPath = "passfileset.pmg";
    final static String passLibraryBackupPath = "passfileset.pmg.bak";

    private static List<DataEntry> passLibrary;

    public static void openLibrary() {
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

    public static void restoreBackup() {
        try {
            //File passLibraryFile = new File(passLibraryPath);
            FileInputStream fis = new FileInputStream(passLibraryBackupPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            passLibrary = (ArrayList)ois.readObject();
            ois.close();
            fis.close();

            saveLibrary(); //called in case original file was damaged or lost
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLibraryFile() {
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

    private static void createLibraryBackupFile() {
        try {
            File passLibraryBackupFile = new File(passLibraryBackupPath);
            passLibraryBackupFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.print(e);
        }
    }

    public static void saveLibrary() {
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

    public static void closeLibrary() {
        saveLibrary();

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

    public static List<DataEntry> getLibrary() {
        return passLibrary;
    }
}
