package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Entity.Flow;
import Entity.Pattern;
import PatternGenerator.PatternFragment;
import PatternGenerator.PatternFragmentSet;

public class PatternAccessor {
	private Connection conn;

	public PatternAccessor()
	{
		conn = MySqlConnectionCreator.getConnection();
	}
	
	public void addPattern(PatternFragment pattern)
	{
		Statement stmt = null;
		String query = String.format("INSERT INTO "+DataAccessString.dbName+".`extractedpattern` (`patternString`, `patternScore`) VALUES ('%s',%.3f)"
				,pattern.toString(),pattern.getAdjustedWeight());
				
		System.out.println(query);
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
	
	public PatternFragmentSet getPatternSet()
	{
		PatternFragmentSet set = new PatternFragmentSet();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM "+DataAccessString.dbName+".extractedpattern";
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
				//UseCase(String projectID, String usecaseID, String usecaseName, String preCondition, String postCondition, String usecaseDescription, String extendsOf, String includeOf)
				PatternFragment p = new PatternFragment(rs.getString("patternString"),Double.parseDouble(rs.getString("patternScore")));
				set.add(p);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		return set;
	}
	
	public void addPatternList(){
		if (PatternGenerator.PatternSetInstance.PatternSet != null) {
			PatternFragmentSet set = PatternGenerator.PatternSetInstance.PatternSet;

			for (PatternFragment pf : set) {
				addPattern(pf);
			}

			System.out.println(set.size() + " patterns are stored.");
		}

	}
	
	public void deletePattern(String patternID)
	{
		Statement stmt = null;
		String query = String.format("DELETE FROM "+DataAccessString.dbName+".`extractedpattern` WHERE patternID = '%s'", patternID);

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
		FA.deleteFlow("Pattern", patternID);
		
		SentenceAccessor SA = new SentenceAccessor();
		SA.deleteSentence("Pattern", patternID);
	}
	
	public ArrayList<Pattern> getPatternList()
	{
		ArrayList<Pattern> PList = new ArrayList<Pattern>();
		
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM "+DataAccessString.dbName+".pattern";
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
				//UseCase(String projectID, String usecaseID, String usecaseName, String preCondition, String postCondition, String usecaseDescription, String extendsOf, String includeOf)
				Pattern p = new Pattern(rs.getString("patternID"),rs.getString("patternName"),null);
				PList.add(p);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		return PList;
	}
	
	public Pattern getPattern(String patternId)
	{
		Pattern p = null;
		Statement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM "+DataAccessString.dbName+".pattern WHERE patternID = '"+patternId+"'";
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
				//UseCase(String projectID, String usecaseID, String usecaseName, String preCondition, String postCondition, String usecaseDescription, String extendsOf, String includeOf)
				p = new Pattern(rs.getString("patternID"),rs.getString("patternName"),null);
			}
		}
		catch(Exception ex) {ex.printStackTrace();}
		
		FlowAccessor FA = new FlowAccessor();
		ArrayList<Flow> FList = FA.getFlowList("Pattern", patternId, 'p');
		//System.out.println(FList.size());
		p.setFlowList(FList);
		
		return p;
	}
}
