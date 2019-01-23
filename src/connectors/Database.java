package connectors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Database{

	public static Connection connection;

	public static void connectToDatabase(){

		final String driverName = "org.postgresql.Driver";
		final int lport = 5432;
		final String url = "jdbc:postgresql://localhost:"+lport+"/u5wardega";
		final String databaseUserName = "u5wardega";
		final String databasePassword = "5wardega";

		try{
			Class.forName(driverName).newInstance();
			connection = DriverManager.getConnection (url, databaseUserName, databasePassword);
			System.out.println("Database connection established");
		} catch (Exception ex){
			ex.printStackTrace();
		}

	}


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




}