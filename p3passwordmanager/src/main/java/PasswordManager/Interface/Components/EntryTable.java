package PasswordManager.Interface.Components;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import PasswordManager.Application.DataManager;
import PasswordManager.Generators.DataEntry;

public class EntryTable extends AbstractTableModel {
    DataManager manager;
    List<DataEntry> library;
    Vector<String> header = DataEntry.getKeys();
    public EntryTable(DataManager manager, List<DataEntry> library) {
        this.manager = manager;
        this.library = library;
        
    }
    @Override
    public int getRowCount() {
        return library.size();
    }

    @Override
    public int getColumnCount() {
        return header.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return header.get(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "??";
        DataEntry entry = library.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = entry.getName();
                break;
            case 1:
                value = entry.getLogin();
                break;
            case 2:
                value = entry.getPassword().getCensored();
                break;
            case 3:
                value = (new Date(entry.getTimeUpdated().getTime())).toString();
                break;
        }
        return value;
    }

    public DataEntry getValueAt(int rowIndex) {
        return library.get(rowIndex);
    }

    public void setLibrary(List<DataEntry> library) {
        this.library = library;
    }
    
}
