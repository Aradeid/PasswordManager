package PasswordManager.Database.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PasswordManager.Database.DBUtil;
import PasswordManager.Generators.DataEntry;
import PasswordManager.Generators.PasswordEntry;

public class DataEntryDao extends BaseDao<DataEntry> {
    /**
     * Default constructor. Defines the table name.
     */
    public DataEntryDao() {
        super("pm_entry");
    }

    /**
     * Adds a given dataEntry to the database, as well as its contained passwordEntry
     * 
     * @param entry value to be added
     * @return index of the newly added entry. -1 on fail
     */
    @Override
    public int add(DataEntry entry) {
        int result = -1;
        try(Connection conn = DBUtil.getConnection()){
            PasswordEntryDao ped = new PasswordEntryDao();
            int passwordId = ped.add(entry.getPassword());
            if (passwordId == -1) {
                return result; //initial insert failed
            }
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ? (website, login, timeadded, timeupdated, passid, userid) VALUES (?,?,?,?,?,?)");
            //getGeneratedKeys
            ps.setString(1, getTableName());
			ps.setString(2, entry.getName());
			ps.setString(3, entry.getLogin());
			ps.setTimestamp(4, entry.getTimeAdded());
			ps.setTimestamp(5, entry.getTimeUpdated());
			ps.setInt(6, passwordId);
            ps.setInt(7, 1); //TODO replace with current user
			
			ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                entry.setId(result);
            }
            rs.close();

			conn.commit();
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Removes a given dataEntry from the database
     * 
     * @param entry value to be deleted
     */
    @Override
    public void remove(DataEntry entry) {
        remove(entry.getId());
    }

    /**
     * Removes a given dataEntry from the database by its id. The corresponding password is deleted by cascade
     * 
     * @param id of the deleted entry
     */
    @Override
    public void remove(int id) {
        try(Connection conn = DBUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM ? WHERE entryid = ?");
            ps.setString(1, getTableName());
			ps.setInt(2, id);
			ps.executeUpdate();

            ps.close();
			conn.commit();
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a given dataEntry in the database
     * 
     * @param entry object to be updated
     */
    @Override
    public void update(DataEntry entry) {
        try(Connection conn = DBUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(
                "UPDATE ? SET website = ?, login = ?, timeadded = ?, timeupdated = ? WHERE entryid = ?");
            
            ps.setString(1, getTableName());
			ps.setString(2, entry.getName());
			ps.setString(3, entry.getLogin());
            ps.setTimestamp(4, entry.getTimeAdded());
			ps.setTimestamp(5, entry.getTimeUpdated());
            ps.setInt(6, entry.getId());			
			ps.executeUpdate();

            ps.close();
			conn.commit();
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all entries in the database
     * 
     * @return collection of all passwordEntry objects in database
     */
    @Override
    public List<DataEntry> getAll() {
        List<DataEntry> output = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "SELECT entryid, website, login, timeadded, timeupdated, passid, canCotainDigits, canContainUppercase, canCotainSpecials, minCount, maxLength, passValue FROM ? JOIN pm_password USING passid WHERE userid = ?");
            ps.setString(1, getTableName());
            ps.setInt(2, 1);//TODO replace with current user
			ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                output.add(
                    new DataEntry(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        new PasswordEntry(
                            rs.getInt(6),
                            rs.getBoolean(7),
                            rs.getBoolean(8),
                            true,
                            rs.getBoolean(9),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getString(12)
                        ),
                        rs.getTimestamp(4),
                        rs.getTimestamp(5)
                    )
                );
            }

            rs.close();
            ps.close();
			conn.commit();
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Returns all entries in the database
     * 
     * @param id of entry to be found
     * @return passwordEntry object with given id, or null
     */
    @Override
    public DataEntry get(int id) {
        DataEntry output = null;
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "SELECT entryid, website, login, timeadded, timeupdated, passid, canCotainDigits, canContainUppercase, canCotainSpecials, minCount, maxLength, passValue FROM ? JOIN pm_password USING passid WHERE passid = ? AND userid = ?");
            ps.setString(1, getTableName());
			ps.setInt(2, id);
            ps.setInt(3, 1);//TODO set current user
			ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                output = new DataEntry(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    new PasswordEntry(
                        rs.getInt(6),
                        rs.getBoolean(7),
                        rs.getBoolean(8),
                        true,
                        rs.getBoolean(9),
                        rs.getInt(10),
                        rs.getInt(11),
                        rs.getString(12)
                    ),
                    rs.getTimestamp(4),
                    rs.getTimestamp(5)
                );
            }

            rs.close();
            ps.close();
			conn.commit();
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }
    
}
