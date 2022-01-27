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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import PasswordManager.Application.DataManager;
import PasswordManager.Application.Settings;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;
import PasswordManager.Generators.Comparators.EntryAddedComparator;
import PasswordManager.Generators.Comparators.EntryNameComparator;
import PasswordManager.Interface.Components.EntryTable;
import PasswordManager.Interface.Components.PMButton;
import PasswordManager.Interface.Components.PMLabel;
import PasswordManager.Interface.Components.PMPasswordField;
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

    EntryTable tableModel;
    PMButton addButton = new PMButton("Add password");
    PMButton filterClearButton = new PMButton("Clear");
    PMTextField filterField = new PMTextField();
    PMButton updateListButton = new PMButton("Update");
    JComboBox<SortOption> sortComboBox = new JComboBox<>(sortOptions);
    PMButton generatePasswordButton = new PMButton("Generate Password");
    PMTextField loginField = new PMTextField();
    PMPasswordField passwordField = new PMPasswordField();
    PMButton copyLoginButton = new PMButton("Copy");
    PMButton copyPassButton = new PMButton("Copy");
    
    public PassLibraryScreen() {
        dataManager = new DataManager();
        dataManager.openLibrary();
        passLibrary = dataManager.getLibrary();

        setLayout(new FlowLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Settings.WindowWidth, Settings.WindowHeight);

        addComponentsToContainer();
        addActionEvent();

        //backs up labriray on close
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dataManager.closeLibrary();
            }
        });
    }
    public void addComponentsToContainer() {
        tableModel = new EntryTable(dataManager, passLibrary);
        JTable paneTable = new JTable(tableModel);
        paneTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            /**
             * Launches an edit dialog when a selection is detected
             * solution from https://stackoverflow.com/questions/10128064/jtable-selected-row-click-event
             * 
             * @param event click or keyboard select event that was fired
             */
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && paneTable.getSelectedRow() != -1) {
                    showEntryEditDialog(tableModel.getValueAt(paneTable.getSelectedRow()));
                }
            }
        });
        JScrollPane sPane = new JScrollPane(paneTable);
        paneTable.setSize((int)(Settings.WindowWidth*0.9), Settings.WindowHeight);

        this.add(new PMLabel("Filter:"));
        this.add(filterField);
        this.add(updateListButton);
        this.add(filterClearButton);
        this.add(new PMLabel("Sort:"));
        this.add(sortComboBox);
        this.add(sPane);
        this.add(addButton);
    }
    public void addActionEvent() {
        addButton.addActionListener(this);
        filterClearButton.addActionListener(this);
        updateListButton.addActionListener(this);
        sortComboBox.addActionListener(this);
        generatePasswordButton.addActionListener(this);
        copyLoginButton.addActionListener(this);
        copyPassButton.addActionListener(this);
    }
    public void reloadTableContents() {
        reloadTableContents(filterField.getText(), getOrderType());
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
            List<DataEntry> values = 
                passLibrary
                    .stream()
                    .filter(entry -> 
                        entry.getName().toLowerCase().contains(filterValue.toLowerCase()) 
                        || entry.getLogin().toLowerCase().contains(filterValue.toLowerCase()))
                    .toList();
            setTableValues(values);
            return;
        }
        setTableValues(passLibrary);
    }
    public void setTableValues(List<DataEntry> values) {
        tableModel.setLibrary(values);
        tableModel.fireTableDataChanged();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            showAddDialog();
        }
        if (e.getSource() == generatePasswordButton) {
            passwordField.setText(PasswordEntry.getNewEntry().getPassword());
        }
        if (e.getSource() == filterClearButton) {
            filterField.setText("");
            reloadTableContents("", getOrderType());
        }
        if (e.getSource() == updateListButton || e.getSource() == sortComboBox) {
            reloadTableContents();
        }
        if (e.getSource() == copyLoginButton) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(loginField.getText()), null);
        }
        if (e.getSource() == copyPassButton) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(new String (passwordField.getPassword())), null);
        }
    }
    public void showAddDialog() {
        JDialog d = new JDialog(this, "Add New Entry");
        JPanel panel = new JPanel(new GridLayout(0, 1));//TODO test with 2,1
        panel.add(new PMLabel("Entry name"));
        PMTextField nameField = new PMTextField();
        panel.add(nameField);
        panel.add(new PMLabel("Login"));
        loginField.setText("");
        panel.add(loginField);
        panel.add(new PMLabel("Password"));
        panel.add(passwordField);

        panel.add(generatePasswordButton);
        int result = JOptionPane.showConfirmDialog(this, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            dataManager.addDataEntry(new DataEntry(nameField.getText(), loginField.getText(), new String(passwordField.getPassword())));
            reloadTableContents();
        }
    }
    public void showEntryEditDialog(DataEntry entry) {
        Object[] options = { "Done", "Cancel", "Delete" };
        JPanel panel = new JPanel(new GridLayout(0, 1));
        PMLabel nameLabel = new PMLabel("Name:");
        PMTextField nameField = new PMTextField();
        nameField.setText(entry.getName());
        PMLabel loginLabel = new PMLabel("Login:");
        loginField.setText(entry.getLogin());
        PMLabel passwordLabel = new PMLabel("Password:");
        passwordField.setText(entry.getPasswordValue());
        PMButton showPassButton = new PMButton("Show");
        showPassButton.addActionListener(new ActionListener() {
            boolean visible = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (visible) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            } 
        });
        
        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        panel.add(namePanel);

        JPanel loginPanel = new JPanel();
        loginPanel.add(loginLabel);
        loginPanel.add(loginField);
        loginPanel.add(copyLoginButton);
        panel.add(loginPanel);

        JPanel passPanel = new JPanel();
        passPanel.add(passwordLabel);
        passPanel.add(passwordField);
        passPanel.add(copyPassButton);
        panel.add(passPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.add(showPassButton);
        controlPanel.add(generatePasswordButton);
        panel.add(controlPanel);

        int result = JOptionPane.showOptionDialog(this, panel, "Test",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.OK_OPTION) {
            entry.setName(nameField.getText());
            entry.setLogin(loginField.getText());
            entry.setPassword(new String(passwordField.getPassword()));
            entry.updateTimeUpdated();
            dataManager.updateDataEntry(entry);
            reloadTableContents();
            
        } else if (result == 2) {
            dataManager.removeDataEntry(entry);
            reloadTableContents();
        }
    }
    public OrderType getOrderType() {
        return ((SortOption)sortComboBox.getSelectedItem()).getType();
    }
}
