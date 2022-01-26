package PasswordManager.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.Settings;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;
import PasswordManager.Interface.Components.PMButton;
import PasswordManager.Interface.Components.PMLabel;
import PasswordManager.Interface.Components.PMTextField;

public class PassLibraryScreen extends JFrame {
    private DataManager dataManager;
    private List<DataEntry> passLibrary;

    DefaultTableModel paneTableModel;
    
    public PassLibraryScreen() {
        dataManager = new DataManager();
        dataManager.openLibrary();
        passLibrary = dataManager.getLibrary();

        setLayout(new FlowLayout());
        //this.setVisible(true);
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Settings.WindowWidth, Settings.WindowHeight);

        //PasswordListPane plp = new PasswordListPane();
        paneTableModel = new DefaultTableModel();
        DataEntry.getKeys().forEach(key ->
            paneTableModel.addColumn(key)
        );
        
        passLibrary.forEach(entry ->
           paneTableModel.addRow(entry.toArray())
        );
        JTable paneTable = new JTable(paneTableModel);
        //paneTable.setPreferredSize(new Dimension((int)(Settings.WindowWidth*0.7), Settings.WindowHeight));
        paneTable.setSize((int)(Settings.WindowWidth*0.7), Settings.WindowHeight);

        this.add(paneTable);
        //this.add(new JLabel("Santa isn't real"));
        //plp.loadTableValues();
        //this.add(plp);

        JFrame currentFrame = this;
        PMButton addButton = new PMButton("Add password");
        this.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(currentFrame, "Add New Entry");
                JPanel panel = new JPanel(new GridLayout(0, 1));//TODO test with 2,1
                panel.add(new PMLabel("Entry name"));
                PMTextField nameField = new PMTextField();
                panel.add(nameField);
                panel.add(new PMLabel("Login"));
                PMTextField loginField = new PMTextField();
                panel.add(loginField);
                panel.add(new PMLabel("Password"));
                PMTextField passwordField = new PMTextField();
                panel.add(passwordField);

                PMButton submitButton = new PMButton("Submit");
                PMButton cancelButton = new PMButton("Cancel");
                PMButton generatePasswordButton = new PMButton("Generate Password");
                generatePasswordButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        passwordField.setText(PasswordEntry.getNewEntry().getPassword());
                    }
                });

                panel.add(generatePasswordButton);
                //panel.add(submitButton);
                //panel.add(cancelButton);
                int result = JOptionPane.showConfirmDialog(null, panel, "Test",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    dataManager.addDataEntry(new DataEntry(nameField.getText(), loginField.getText(), passwordField.getText()));
                    reloadTableContents();
                } else {
                    System.out.println("Cancelled");
                }
            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dataManager.closeLibrary();
            }
        });
    }
    public void reloadTableContents() {
        paneTableModel.setRowCount(0);
        for(DataEntry de : passLibrary)
            paneTableModel.addRow(de.toArray());
    }
}
