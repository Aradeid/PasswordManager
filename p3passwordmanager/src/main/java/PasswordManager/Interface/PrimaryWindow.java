package PasswordManager.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.Settings;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;

public class PrimaryWindow extends JFrame {
    //final int PRIMARY_WINDOW_WIDTH = 1000;
    //final int PRIMARY_WINDOW_HEIGHT = 600;
    private int PRIMARY_WINDOW_WIDTH;
    private int PRIMARY_WINDOW_HEIGHT;
    private DataManager dataManager;
    private List<DataEntry> passLibrary;
    public static Settings settings = new Settings();

    DefaultTableModel paneTableModel;

    public PrimaryWindow() {
        super("Password Manager");
        PRIMARY_WINDOW_WIDTH = settings.getWindowWidth();
        PRIMARY_WINDOW_HEIGHT = settings.getWindowHeight();

        dataManager = new DataManager();
        passLibrary = dataManager.getLibrary();

        setLayout(new FlowLayout());
        //this.setVisible(true);
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(PRIMARY_WINDOW_WIDTH, PRIMARY_WINDOW_HEIGHT);

        //PasswordListPane plp = new PasswordListPane();
        paneTableModel = new DefaultTableModel();
        DataEntry.getKeys().forEach(key ->
            paneTableModel.addColumn(key)
        );
        
        passLibrary.forEach(entry ->
           paneTableModel.addRow(entry.toArray())
        );
        JTable paneTable = new JTable(paneTableModel);
        paneTable.setPreferredSize(new Dimension((int)(PRIMARY_WINDOW_WIDTH*0.7), PRIMARY_WINDOW_HEIGHT));

        this.add(paneTable);
        //this.add(new JLabel("Santa isn't real"));
        //plp.loadTableValues();
        //this.add(plp);

        JFrame currentFrame = this;
        JButton addButton = new JButton("Add password");
        this.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(currentFrame, "Add New Entry");
                JPanel panel = new JPanel(new GridLayout(0, 1));//TODO test with 2,1
                panel.add(new JLabel("Entry name"));
                JTextField nameField = new JTextField();
                panel.add(nameField);
                panel.add(new JLabel("Login"));
                JTextField loginField = new JTextField();
                panel.add(loginField);
                panel.add(new JLabel("Password"));
                JTextField passwordField = new JTextField();
                panel.add(passwordField);

                JButton submitButton = new JButton("Submit");
                JButton cancelButton = new JButton("Cancel");
                JButton generatePasswordButton = new JButton("Generate Password");
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
