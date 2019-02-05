package connectors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents connecting application to database
 */
public class Database{

	public static Connection connection;

	/**
	 * Connecting to database by stationary data like:
	 * driver, user, password and absolute URL address to database
	 */
	public static void connectToDatabase(){

		final String driverName = "org.postgresql.Driver";
		final int lport = 5432;
		final String url = "jdbc:postgresql://localhost:"+lport+"/DB_USER";
		final String databaseUserName = "DB_USERNAME";
		final String databasePassword = "DB_PASSWORD";

		try{
			Class.forName(driverName).newInstance();
			connection = DriverManager.getConnection (url, databaseUserName, databasePassword);
			System.out.println("Database connection established");
		} catch (Exception ex){
			ex.printStackTrace();
		}

	}

	/**
	 * Closing connection to database
	 */
	public static void close(){
		try {
			if (connection != null && !connection.isClosed()) {
				System.out.println("Closing Database Connection");
				Database.connection.close();
			} else {
				System.out.println("PROBLEM with -- Closing Database Connection");
				System.out.println("Probably connection == null or connection is closed");
			}
		} catch (SQLException ex){
			ex.printStackTrace();
		}
		
	}

	/**
	 * setting off AutoCommit mod
	 */
	public static void setAutoCommitTrue(){
		try{
			Database.connection.setAutoCommit(true);
		} catch (SQLException ex){
			ex.printStackTrace();
		}

	}


}