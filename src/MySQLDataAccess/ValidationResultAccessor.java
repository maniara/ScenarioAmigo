package MySQLDataAccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ValidationResultAccessor {
	
private Connection conn;
	
	private Connection getConnection() throws SQLException
	{
		if(conn == null)
			conn = MySqlConnectionCreator.getConnection();
		else if(conn.isClosed())
			conn = MySqlConnectionCreator.getConnection();
		
		return conn;
	}
	
	public ValidationResultAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addResult(String valResult)
	{
		String[] resArray = valResult.split(";");
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".`validationresult` (`try`, `ucid`"
				+ ", `originScenario`, `missedAction`,`missedSeq`,`inputScenario`,`extRoute`,`result`,`extractedNum`,`found`,`success`) "
				+ "VALUES ('%s', '%s', '%s', '%s','%s','%s','%s','%s','%s','%s','%s')",
				resArray[0],resArray[1],resArray[2],resArray[3],resArray[4],resArray[5],resArray[6],resArray[7],resArray[8],resArray[9],resArray[10]);

		//System.out.println(query);
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
			ex.printStackTrace();
		}
	}
	
	public void deleteAllResult()
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`validationresult`");

		//System.out.println(query);
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
			ex.printStackTrace();
		}
	}

}
