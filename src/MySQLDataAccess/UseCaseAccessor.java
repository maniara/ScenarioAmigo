package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.Flow;
import Entity.UseCase;

public class UseCaseAccessor {
	
	private Connection conn;
	
	public UseCaseAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addUseCase(UseCase uc)
	{
		Statement stmt = null;
		String query = String
				.format("INSERT INTO "+DataAccessString.dbName+".`usecase` (`usecaseID`, `projectID`, `usecaseName`, `usecaseDescription`, `actor`,`preCondition`, `postCondition`, `includedOf`, `extendsOf`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
						uc.getUseCaseID(), uc.getProjectID(),
						uc.getUseCaseName(), uc.getUseCaseDescription(),
						uc.getActor(), uc.getPreCondition(),
						uc.getPostCondition(), uc.getIncludedOf(),
						uc.getExtendsOf());

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
		
		FlowAccessor FA = new FlowAccessor();
		for(Flow flow : uc.getFlowList())
		{
			FA.addFlow(flow, 'u');
		}
	}
	
	public void deleteUseCase(String projectID, String usecaseID)
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`usecase` WHERE projectID = '%s' and usecaseID = '%s'", projectID, usecaseID);

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
		
		FlowAccessor FA = new FlowAccessor();
		FA.deleteFlow(projectID, usecaseID);
		
		SentenceAccessor SA = new SentenceAccessor();
		SA.deleteSentence(projectID, usecaseID);
	}
	
	public ArrayList<UseCase> getUseCaseList(String projectID)
	{
		FlowAccessor fa = new FlowAccessor();
		ArrayList<UseCase> UCList = new ArrayList<UseCase>();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM "+DataAccessString.dbName+".usecase where projectID = '" + projectID + "'";
		//System.out.println(query);
		
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
				String usecaseID = rs.getString("usecaseID");
				//UseCase(String projectID, String usecaseID, String usecaseName, String preCondition, String postCondition, String usecaseDescription, String extendsOf, String includeOf)
				UseCase uc = new UseCase(rs.getString("projectID"),rs.getString("usecaseID"),rs.getString("usecaseName"),rs.getString("actor"),rs.getString("preCondition"), rs.getString("postCondition"), rs.getString("usecaseDescription"));
				if(rs.getString("includedOf") != null)
					uc.setIncludedOf(rs.getString("includedOf"));
				if(rs.getString("extendsOf") != null)
					uc.setExtendsOf(rs.getString("extendsOf"));
				UCList.add(uc);
				
				fa.getFlowList(projectID, usecaseID,'u');
				for(Flow f : fa.getFlowList(projectID, usecaseID,'u'))
				{
					if(!f.getIsAlternative())
					{
						f.addAllSentence();
						uc.addFlow(f);
					}
				}
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		return UCList;
	}
	
	public ArrayList<UseCase> getExceptedTrainingUseCaseList(String projectID)
	{
		String query = "";
		query = "SELECT * FROM "+DataAccessString.dbName+".training_use_case where projectID != '"+projectID+"'";
		return getTraningUseCaseList(query);
	}
	
	public ArrayList<UseCase> getAllTrainingUseCaseList()
	{
		String query = "";
		query = "SELECT * FROM "+DataAccessString.dbName+".training_use_case";
		return getTraningUseCaseList(query);
	}
	
	private ArrayList<UseCase> getTraningUseCaseList(String query)
	{
		ArrayList<UseCase> UCList = new ArrayList<UseCase>();
		FlowAccessor fa = new FlowAccessor();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		//System.out.println(query);
		
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		}
		catch(SQLException ex)
		{
			System.out.println("Query Execution Error");
			ex.printStackTrace();
		}

		try{
			while(rs.next())
			{
				String projectID = rs.getString("projectID");
				String usecaseID = rs.getString("usecaseID");
				//UseCase(String projectID, String usecaseID, String usecaseName, String preCondition, String postCondition, String usecaseDescription, String extendsOf, String includeOf)
				UseCase uc = new UseCase(projectID,usecaseID,rs.getString("usecaseName"),rs.getString("actor"),rs.getString("preCondition"), rs.getString("postCondition"), rs.getString("usecaseDescription"));
				if(rs.getString("includedOf") != null)
					uc.setIncludedOf(rs.getString("includedOf"));
				if(rs.getString("extendsOf") != null)
					uc.setExtendsOf(rs.getString("extendsOf"));

				
				fa.getFlowList(projectID, usecaseID,'u');
				for(Flow f : fa.getFlowList(projectID, usecaseID,'u'))
				{
					if(!f.getIsAlternative())
					{
						f.addAllSentence();
						uc.addFlow(f);
					}
				}
				
				UCList.add(uc);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		//System.out.println(UCList.size());
		
		//add start node 
		for(UseCase uc : UCList)
		{
			uc.addStartNode();
		}
		
		return UCList;
	}
}
