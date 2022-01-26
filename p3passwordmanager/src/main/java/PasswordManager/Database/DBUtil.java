package PasswordManager.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import PasswordManager.Application.Settings;

//Mostly based off of the example made during class
public class DBUtil {
    static String url = Settings.DatabaseUrl;
	//static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String password = "";
	static String user = Settings.DatabaseUser;
	
	public static  void dispaySQLExceptions(SQLException ex) {
		while (ex != null) {
			System.out.println("SQL State:" + ex.getSQLState());
			System.out.println("Error Code:" + ex.getErrorCode());
			System.out.println("Message:" + ex.getMessage());
			Throwable t = ex.getCause();
			while (t != null) {
				System.out.println("Cause:" + t);
				t = t.getCause();
			}
			ex = ex.getNextException();
		}
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driver);
		return  DriverManager.getConnection(url, user, password);
	}

}