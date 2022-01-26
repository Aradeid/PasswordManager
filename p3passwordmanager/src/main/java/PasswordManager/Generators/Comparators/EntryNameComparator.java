package PasswordManager.Generators.Comparators;

import java.util.Comparator;

import PasswordManager.Generators.DataEntry;

public class EntryNameComparator implements Comparator<DataEntry> {
    /**
     * Compares 2 values of DataEntry by name alphabetically
     * 
     * @param o1 first entry
     * @param o2 second entry
     * @return -1 if first is earlier, 0 if same, 1 if later
     */
    @Override
    public int compare(DataEntry o1, DataEntry o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
