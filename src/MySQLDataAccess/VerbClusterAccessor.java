package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import Entity.InteractionString;
import Entity.Sentence;
import Entity.VerbCluster;

public class VerbClusterAccessor {
	
	private Connection conn;
	
	private Connection getConnection() throws SQLException
	{
		if(conn == null)
			conn = MySqlConnectionCreator.getConnection();
		else if(conn.isClosed())
			conn = MySqlConnectionCreator.getConnection();
		
		return conn;
	}
	
	public VerbClusterAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addCluster(String representives, String verbs, String subjectType)
	{
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".`verbcluster` (`representives`, `verbs`, `iscustom`,`subjectType`) VALUES ('%s', '%s', '%s','%s')",
				representives,verbs,'0',subjectType);

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
	


	public ArrayList<VerbCluster> getAllClusters()
	{
		ArrayList<VerbCluster> retList = new ArrayList<VerbCluster>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".verbcluster");
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
	
	public HashMap<String,ArrayList<String>> getAllVerbOfCustomCluster()
	{
		HashMap<String,ArrayList<String>> retMap = new HashMap<String,ArrayList<String>>();
		
		ArrayList<String> userList = new ArrayList<String>();
		ArrayList<String> systemList = new ArrayList<String>();
		
		retMap.put("u", userList);
		retMap.put("s", systemList);
		
		ArrayList<VerbCluster> clusters = this.getAllClusters();
		
		for(VerbCluster vc : clusters)
		{
			if(vc.isCustom())
			{
				for(String s : vc.getVerbs().split(";"))
				{
					if(vc.getSubjectType().equals("u"))
						userList.add(s);
					else if(vc.getSubjectType().equals("s"))
						systemList.add(s);
				}
			}
		}
		
		return retMap;
	}

	public void deleteAllGeneratedCluster()
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`verbcluster` WHERE iscustom = '0'");

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
	
	public void deleteAllCluster()
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`verbcluster`");

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
