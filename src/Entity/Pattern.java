package Entity;

import java.util.ArrayList;

public class Pattern {
	
	private String patternID;
	private String patternName;
	private ArrayList<Flow> flowList;
	
	public Pattern(String patternID, String patternName, ArrayList<Flow> flowList)
	{
		this.patternID = patternID;
		this.patternName = patternName;
		this.flowList = flowList;
	}
	
	public String getPatternID()
	{
		return this.patternID;
	}
	
	public String getPatternName()
	{
		return this.patternName;
	}
	
	public ArrayList<Flow> getFlowList()
	{
		return this.flowList;
	}
	
	public void setFlowList(ArrayList<Flow> flowList)
	{
		if(this.flowList == null)
			this.flowList = new ArrayList<Flow>();
		for(Flow f:flowList)
		{
			this.flowList.add(f);
		}
	}
	
	public void addFlow(Flow flow)
	{
		if(this.flowList == null)
			flowList = new ArrayList<Flow>();
		this.flowList.add(flow);
	}
}
