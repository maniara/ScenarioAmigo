package ActionFlowGraph;

public class AFGNode {
	private String subjectType;
	private String repVerb;
	
	private int inCount;
	private int outCount;
	private int occurCount;
	
	public AFGNode()
	{
		this.inCount = 0;
		this.outCount = 0;
		this.occurCount = 1;
	}
	
	public AFGNode(String type, String verb)
	{
		this();
		this.subjectType = type;
		this.repVerb  = verb;
	}
	
	public boolean equals(String type, String verb)
	{
		return this.getIdentifyString().equals(type+":"+verb);
	}
	
	public boolean equals(AFGNode node)
	{
		if(this.toString().equals(node.getIdentifyString()))
			return true;
		else
			return false;
	}
	
	public String getIdentifyString()
	{
		return subjectType+":"+repVerb;
	}
	
	public String toString()
	{
		return subjectType+":"+repVerb;
	}
	
	public void addOccurCount()
	{
		this.occurCount++;
	}
	
	public void addInCount()
	{
		this.inCount++;
	}
	
	public void addOutCount()
	{
		this.outCount++;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getRepVerb() {
		return repVerb;
	}

	public void setRepVerb(String repVerb) {
		this.repVerb = repVerb;
	}

	public int getInCount() {
		return inCount;
	}

	public int getOutCount() {
		return outCount;
	}

	public int getOccurCount() {
		return occurCount;
	}
	
	
}
