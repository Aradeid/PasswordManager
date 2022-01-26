package PasswordManager.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.Settings;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;
import PasswordManager.Generators.Comparators.EntryAddedComparator;
import PasswordManager.Generators.Comparators.EntryNameComparator;
import PasswordManager.Interface.Components.PMButton;
import PasswordManager.Interface.Components.PMLabel;
import PasswordManager.Interface.Components.PMTextField;

public class PassLibraryScreen extends JFrame implements ActionListener {
    enum OrderType {
        ByName,
        ByAdd,
        ByUpdate
    }
    private class SortOption {
        public OrderType type;
        public String name;
        public SortOption(OrderType type, String name) {
            this.type = type;
            this.name = name;
        }
        public OrderType getType() {
            return type;
        }
        @Override
        public String toString() {
            return name;
        }
    }
    private DataManager dataManager;
    private List<DataEntry> passLibrary;
    SortOption[] sortOptions = {
        new SortOption(OrderType.ByAdd, "By Date Added"),
        new SortOption(OrderType.ByUpdate, "By Date Updated"),
        new SortOption(OrderType.ByName, "By Name")
    };

    DefaultTableModel paneTableModel = new DefaultTableModel();
    PMButton addButton = new PMButton("Add password");
    PMButton filterClearButton = new PMButton("Clear");
    PMTextField filterField = new PMTextField();
    PMButton updateListButton = new PMButton("Update");
    JComboBox<SortOption> sortComboBox = new JComboBox<>(sortOptions);
    PMButton generatePasswordButton;
    PMTextField newPasswordField;
    
    public PassLibraryScreen() {
        dataManager = new DataManager();
        dataManager.openLibrary();
        passLibrary = dataManager.getLibrary();

        setLayout(new FlowLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Settings.WindowWidth, Settings.WindowHeight);

        DataEntry.getKeys().forEach(key ->
            paneTableModel.addColumn(key)
        );

        addComponentsToContainer();
        addActionEvent();
        
        // passLibrary.forEach(entry ->
        //    paneTableModel.addRow(entry.toArray())
        // );
        

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dataManager.closeLibrary();
            }
        });
    }
    public void addComponentsToContainer() {
        reloadTableContents();
        JTable paneTable = new JTable(paneTableModel);
        paneTable.setSize((int)(Settings.WindowWidth*0.7), Settings.WindowHeight);

        this.add(new PMLabel("Filter:"));
        this.add(filterField);
        this.add(updateListButton);
        this.add(filterClearButton);
        this.add(new PMLabel("Sort:"));
        this.add(sortComboBox);
        this.add(paneTable);
        this.add(addButton);
    }
    public void addActionEvent() {
        addButton.addActionListener(this);
        filterClearButton.addActionListener(this);
        updateListButton.addActionListener(this);
        sortComboBox.addActionListener(this);
    }
    public void reloadTableContents() {
        reloadTableContents("", OrderType.ByAdd);
    }
    public void reloadTableContents(String filterValue, OrderType type) {
        if (type == OrderType.ByAdd) {
            passLibrary.sort(new EntryAddedComparator());
        } else if (type == OrderType.ByUpdate) {
            passLibrary.sort(new EntryAddedComparator());
        } else {
            passLibrary.sort(new EntryNameComparator());
        }
        if (filterValue.length() > 0) {
            List<DataEntry> values = passLibrary.stream().filter(entry -> entry.getName().contains(filterValue) || entry.getLogin().contains(filterValue)).toList();
            values.forEach(v -> System.out.println(v));
            setTableValues(values);
            return;
        }
        setTableValues(passLibrary);
    }
    public void setTableValues(List<DataEntry> values) {
        paneTableModel.setRowCount(0);
        for(DataEntry de : values) {
            paneTableModel.addRow(de.toArray());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("a click did happen at least");
        if (e.getSource() == addButton) {
            showAddDialog();
        }
        if (e.getSource() == generatePasswordButton) {
            newPasswordField.setText(PasswordEntry.getNewEntry().getPassword());
        }
        if (e.getSource() == filterClearButton) {
            filterField.setText("");
            reloadTableContents("", getOrderType());
        }
        if (e.getSource() == updateListButton || e.getSource() == sortComboBox) {
            System.out.println("Clicked");
            reloadTableContents(filterField.getText(), getOrderType());
        }
    }
    public void showAddDialog() {
        JDialog d = new JDialog(this, "Add New Entry");
        JPanel panel = new JPanel(new GridLayout(0, 1));//TODO test with 2,1
        panel.add(new PMLabel("Entry name"));
        PMTextField nameField = new PMTextField();
        panel.add(nameField);
        panel.add(new PMLabel("Login"));
        PMTextField loginField = new PMTextField();
        panel.add(loginField);
        panel.add(new PMLabel("Password"));
        newPasswordField = new PMTextField();
        panel.add(newPasswordField);

        generatePasswordButton = new PMButton("Generate Password");
        generatePasswordButton.addActionListener(this);

        panel.add(generatePasswordButton);
        int result = JOptionPane.showConfirmDialog(null, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            dataManager.addDataEntry(new DataEntry(nameField.getText(), loginField.getText(), newPasswordField.getText()));
            reloadTableContents();
        }
    }
    public OrderType getOrderType() {
        return ((SortOption)sortComboBox.getSelectedItem()).getType();
    }
}
