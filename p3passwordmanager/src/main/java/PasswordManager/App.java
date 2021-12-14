package PasswordManager;

import java.util.List;

import PasswordManager.Application.FileManager;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //fileSystemTest();
        //passOutputVerification();
    }

    public static void passOutputVerification() {
        DataEntry entry = new DataEntry("Zucc's wet dream", "Aradeid", new PasswordEntry("internet"));
        System.out.println(entry.getPassword().toString());
    }

    public static void fileSystemTest() {
        FileManager.openLibrary();
        List<DataEntry> passLibrary = FileManager.getLibrary();
        //passLibrary.add(new DataEntry("Jojo fan wiki", "JosefJoestar", PasswordEntry.getNewEntry()));
        //passLibrary.add(new DataEntry("Zucc's wet dream", "Aradeid", new PasswordEntry("internet")));

        passLibrary.add(new DataEntry("From zero", "to hero", PasswordEntry.getNewEntry()));
        passLibrary.add(new DataEntry("example.com", "Wikiman", PasswordEntry.getNewEntry()));

        FileManager.saveLibrary();

        passLibrary.forEach(entry -> System.out.println(entry));
        FileManager.closeLibrary();
    }
}
