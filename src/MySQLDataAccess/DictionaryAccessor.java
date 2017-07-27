package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.VerbCluster;

public class DictionaryAccessor {
	
private Connection conn;
	
	private Connection getConnection() throws SQLException
	{
		if(conn == null)
			conn = MySqlConnectionCreator.getConnection();
		else if(conn.isClosed())
			conn = MySqlConnectionCreator.getConnection();
		
		return conn;
	}
	
	public DictionaryAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addDictionaryItem(String representives, String verbs, String subjectType)
	{
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".`synonymdictionary` (`representives`, `verbs`, `iscustom`,`subjectType`) VALUES ('%s', '%s', '%s','%s')",
				representives,verbs,'1',subjectType);

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
	
	public ArrayList<VerbCluster> getDictionary()
	{
		ArrayList<VerbCluster> retList = new ArrayList<VerbCluster>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".synonymdictionary");
		
		//System.out.println(query);
		
		try {
			if(this.getConnection() == null)
			{
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			stmt = this.getConnection().createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
		}

		try{
			//String tmpStr;
			
			while(rs.next())
			{
				String rep = rs.getString("representives");
				String verbs = rs.getString("verbs");
				String iscustom = rs.getString("iscustom");
				String type = rs.getString("subjectType");
				retList.add(new VerbCluster(rep,verbs,iscustom,type));
			}
		}
		catch(Exception ex) {}
		
		return retList;
	}
	
	public ArrayList<VerbCluster> getUserDictionary()
	{
		ArrayList<VerbCluster> retList = new ArrayList<VerbCluster>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".synonymdictionary where subjectType = 'u'");
		
		//System.out.println(query);
		
		try {
			if(this.getConnection() == null)
			{
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			stmt = this.getConnection().createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
		}

		try{
			//String tmpStr;
			
			while(rs.next())
			{
				String rep = rs.getString("representives");
				String verbs = rs.getString("verbs");
				String iscustom = rs.getString("iscustom");
				String type = rs.getString("subjectType");
				retList.add(new VerbCluster(rep,verbs,iscustom,type));
			}
		}
		catch(Exception ex) {}
		
		return retList;
	}
	
	public ArrayList<VerbCluster> getSystemDictionary()
	{
		ArrayList<VerbCluster> retList = new ArrayList<VerbCluster>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".synonymdictionary where subjectType = 's'");
		
		//System.out.println(query);
		
		try {
			if(this.getConnection() == null)
			{
				System.out.println("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			stmt = this.getConnection().createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
		}

		try{
			//String tmpStr;
			
			while(rs.next())
			{
				String rep = rs.getString("representives");
				String verbs = rs.getString("verbs");
				String iscustom = rs.getString("iscustom");
				String type = rs.getString("subjectType");
				retList.add(new VerbCluster(rep,verbs,iscustom,type));
			}
		}
		catch(Exception ex) {}
		
		return retList;
	}
	
	public void deleteDictionaryItem(String rep)
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`synonymdictionary` WHERE representives = '%s'",rep);

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
