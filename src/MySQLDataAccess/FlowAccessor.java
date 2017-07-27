package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.Flow;
import Entity.Sentence;

public class FlowAccessor
{
	private Connection conn;

	public FlowAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addFlow(Flow flow, char flowType)
	{
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".`flow` (`projectID`, `usecaseID`, `isAlternative`, `startOrder`, `flowID`, `startCondition` , `flowType`) VALUES ('%s','%s','%c','%s','%s','%s','%c')",
				flow.getProjectID(), flow.getUseCaseID(), flow.getIsAlternativeByChar(),flow.getStartOrder(),flow.getFlowID(),flow.getStartCondition(),flowType);

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
		
		SentenceAccessor SA = new SentenceAccessor();
		for(Sentence sen : flow.getSentenceList())
		{
			if(flow.getIsAlternative() == false)
			{
				if(sen.getSentenceOrder() == null || sen.getSentenceOrder().equals(""))
					sen.setSentenceOrder(flow.getSentenceList().indexOf(sen)+"");
			}
			else
			{
				if(sen.getSentenceOrder() == null || sen.getSentenceOrder().equals(""))
					sen.setSentenceOrder(flow.getStartOrder()+flow.getSentenceList().indexOf(sen));
			}
			SA.addSentence(sen);
		}
		
	}
	
	public void deleteFlow(String projectID, String usecaseID)
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`flow` WHERE projectID = '%s' and usecaseID = '%s'", projectID, usecaseID);

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
	
	public ArrayList<Flow> getFlowList(String projectID, String usecaseID, char flowType)
	{
		ArrayList<Flow> FList = new ArrayList<Flow>();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM "+DataAccessString.dbName+".flow Where projectID = '%s' and usecaseID like '%s' and flowType = '%s'",projectID, usecaseID, flowType);
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
				Flow flow = null;
				flow = getFlowObject(rs);
				
				FList.add(flow);
			}
		}
		catch(Exception ex) {}
		//System.out.println(FList.size());
		return FList;
	}
	
	public ArrayList<Flow> getAllBasicFlowList()
	{
		ArrayList<Flow> FList = new ArrayList<Flow>();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM "+DataAccessString.dbName+".flow Where isAlternative = 'N'");
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
				Flow flow = null;
				flow = getFlowObject(rs);
				
				FList.add(flow);
			}
		}
		catch(Exception ex) {}
		//System.out.println(FList.size());
		return FList;
	}
	
	public ArrayList<Flow> getAllBasicFlowListForValidate(String projectId)
	{
		ArrayList<Flow> FList = new ArrayList<Flow>();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM "+DataAccessString.dbName+".flow Where isAlternative = 'N' and projectId != '"+projectId+"'");
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
				Flow flow = null;
				flow = getFlowObject(rs);
				
				FList.add(flow);
			}
		}
		catch(Exception ex) {}
		//System.out.println(FList.size());
		return FList;
	}
	
	private Flow getFlowObject(ResultSet rs)
	{
		Flow flow = null;
		String projectID = null;
		String usecaseID = null;
		String startOrder = null;
		String flowID = null;
		String startCondition = null;
		char flowType = 0;
		ArrayList<Sentence> sentenceList = null;
		
		boolean isAlternative = false;

		try{
			if(rs.getString("isAlternative").equals("Y"))
				isAlternative = true;
			projectID = rs.getString("projectID");
			usecaseID = rs.getString("usecaseID");
			startOrder = rs.getString("startOrder");
			flowID = rs.getString("flowID");
			startCondition = rs.getString("startCondition");
			flowType = rs.getString("flowType").toCharArray()[0];
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		SentenceAccessor SA = new SentenceAccessor();
		sentenceList = SA.getSentenceList(projectID, usecaseID, flowID);
		flow = new Flow(projectID, usecaseID, isAlternative, startOrder, flowID, sentenceList, startCondition,flowType);

		return flow;
	}
}
