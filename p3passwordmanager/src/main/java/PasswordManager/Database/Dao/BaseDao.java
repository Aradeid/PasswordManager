package PasswordManager.Database.Dao;

import java.util.List;

public abstract class BaseDao<T> {
    private String tableName;
    /**
     * Default constructor. Saves the table name.
     * 
     * @param name of table in database
     */
    public BaseDao(String name) {
        tableName = name;
    }

    /**
     * Adds a given value to database
     * 
     * @param t value to be added
     * @return index in database
     */
    public abstract int add(T t);

    /**
     * Updates a given value in database
     * 
     * @param t value to be updated
     */
    public abstract void update(T t);
    
    /**
     * Deletes a value from database
     * 
     * @param t value to be deleted
     */
    public abstract void remove(T t);

    /**
     * Deletes a value by its index from database
     * 
     * @param id database index
     */
    public abstract void remove(int id);

    /**
     * Returns all entries in database
     * 
     * @return collection of all entries
     */
    public abstract List<T> getAll();
    public abstract T get(int id);
    public String getTableName() {
        return tableName;
    }
}
