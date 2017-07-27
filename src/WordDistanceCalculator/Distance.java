package WordDistanceCalculator;

import java.util.ArrayList;

public class Distance implements Comparable<Distance>{
	public String verb1;
	public String verb2;
	public double distance;
	
	public Distance(String verb1, String verb2, double distance) {
		super();
		this.verb1 = verb1;
		this.verb2 = verb2;
		this.distance = distance;
	}
	public Distance(String verb1, String verb2)
	{
		this.verb1 = verb1;
		this.verb2 = verb2;
	}
	
	public String toString()
	{
		return verb1+":"+verb2+":"+distance;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Distance d = (Distance) o;
		ArrayList<String> verbs = new ArrayList<String>();
		verbs.add(verb1);
		verbs.add(verb2);
		
		if(verbs.contains(d.verb1) && verbs.contains(d.verb2))
			return true;
		else 
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return verb1.length()+verb2.length();
	}
	
	@Override
	public int compareTo(Distance d) {
		if(this.distance < d.distance)
			return -1;
		else if(this.distance == d.distance)
			return 0;
		else 
			return 1;
	}
}
