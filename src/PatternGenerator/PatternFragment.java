package PatternGenerator;

import java.util.ArrayList;

public class PatternFragment  implements Comparable<PatternFragment>{
	private ArrayList<String> prevRepVerbs;
	private String nextVerb;
	private int prevOccrCount;
	private int conditionalOccrCount; //number of verb occurrence the next of prevVerbs
	private double averageGraphWeight;
	private double totalGraphWeight;
	private double totalRI;
	private double averageRI;
	private double sizeFactor;
	private double countFactor;
	private double adjustedWeight;
	private String representVerb;
	
	public PatternFragment()
	{
		prevRepVerbs = new ArrayList<String>();
		prevOccrCount = 0;
		conditionalOccrCount = 0;
		totalGraphWeight= 0;
	}
	
	public PatternFragment(ArrayList<String> verbStringList)
	{
		this.representVerb = verbStringList.get(verbStringList.size()-1);
		verbStringList.remove(verbStringList.size()-1);
		this.prevRepVerbs = verbStringList;
	}
	
	public PatternFragment(String verbString)
	{
		this();
		prevRepVerbs.add(verbString);
		this.prevOccrCount++;
	}
	
	public PatternFragment(String verbList, double weight)
	{
		this();
		String[] sl = verbList.split("-");
		for(int i=0;i<sl.length;i++)
		{
			String s = sl[i];
			
			if(sl.length-1 == i)
				this.setNextVerb(s);
			else
				this.prevRepVerbs.add(s);
		}
		
		this.adjustedWeight= weight;
	}
	
	public double getSizeFactor() {
		return sizeFactor;
	}

	public void setSizeFactor(double sizeFactor) {
		this.sizeFactor = sizeFactor;
	}

	public double getCountFactor() {
		return countFactor;
	}

	public void setCountFactor(double countFactor) {
		this.countFactor = countFactor;
	}

	public double getAdjustedWeight() {
		return adjustedWeight;
	}

	public void setAdjustedWeight(double adjustedWeight) {
		this.adjustedWeight = adjustedWeight;
	}

	public double getTotalRI() {
		return (double) Math.round(totalRI*1000) / 1000;
	}

	public void setTotalRI(double totalRI) {
		this.totalRI = totalRI;
		calculateAverageRI();
	}

	public void setAverageRI(double averageRI) {
		this.averageRI = averageRI;
	}

	public double getConditionalProb()
	{
		return (double) ((double) conditionalOccrCount / (double) prevOccrCount);
	}

	public ArrayList<String> getPrevRepVerbs() {
		return prevRepVerbs;
	}
	
	public void setPrevRepVerbs(ArrayList<String> prevRepVerbs) {
		ArrayList<String> list = new ArrayList<String>(prevRepVerbs);
		this.prevRepVerbs = list;
	}

	public String getNextVerb() {
		return nextVerb;
	}

	public void setNextVerb(String nextVerb) {
		this.nextVerb = nextVerb;
	}

	public int getPrevOccrCount() {
		return prevOccrCount;
	}

	public void setPrevOccrCount(int prevOccrCount) {
		this.prevOccrCount = prevOccrCount;
	}
	
	public double getDistributionInLength()
	{
		return ((double) this.getConditionalOccrCount()) - PatternLenFreqMap.map.get(this.getVerbList().size());
	}

	public int getConditionalOccrCount() {
		return conditionalOccrCount;
	}
	
	public void addConditionalOccrCount()
	{
		conditionalOccrCount = conditionalOccrCount + 1;
	}

	public void setConditionalOccrCount(int conditionalOccrCount) {
		this.conditionalOccrCount = conditionalOccrCount;
	}
	
	@Override
	public String toString()
	{
		String str = this.prevRepVerbs.get(0);
		
		if(this.prevRepVerbs.size() > 1)		
		{
			for(int i = 1;i<this.prevRepVerbs.size();i++)
			{
				str = str + "-" + this.prevRepVerbs.get(i);
			}
		}
		
		if(this.nextVerb != null)
		{
			str = str + "-" + this.nextVerb;
		}
		
		return str;
	}
	
	@Override
	public boolean equals(Object o)
	{
		PatternFragment pf = (PatternFragment) o;
		
		if(pf.toString().equals(this.toString()))
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	public ArrayList<String> getVerbList()
	{
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(this.getPrevRepVerbs());
		ret.add(this.nextVerb);
		
		return ret;
	}
	
	public double getAverageRI()
	{
		return (double) Math.round(this.averageRI*1000) / 1000;
	}

	public double getAverageWeightFromGraph() {
		return averageGraphWeight;
	}


	public double getTotalWeight() {
		return totalGraphWeight;
	}
	
	public void setTotalWeight(double totalWeight) {
		this.totalGraphWeight = totalWeight;
		calculateAverageGraphWeight();
	}

	public void addTotalWeight(double weight)
	{
		this.totalGraphWeight = this.totalGraphWeight + weight;
		calculateAverageGraphWeight();
	}
	
	public void calculateAverageGraphWeight()
	{
		this.averageGraphWeight = this.getTotalWeight()/(this.getVerbList().size()-1);
	}
	
	public void calculateAverageRI()
	{
		this.averageRI = this.getTotalRI()/(this.getVerbList().size()-1);
	}
	
	public double getWeightBySigmoidFunction(double count, double avgSize)
	{
		double x = count;
		double jisu = (double) -1*(x - avgSize);
		
		double exp = (double) Math.exp(jisu);
		
		double sw = (double) 1/(1+exp);
		
		return (double) Math.round(sw*1000)/1000;
	}
	
	public double getWeightByArcTan(double count, double avgSize)
	{
		double x = count;
		double atan = Math.atan(x - avgSize);
		double sw = (atan/3) + 0.5;
		return (double) Math.round(sw*1000)/1000;
	}
	
	public double getWeightBySoftsign(double count, double avgSize)
	{
		double x = count - avgSize;
		double abx = Math.abs(x);
		double sw = (x/(1+abx))/2+0.5;
		return (double) Math.round(sw*1000)/1000;
		
	}

	public String getVerb(int level)
	{
		if(level < this.getVerbList().size())
		{
			return this.getVerbList().get(level);
		}
		else
			return null;
	}

	@Override
	public int compareTo(PatternFragment o) {
		return this.toString().compareTo(o.toString());
	}

	public String getRepresentVerb() {
		return representVerb;
	}

	public void setRepresentVerb(String representVerb) {
		this.representVerb = representVerb;
	}
	
	
}
