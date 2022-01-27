package PasswordManager.Interface.Components;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import PasswordManager.Generators.DataEntry;

//a table model just for DataEntry objects
public class EntryTable extends AbstractTableModel {
    List<DataEntry> library;
    Vector<String> header = DataEntry.getKeys();

    /**
     * Constructor. Generates a table with a given collection of values
     * 
     * @param library
     */
    public EntryTable(List<DataEntry> library) {
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

    /**
     * The reason why the table has headers. Returns column names on demand
     */
    @Override
    public String getColumnName(int columnIndex) {
        return header.get(columnIndex);
    }

    /**
     * The reason why table has cells. Returns a value for a given coordinate
     */
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

    /**
     * An index getter but for rows. Returns full fledged objects.
     * 
     * @param rowIndex index of row, and therefore entry to be returned
     * @return a full fledged entry object
     */
    public DataEntry getValueAt(int rowIndex) {
        return library.get(rowIndex);
    }

    /**
     * Dynamically sets library of values. This way content can be sorted and filtered
     * 
     * @param library collection of new values
     */
    public void setLibrary(List<DataEntry> library) {
        this.library = library;
    }
    
}
