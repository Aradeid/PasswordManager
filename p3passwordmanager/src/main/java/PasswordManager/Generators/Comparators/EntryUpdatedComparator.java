package PasswordManager.Generators.Comparators;

import java.util.Comparator;

import PasswordManager.Generators.DataEntry;

public class EntryUpdatedComparator implements Comparator<DataEntry> {
    /**
     * Compares 2 values of DataEntry by date updated. Order is inverted, so newer entries come first
     * 
     * @param o1 first entry
     * @param o2 second entry
     * @return -1 if first is newer, 0 if equal, 1 if older
     */
    @Override
    public int compare(DataEntry o1, DataEntry o2) {
        return 0 - o1.getTimeUpdated().compareTo(o2.getTimeUpdated());
    }
}
