package PasswordManager.Interface;

import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.FileManager;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;

public class PasswordListPane extends JScrollPane {
    private JTable paneTable;
    private DefaultTableModel paneTableModel;
    private DataManager dataManager;
    private List<DataEntry> passLibrary;

    public PasswordListPane() {
        super();
        dataManager = new DataManager();
        passLibrary = dataManager.getLibrary();
        paneTableModel = new DefaultTableModel();
        this.loadTableValues();
        paneTable = new JTable(paneTableModel);

        this.add(paneTable);
        //this.add(new JLabel("Santa isn't real"));
    }

    public void loadTableValues() {
        DataEntry.getKeys().forEach(key ->
            paneTableModel.addColumn(key)
        );
        
        passLibrary.forEach(entry ->
           paneTableModel.addRow(entry.toArray())
        );

    }
}
