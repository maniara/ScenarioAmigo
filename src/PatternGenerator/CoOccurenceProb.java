package PatternGenerator;

public class CoOccurenceProb {
	
	public String verb1;
	public String verb2;
	public double coOccurProb;
	
	public CoOccurenceProb(String verb1, String verb2) {
		super();
		this.verb1 = verb1;
		this.verb2 = verb2;
	}
	
	public String getVerb1() {
		return verb1;
	}
	public void setVerb1(String verb1) {
		this.verb1 = verb1;
	}
	public String getVerb2() {
		return verb2;
	}
	public void setVerb2(String verb2) {
		this.verb2 = verb2;
	}
	public double getCoOccurProb() {
		return coOccurProb;
	}
	public void setCoOccurProb(double coOccurProb) {
		this.coOccurProb = coOccurProb;
	}
	
	public String toString()
	{
		return verb1+"|"+verb2;
	}
	
	@Override
	public boolean equals(Object o)
	{
		CoOccurenceProb cop = (CoOccurenceProb) o;
		if(cop.toString().equals(this.toString()))
			return true;
		else if((cop.verb2+"|"+cop.verb1).equals(this.toString()))
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.verb1.hashCode() + this.verb2.hashCode();
	}
}
