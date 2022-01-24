package PasswordManager.Database.Dao;

import java.util.List;

public abstract class BaseDao<T> {
    private String tableName;
    public BaseDao(String name) {
        tableName = name;
    }
    public abstract int add(T t);
    public abstract void update(T t);
    public abstract void remove(T t);
    public abstract void remove(int id);
    public abstract List<T> getAll();
    public abstract T get(int id);
    public String getTableName() {
        return tableName;
    }
}
