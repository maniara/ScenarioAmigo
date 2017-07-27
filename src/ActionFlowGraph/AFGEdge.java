package ActionFlowGraph;

import ToolSettings.Thresholds;

public class AFGEdge {
	private AFGNode fromNode;
	private AFGNode toNode;
	private int occurCount;
	private double weight;
	private double RelatedInterconectivity;
	private double OccurrencesByMax;
	
	public AFGEdge()
	{
		occurCount = 1;
	}
	
	public AFGEdge(AFGNode from, AFGNode to)
	{
		this();
		this.fromNode = from;
		this.toNode = to;
	}
	
	public boolean equals(AFGNode from, AFGNode to)
	{
		if(this.fromNode.equals(from) && this.toNode.equals(to))
			return true;
		else
			return false;
	}
	
	public void addOccurCount()
	{
		this.occurCount++;
	}
	
	public String getIdString()
	{
		String s = "";
		s=s+this.fromNode.toString()+"->";
		s=s+this.toNode.toString();
		return s;
	}
	
	@Override
	public String toString()
	{
		/*double nomalizedWeight = this.getRelatedInterconectivity();
		return ((double) Math.round(nomalizedWeight*1000)/1000) + "";*/
		
		double nomalizedWeight = this.getWeight();
		return ((double) Math.round(nomalizedWeight*1000)/1000) + "";
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public AFGNode getFromNode() {
		return fromNode;
	}

	public AFGNode getToNode() {
		return toNode;
	}

	public int getOccurCount() {
		return occurCount;
	}
	
	

	public double getRelatedInterconectivity() {
		return RelatedInterconectivity;
	}

	public double getOccurrencesByMax() {
		return OccurrencesByMax;
	}

	public void calculateWeight(int maxOccurs) {
		OccurrencesByMax = (double) this.occurCount/ (double) maxOccurs;
		RelatedInterconectivity = 0.0;
		RelatedInterconectivity = (double) ((double) this.occurCount/(((double) this.fromNode.getOutCount() + (double) this.toNode.getInCount())/2));
		RelatedInterconectivity = Math.round(RelatedInterconectivity*100d)/100d;
		
		OccurrencesByMax = Math.round(OccurrencesByMax*100d)/100d;
		this.weight = Thresholds.RI_Share * RelatedInterconectivity + Thresholds.FrequencyFactor_Share * OccurrencesByMax;
		this.weight = Math.round(weight*100d)/100d;
	}
}


