package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Entity.Sentence;
import Entity.VerbCluster;
import Entity.VerbFrequency;

public class VerbFrequencyAccessor {
private Connection conn;
	
	private Connection getConnection() throws SQLException
	{
		if(conn == null)
			conn = MySqlConnectionCreator.getConnection();
		else if(conn.isClosed())
			conn = MySqlConnectionCreator.getConnection();
		
		return conn;
	}
	
	public VerbFrequencyAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void createFrequencyTable(ArrayList<VerbFrequency> frequencies)
	{
		deleteAllFrequency();
		
		for(VerbFrequency vf : frequencies)
		{
			
			Statement stmt = null;
			String query = String.format("INSERT INTO "+DataAccessString.dbName+".`verbfrequency` (`verb`, `count`, `frequency`,`type`,`totalCount`) VALUES ('%s', '%d', '%f','%s','%d')",
					vf.getVerb(),vf.getCount(),vf.getFrequency(),vf.getType(),vf.getTotal());
	
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
	
	public ArrayList<VerbFrequency> getAllFrequency()
	{
		ArrayList<VerbFrequency> retList = new ArrayList<VerbFrequency>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".verbfrequency");
		//System.out.println(query);
		
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
				String verb = rs.getString("verb");
				int count = Integer.parseInt(rs.getString("count"));
				double frequency = Double.parseDouble(rs.getString("frequency"));
				String type = rs.getString("type");
				int total = Integer.parseInt(rs.getString("totalCount"));
				retList.add(new VerbFrequency(verb,type,count,total,frequency));
			}
		}
		catch(Exception ex) {}
		
		return retList;
	}
	
	public ArrayList<VerbFrequency> getAllFrequencyByTH(double th)
	{
		ArrayList<VerbFrequency> retList = new ArrayList<VerbFrequency>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("select * from "+DataAccessString.dbName+".verbfrequency");
		//System.out.println(query);
		
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
				String verb = rs.getString("verb");
				int count = Integer.parseInt(rs.getString("count"));
				double frequency = Double.parseDouble(rs.getString("frequency"));
				String type = rs.getString("type");
				int total = Integer.parseInt(rs.getString("totalCount"));
				if(frequency >= th)
					retList.add(new VerbFrequency(verb,type,count,total,frequency));
			}
		}
		catch(Exception ex) {}
		
		return retList;
	}
	
	public void deleteAllFrequency()
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`verbfrequency`");

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
