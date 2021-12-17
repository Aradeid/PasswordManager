package PasswordManager;

import java.util.List;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.FileManager;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;
import PasswordManager.Interface.PrimaryWindow;

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
        PrimaryWindow app = new PrimaryWindow();
        app.setVisible(true);
        app.setLocationRelativeTo(null);
    }

    public static void passOutputVerification() {
        DataEntry entry = new DataEntry("Zucc's wet dream", "mihailv", new PasswordEntry("internet"));
        System.out.println(entry.getPassword().toString());
    }

    public static void fileSystemTest() {
        DataManager dMgr = new DataManager();
        dMgr.openLibrary();
        List<DataEntry> passLibrary = dMgr.getLibrary();
        //passLibrary.add(new DataEntry("Jojo fan wiki", "JosefJoestar", PasswordEntry.getNewEntry()));
        //passLibrary.add(new DataEntry("Zucc's wet dream", "Aradeid", new PasswordEntry("internet")));

        dMgr.addDataEntry(new DataEntry("Eternity OS", "Lambda", PasswordEntry.getNewEntry()));
        dMgr.addDataEntry(new DataEntry("Google.com", "I am da CEO of guugl", PasswordEntry.getNewEntry()));

        dMgr.updateLibrary();

        passLibrary.forEach(entry -> System.out.println(entry));
        dMgr.closeLibrary();
    }
}
