package Entity;

import java.util.ArrayList;
import java.util.Collections;

import MySQLDataAccess.SentenceAccessor;

public class Flow {
	private String startOrder;
	private boolean isAlternative;
	private ArrayList<Sentence> sentenceList; 
	private String flowID;
	private String startCondition;
	private String projectID;
	private String usecaseID;
	private char flowType;
	
	public Flow(String projectID, String usecaseID, boolean isAlternative, String startOrder, String flowID, ArrayList<Sentence> sentenceList, char flowType)
	{
		//sentenceList = new ArrayList<Sentence>();
		this.isAlternative = isAlternative;
		this.startOrder = startOrder;
		this.flowID = flowID;
		this.sentenceList = sentenceList;
		this.projectID = projectID;
		this.usecaseID = usecaseID;
		this.flowType = flowType;
	}
	
	public Flow(String projectID, String usecaseID, boolean isAlternative, String startOrder, String flowID, ArrayList<Sentence> sentenceList, String startCondition, char flowType)
	{
		this.isAlternative = isAlternative;
		this.startOrder = startOrder;
		this.flowID = flowID;
		this.sentenceList = sentenceList;
		this.startCondition = startCondition;
		this.projectID = projectID;
		this.usecaseID = usecaseID;
		this.flowType = flowType;
	}
	
	public Flow(Flow flow) {
		this(flow.getProjectID(),flow.getUseCaseID(),flow.getIsAlternative(),flow.getStartOrder(),flow.getFlowID(),null,flow.getStartCondition(),flow.getFlowType());
		ArrayList<Sentence> SA = new ArrayList<Sentence>();
		SA.addAll(flow.getSentenceList());
		this.sentenceList = SA;
	}
	
	public String getFlowString()
	{
		String ret = "";
		for(int i=0;i<this.getSentenceList().size();i++)
		{
			Sentence s = this.getSentenceBySeq(i);
			ret = ret + s.getVerbString()+ "-";
		}
		return ret;
		
	}

	public char getFlowType()
	{
		return this.flowType;
	}
	
	public String getStartOrder()
	{
		return this.startOrder;
	}
	
	public boolean getIsAlternative()
	{
		return this.isAlternative;
	}
	
	public char getIsAlternativeByChar()
	{
		if(this.isAlternative)
			return 'Y';
		else
			return 'N';
	}
	
	public ArrayList<Sentence> getSentenceList()
	{
		return this.sentenceList;
	}
	
	public String getFlowID()
	{
		return this.flowID;
	}
	
	public String getStartCondition()
	{
		return this.startCondition;
	}
	public String getProjectID()
	{
		return this.projectID;
	}
	public String getUseCaseID()
	{
		return this.usecaseID;
	}
	
	public int getFirstRepeat()
	{
		for(Sentence s:this.sentenceList)
		{
			if(s.isRepeatable())
				return s.getSentenceSeq();
		}
		return 0;
	}

	public void changeOrder(String from, String to) {
		String original = this.startOrder; 
		String changed = original.replaceFirst(from, to);
		this.startOrder = changed;
		
		for(Sentence s:this.sentenceList)
		{
			s.changeOrder(from, to);
		}
	}
	
	public void addAllSentence()
	{
		SentenceAccessor sa = new SentenceAccessor();
		this.sentenceList=sa.getSentenceList(this.projectID, this.usecaseID, this.flowID);
	}
	
	public void addStartNode()
	{
		Sentence fistSen = this.getSentenceList().get(0);
		Sentence startSentence = new Sentence(fistSen.getProjectID(), fistSen.getUseCaseID(), fistSen.getFlowID(), "0", "Scnario Start", 's', 0, false, false,"n");
		startSentence.setVerb("ScenarioStart");
		startSentence.setRepresentVerb("ScenarioStart");
		
		this.getSentenceList().add(startSentence);
	}
	
	public Sentence getSentenceBySeq(int seq)
	{
		for(Sentence s: this.sentenceList)
			if(s.getSentenceSeq() == seq)
				return s;
		return null;
	}
}
