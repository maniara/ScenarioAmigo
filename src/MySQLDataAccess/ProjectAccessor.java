package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.Project;

public class ProjectAccessor {
	
	private Connection conn;
	
	public ProjectAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addProject(String id, String name, String desc, String person, boolean forTraining)
	{
		int isTraining = 0;
		if(forTraining)
			isTraining = 1;
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".project(projectID, projectName, projectDescription, createPerson, createDate, forTraining) values ('%s', '%s', '%s', '%s', now(), '%d')",id,name,desc,person,isTraining);
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
	
	public ArrayList<Project> getProjectList(boolean isTraining)
	{
		ArrayList<Project> PList = new ArrayList<Project>();
	
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM "+DataAccessString.dbName+".project";
		
		if(isTraining)
			query = query + " where forTraining = '1'";
		else
			query = query + " where forTraining = '0'";
		
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
		}

		try{
			while(rs.next())
			{
				PList.add(new Project(rs.getString("projectID"),rs.getString("projectName"),rs.getDate("createDate"),rs.getString("createPerson"), rs.getString("projectDescription")));
			}
		}
		catch(Exception ex) {}
		
		return PList;
	}
}
