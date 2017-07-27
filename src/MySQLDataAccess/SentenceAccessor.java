package MySQLDataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Entity.InteractionString;
import Entity.Sentence;

public class SentenceAccessor {

	private Connection conn;

	public SentenceAccessor() {
		conn = MySqlConnectionCreator.getConnection();
	}

	public Connection getConnection() {
		if (conn == null) {
			conn = MySqlConnectionCreator.getConnection();
		}

		try {
			if (conn.isClosed()) {
				conn = MySqlConnectionCreator.getConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public HashMap<String, ArrayList<InteractionString>> getInteractionStringMap() {
		HashMap<String, ArrayList<InteractionString>> retMap = new HashMap<String, ArrayList<InteractionString>>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String
				.format("select usecaseID, sentenceOrder, sentenceType, isRepeatable, isOptional, sentenceContents from "
						+ DataAccessString.dbName
						+ ".sentence where projectID = 'Pattern' and flowID in (select flowID from raffer.flow where flowtype = 'p' and isAlternative = 'N') group by usecaseID,sentenceOrder order by usecaseID, sentenceOrder");
		//System.out.println(query);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			// String tmpStr;

			while (rs.next()) {
				String id = rs.getString("usecaseID");
				boolean isR = this.getBoolean(rs.getString("isRepeatable"));
				boolean isO = this.getBoolean(rs.getString("isOptional"));
				String type = rs.getString("sentenceType");
				String contents = rs.getString("sentenceContents");
				if (isR)
					type = type.toUpperCase();

				if (retMap.containsKey(id)) {
					ArrayList<InteractionString> curList = retMap.get(id);
					if (isO) {
						ArrayList<InteractionString> tmpList = new ArrayList<InteractionString>();
						tmpList.addAll(curList);
						for (InteractionString s : tmpList) {
							ArrayList<String> tmpVerbList = new ArrayList<String>();
							tmpVerbList.addAll(s.getSentenceList());
							tmpVerbList.add(contents);
							curList.add(new InteractionString(id, s.getInteractionString() + type, tmpVerbList));
						}
					} else {
						for (InteractionString s : curList) {
							s.addInteraction(type);
							s.addSentence(contents);
						}
					}
					// rs.getString("sentenceType");
				} else {
					ArrayList<InteractionString> tmpList = new ArrayList<InteractionString>();
					tmpList.add(new InteractionString(id, type, contents));
					if (isO)
						tmpList.add(new InteractionString());
					retMap.put(id, tmpList);
				}
			}
		} catch (Exception ex) {
		}

		return retMap;
	}

	/*
	 * public HashMap<String,ArrayList<String>> getInteractionStringListMap() {
	 * HashMap<String,ArrayList<String>> retMap = new
	 * HashMap<String,ArrayList<String>>(); Statement stmt = null; ResultSet rs
	 * = null; String query = String.format(
	 * "select usecaseID, sentenceOrder, sentenceType, isRepeatable, isOptional from raffer.sentence where projectID = 'Pattern' group by usecaseID,sentenceOrder order by usecaseID, sentenceOrder"
	 * ); //System.out.println(query);
	 * 
	 * try{ stmt = conn.createStatement(); rs = stmt.executeQuery(query); }
	 * catch(SQLException ex) { System.out.println("Query Execution Error"); }
	 * 
	 * try{ //String tmpStr;
	 * 
	 * while(rs.next()) { String id = rs.getString("usecaseID"); boolean isR =
	 * this.getBoolean(rs.getString("isRepeatable")); boolean isO =
	 * this.getBoolean(rs.getString("isOptional")); String type =
	 * rs.getString("sentenceType"); if(isR) type = type.toUpperCase();
	 * 
	 * if(retMap.containsKey(id)) { ArrayList<String> curList = retMap.get(id);
	 * if(isO) { ArrayList<String> tmpList = new ArrayList<String>();
	 * tmpList.addAll(curList); for(String s : tmpList) { curList.add(s+type); }
	 * } else { for(String s : curList) { curList.remove(s); s = s + type;
	 * curList.add(s); } } //rs.getString("sentenceType"); } else {
	 * ArrayList<String> tmpList = new ArrayList<String>(); tmpList.add(type);
	 * if(isO) tmpList.add(""); retMap.put(id, tmpList); } } } catch(Exception
	 * ex) {}
	 * 
	 * return retMap; }
	 */

	public void addSentence(Sentence sentence) {
		Statement stmt = null;
		String query = String.format(
				"INSERT INTO " + DataAccessString.dbName
						+ ".`sentence` (`projectID`, `usecaseID`, `sentenceOrder`, `sentenceContents`, `flowID`, `sentenceType`,`sentenceSeq`,`isRepeatable`,`isOptional`) VALUES ('%s', '%s', '%s', '%s', '%s', '%c' ,%d, '%c','%c')",
				sentence.getProjectID(), sentence.getUseCaseID(), sentence.getSentenceOrder(),
				sentence.getSentenceContents(), sentence.getFlowID(), sentence.getSentenceType(),
				sentence.getSentenceSeq(), getChar(sentence.isRepeatable()), getChar(sentence.isOptional()));

		// System.out.println(query);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
			ex.printStackTrace();
		}
	}

	public void deleteSentence(String projectID, String usecaseID) {
		Statement stmt = null;
		String query = String.format(
				"DELETE FROM " + DataAccessString.dbName + ".`sentence` WHERE projectID = '%s' and usecaseID = '%s'",
				projectID, usecaseID);

		// System.out.println(query);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
			ex.printStackTrace();
		}
	}

	public ArrayList<Sentence> getPatternSentenceList() {
		// System.out.println("getSentenceList starts");
		ArrayList<Sentence> SList = new ArrayList<Sentence>();

		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM SOFAR.sentence Where projectID = 'Pattern'");
		// System.out.println(query);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				SList.add(new Sentence(rs.getString("projectID"), rs.getString("usecaseID"), rs.getString("flowID"),
						rs.getString("sentenceOrder"), rs.getString("sentenceContents"),
						rs.getString("sentenceType").toCharArray()[0], rs.getInt("sentenceSeq"),
						getBoolean(rs.getString("isRepeatable")), getBoolean(rs.getString("isOptional")),rs.getString("isSample")));
			}
		} catch (Exception ex) {
		}

		return SList;
	}

	public ArrayList<Sentence> getAllTrainingSentenceList() {
		ArrayList<Sentence> SList = new ArrayList<Sentence>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format(
				"SELECT * "+
				"FROM "+DataAccessString.dbName+".sentence "+
				"WHERE (projectID IN (SELECT projectID FROM "+DataAccessString.dbName+".project WHERE (forTraining = 1)) "+
				"AND flowID IN (SELECT flowID FROM "+DataAccessString.dbName+".flow WHERE (isAlternative = 'N')))");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				Sentence sen = new Sentence(rs.getString("sentenceNum"), rs.getString("projectID"),
						rs.getString("usecaseID"), rs.getString("flowID"), rs.getString("sentenceOrder"),
						rs.getString("sentenceContents"), rs.getString("sentenceType").toCharArray()[0],
						rs.getInt("sentenceSeq"), getBoolean(rs.getString("isRepeatable")),
						getBoolean(rs.getString("isOptional")),null);
				sen.setVerb(rs.getString("mainVerb"));
				SList.add(sen);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return SList;
	}

	public ArrayList<Sentence> getNoVerbTrainingSentenceList() {
		ArrayList<Sentence> SList = new ArrayList<Sentence>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM " + DataAccessString.dbName
				+ ".sentence where projectID in (select projectID from SOFAR.project where forTraining = 1 and mainVerb is null )");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				SList.add(
						new Sentence(rs.getString("sentenceNum"), rs.getString("projectID"), rs.getString("usecaseID"),
								rs.getString("flowID"), rs.getString("sentenceOrder"), rs.getString("sentenceContents"),
								rs.getString("sentenceType").toCharArray()[0], rs.getInt("sentenceSeq"),
								getBoolean(rs.getString("isRepeatable")), getBoolean(rs.getString("isOptional")),rs.getString("isSample")));
			}
		} catch (Exception ex) {
		}

		return SList;
	}

	public ArrayList<Sentence> getBasicFlowSentenceList(String projectID, String usecaseID) {
		// System.out.println("getSentenceList starts");
		ArrayList<Sentence> SList = new ArrayList<Sentence>();

		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM " + DataAccessString.dbName
				+ ".sentence Where projectID = '%s' and usecaseID = '%s' and flowID = (select flowID from "
				+ DataAccessString.dbName + ".flow where projectId = '%s' and usecaseid = '%s' and isAlternative='N')",
				projectID, usecaseID, projectID, usecaseID);
		// System.out.println(query);

		try {
			stmt = getConnection().createStatement();
			if (stmt == null) {
				System.out.println("");
			}
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				Sentence sen = new Sentence(rs.getString("projectID"), rs.getString("usecaseID"),
						rs.getString("flowID"), rs.getString("sentenceOrder"), rs.getString("sentenceContents"),
						rs.getString("sentenceType").toCharArray()[0], rs.getInt("sentenceSeq"),
						getBoolean(rs.getString("isRepeatable")), getBoolean(rs.getString("isOptional")),rs.getString("isSample"));
				sen.setSentenceNum(rs.getString("sentenceNum"));
				if (rs.getString("mainVerb") != null)
					sen.setVerb(rs.getString("mainVerb"));
				SList.add(sen);
			}
		} catch (Exception ex) {
		}

		// set next and previous U/S type
		Collections.sort(SList);

		for (int i = 0; i < SList.size(); i++) {
			String prev = null;
			String next = null;

			if (i == 0) {
				next = SList.get(i + 1).getSentenceType() + "";
			} else if (i == SList.size() - 1) {
				prev = SList.get(i - 1).getSentenceType() + "";
			} else {
				next = SList.get(i + 1).getSentenceType() + "";
				prev = SList.get(i - 1).getSentenceType() + "";
			}

			SList.get(i).setPrevType(prev);
			SList.get(i).setNextType(next);
		}

		return SList;
	}

	public ArrayList<Sentence> getSentenceList(String projectID, String usecaseID, String flowID) {
		// System.out.println("getSentenceList starts");
		ArrayList<Sentence> SList = new ArrayList<Sentence>();

		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format(
				"SELECT * FROM " + DataAccessString.dbName
						+ ".sentence Where projectID = '%s' and usecaseID = '%s' and flowID = '%s'",
				projectID, usecaseID, flowID);
		// System.out.println(query);

		try {
			stmt = getConnection().createStatement();
			if (stmt == null) {
				System.out.println("");
			}
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				Sentence sen = new Sentence(rs.getString("projectID"), rs.getString("usecaseID"),
						rs.getString("flowID"), rs.getString("sentenceOrder"), rs.getString("sentenceContents"),
						rs.getString("sentenceType").toCharArray()[0], rs.getInt("sentenceSeq"),
						getBoolean(rs.getString("isRepeatable")), getBoolean(rs.getString("isOptional")),null);
				sen.setSentenceNum(rs.getString("sentenceNum"));
				if (rs.getString("mainVerb") != null)
					sen.setVerb(rs.getString("mainVerb"));
				SList.add(sen);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// set next and previous U/S type
		Collections.sort(SList);

		for (int i = 0; i < SList.size(); i++) {
			String prev = null;
			String next = null;

			if (i == 0) {
				next = SList.get(i + 1).getSentenceType() + "";
			} else if (i == SList.size() - 1) {
				prev = SList.get(i - 1).getSentenceType() + "";
			} else {
				next = SList.get(i + 1).getSentenceType() + "";
				prev = SList.get(i - 1).getSentenceType() + "";
			}

			SList.get(i).setPrevType(prev);
			SList.get(i).setNextType(next);
		}

		return SList;
	}

	public void updateVerb(Sentence s) {
		Statement stmt = null;
		String query = String.format(
				"UPDATE " + DataAccessString.dbName + ".sentence SET mainVerb = '%s' Where sentenceNum = '%s'",
				s.getVerb(), s.getSentenceNum());

		// System.out.println(query);

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Query Execution Error");
		}

	}

	private char getChar(Boolean b) {
		if (b)
			return 'Y';
		else
			return 'N';

	}

	private boolean getBoolean(String s) {
		if (s.equals("Y"))
			return true;
		else
			return false;

	}

	public ArrayList<Sentence> getTrainingSentenceListForValidation(String targetProjectId) {
		ArrayList<Sentence> SList = new ArrayList<Sentence>();
		Statement stmt = null;
		ResultSet rs = null;
		String query = String.format("SELECT * FROM " + DataAccessString.dbName
				+ ".training_basicflow_sentence where projectid != '" + targetProjectId + "'");
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Query Execution Error");
		}

		try {
			while (rs.next()) {
				// String projectID, String usecaseID, String flowID, String
				// sentenceOrder, String sentenceContents, char sentenceType
				Sentence sen = new Sentence(rs.getString("sentenceNum"), rs.getString("projectID"),
						rs.getString("usecaseID"), rs.getString("flowID"), rs.getString("sentenceOrder"),
						rs.getString("sentenceContents"), rs.getString("sentenceType").toCharArray()[0],
						rs.getInt("sentenceSeq"), getBoolean(rs.getString("isRepeatable")),
						getBoolean(rs.getString("isOptional")),rs.getString("isSample"));
				sen.setVerb(rs.getString("mainVerb"));
				SList.add(sen);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return SList;
	}
}
