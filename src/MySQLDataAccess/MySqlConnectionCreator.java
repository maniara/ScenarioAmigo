package MySQLDataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySqlConnectionCreator {
	
	private static Connection conn;
	
	public static Connection getConnection()
	{
		if(conn == null)
		{
			try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost","root","root");
			}
		catch(SQLException ex)
		{}
		}
		
		return conn;
	}

}
