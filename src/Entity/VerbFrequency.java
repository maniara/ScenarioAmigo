package Entity;

public class VerbFrequency {
	private String verb;
	private String type;
	private int count;
	private int total;
	private double frequency;
	
	public VerbFrequency(String verb, String type, int total) {
		super();
		this.verb = verb;
		this.type = type;
		this.count = 1;
		this.total = total;
		this.frequency = (double) 1/total;
	}
	
	public VerbFrequency(String verb, int total)
	{
		super();
		this.verb = verb;
		this.count = 1;
		this.total = total;
		this.frequency = (double) 1/total;
	}
	
	
	
	public VerbFrequency(String verb, String type, int count, int total,
			double frequency) {
		super();
		this.verb = verb;
		this.type = type;
		this.count = count;
		this.total = total;
		this.frequency = frequency;
	}



	public void addCount()
	{
		this.count++;
		this.frequency = (double) this.count/this.total;
	}

	public String getVerb() {
		return verb;
	}

	public String getType() {
		return type;
	}

	public int getCount() {
		return count;
	}

	public int getTotal() {
		return total;
	}

	public double getFrequency() {
		return frequency;
	}
	
	@Override
	public String toString()
	{
		return this.type+":"+this.verb;
	}
	
	@Override
	public boolean equals(Object o)
	{
		VerbFrequency vf = (VerbFrequency) o;

		if(this.toString().equals(vf.toString()))
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
}
