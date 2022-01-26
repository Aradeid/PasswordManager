package PasswordManager.Database.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import PasswordManager.Database.DBUtil;
import PasswordManager.Generators.PasswordEntry;

public class PasswordEntryDao extends BaseDao<PasswordEntry> {
    /**
     * Default constructor. Defines the table name.
     */
    public PasswordEntryDao() {
        super("pm_password");
    }

    /**
     * Adds a given passwordEntry to the database
     * 
     * @param entry value to be added
     * @return index of the newly added entry. -1 on fail
     */
    @Override
    public int add(PasswordEntry entry) {
        int result = -1;
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO ? (passvalue, canContainUppercase, canCotainDigits, canCotainSpecials, minCount, maxLength) VALUES (?,?,?,?,?,?)");

            ps.setString(1, getTableName());
			ps.setString(2, entry.getPassword());
			ps.setBoolean(3, entry.getCanUseUpperCase());
            ps.setBoolean(4, entry.getCanUseDigits());
			ps.setBoolean(5, entry.getCanUseSpecialSymbols());
            ps.setInt(6, entry.getMinimumRequirementsPerSymbolType());
            ps.setInt(7, entry.getMaximumPasswordLength());
			
			ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
                entry.setId(result);
            }
            rs.close();
            ps.close();
			conn.commit();
            return result;
		} catch (SQLException e) {
			DBUtil.dispaySQLExceptions(e);
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Removes a given passwordEntry from the database
     * 
     * @param entry value to be deleted
     */
    @Override
    public void remove(PasswordEntry entry) {
        remove(entry.getId());
    }

    /**
     * Removes a given passwordEntry from the database by its id
     * 
     * @param id of the deleted entry
     */
    @Override
    public void remove(int id) {
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM ? WHERE passid = ?");
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
     * Updates a given passwordEntry in the database
     * 
     * @param entry object to be updated
     */
    @Override
    public void update(PasswordEntry entry) {
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "UPDATE ? SET passvalue = ?, canContainUppercase = ?, canCotainDigits = ?, canCotainSpecials = ?, minCount = ?, maxLength = ? WHERE passid = ?");
            
            ps.setString(1, getTableName());
			ps.setString(2, entry.getPassword());
			ps.setBoolean(3, entry.getCanUseUpperCase());
            ps.setBoolean(4, entry.getCanUseDigits());
			ps.setBoolean(5, entry.getCanUseSpecialSymbols());
            ps.setInt(6, entry.getMinimumRequirementsPerSymbolType());
            ps.setInt(7, entry.getMaximumPasswordLength());
            ps.setInt(8, entry.getId());			
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
    public List<PasswordEntry> getAll() {
        List<PasswordEntry> output = new ArrayList<>();
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "SELECT passid, canCotainDigits, canContainUppercase, canCotainSpecials, minCount, maxLength, passValue FROM ?");
            ps.setString(1, getTableName());
			ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                output.add(
                    new PasswordEntry(
                        rs.getInt(1),
                        rs.getBoolean(2),
                        rs.getBoolean(3),
                        true,
                        rs.getBoolean(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7)
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
    public PasswordEntry get(int id) {
        PasswordEntry output = null;
        try(Connection conn = DBUtil.getConnection()){
			PreparedStatement ps = conn.prepareStatement(
                "SELECT passid, canCotainDigits, canContainUppercase, canCotainSpecials, minCount, maxLength, passValue FROM ? WHERE passid = ?");
            ps.setString(1, getTableName());
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                output = new PasswordEntry(
                    rs.getInt(1),
                    rs.getBoolean(2),
                    rs.getBoolean(3),
                    true,
                    rs.getBoolean(4),
                    rs.getInt(5),
                    rs.getInt(6),
                    rs.getString(7)
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
