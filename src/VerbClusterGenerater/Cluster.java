package VerbClusterGenerater;

import java.util.ArrayList;
import java.util.Collections;

public class Cluster {
	
	private ArrayList<String> verbList;
	private boolean isClustered;
	private int createdPhase;
	private double SumSquare;
	private double avrDistanceBeforeClustered;
	
	
	public Cluster(int phase)
	{
		verbList = new ArrayList<String>();
		isClustered = false;
		this.createdPhase = phase;
		SumSquare = 0.0;
		avrDistanceBeforeClustered = 0.0;
	}
	
	public Cluster(String verb)
	{
		verbList = new ArrayList<String>();
		verbList.add(verb);
	}
	
	
	public double getAvrDistanceBeforeClustered() {
		return avrDistanceBeforeClustered;
	}

	public void setAvrDistanceBeforeClustered(double avrDistanceBeforeClustered) {
		this.avrDistanceBeforeClustered = avrDistanceBeforeClustered;
	}





	public double getSumSquare() {
		return SumSquare;
	}
	
	public int getPhase()
	{
		return this.createdPhase;
	}



	public void setSumSquare(double sumSquare) {
		SumSquare = sumSquare;
	}



	public ArrayList<String> getVerbList() {
		return verbList;
	}

	public boolean isClustered() {
		return isClustered;
	}

	public void setClustered(boolean isClustered) {
		this.isClustered = isClustered;
	}

	public Cluster(int phase, String d)
	{
		this(phase);
		this.verbList.add(d);
	}
	
	public String get(int i)
	{
		return this.verbList.get(i);
	}
	
	public void addVerb(String v)
	{
		this.verbList.add(v);
		Collections.sort(this.verbList);
	}
	
	@Override
	public String toString()
	{
		Collections.sort(this.verbList);
		String ss = "";
		for(int i=0;i<this.verbList.size();i++)
		{
			String s = this.verbList.get(i);
			if(i==0)
				ss = s;
			else
				ss = ss + "," + s;
		}
		return ss;
	}

}
