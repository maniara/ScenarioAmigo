package Entity;

import java.util.ArrayList;

public class UseCase {
	private String projectID;
	private String usecaseID;
	private String usecaseName;
	private String actor;
	private String preCondition;
	private String postCondition;
	private String usecaseDescription;
	private String extendsOf;
	private String includedOf;
	private ArrayList<Flow> flowList;
	
	public UseCase(String projectID, String usecaseID, String usecaseName, String actor, String preCondition, String postCondition, String usecaseDescription)
	{
		this.projectID = projectID;
		this.actor = actor;
		this.usecaseID = usecaseID;
		this.usecaseName = usecaseName;
		this.preCondition = preCondition;
		this.postCondition = postCondition;
		this.usecaseDescription = usecaseDescription;
		this.flowList = new ArrayList<Flow>();
	}
	
	public ArrayList<Sentence> getBasicFlowSentences()
	{
		for(Flow f:this.flowList)
		{
			if(!f.getIsAlternative())
				return f.getSentenceList();
		}
		return null;
	}
	
	public Sentence getBasicFlowSentence(int seq)
	{
		for(Sentence sen : this.getBasicFlowSentences())
		{
			if(sen.getSentenceSeq() == seq)
				return sen;
		}
		return null;
	}
	
	public void setFlowList(ArrayList<Flow> flowList)
	{
		this.flowList = flowList;
	}
	
	public void addFlow(Flow flow)
	{
		this.flowList.add(flow);
	}
	
	public void setExtendsOf(String extendsOf)
	{
		this.extendsOf = extendsOf;
	}
	
	public void setIncludedOf(String includedOf)
	{
		this.includedOf = includedOf;
	}
	
	public String getProjectID()
	{
		return this.projectID;
	}
	
	public String getUseCaseID()
	{
		return this.usecaseID;
	}
	
	public String getUseCaseName()
	{
		return this.usecaseName;
	}
	
	public String getPreCondition()
	{
		return this.preCondition;
	}
	
	public String getPostCondition()
	{
		return this.postCondition;
	}
	
	public String getUseCaseDescription()
	{
		return this.usecaseDescription;
	}
	
	public String getExtendsOf()
	{
		return this.extendsOf;
	}
	
	public String getIncludedOf()
	{
		return this.includedOf;
	}
	
	public ArrayList<Flow> getFlowList() 
	{
		return this.flowList;
	}

	public String getActor() {
		return actor;
	}

	public void addStartNode() {
		Sentence fistSen = this.getBasicFlowSentences().get(0);
		Sentence startSentence = new Sentence(fistSen.getProjectID(), fistSen.getUseCaseID(), fistSen.getFlowID(), "0", "Scnario Start", 's', 0, false, false,"n");
		startSentence.setVerb("ScenarioStart");
		startSentence.setRepresentVerb("ScenarioStart");
		for(Flow f:this.flowList)
		{
			if(!f.getIsAlternative())
			{
				f.getSentenceList().add(startSentence);
				break;
			}
		}
	} 
	
}
