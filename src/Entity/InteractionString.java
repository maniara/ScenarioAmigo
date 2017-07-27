package Entity;

import java.util.ArrayList;

public class InteractionString implements Comparable<InteractionString>{
	private String patternID;
	private Integer from;
	private int to;
	private String interactionString;
	private ArrayList<String> sentenceList;
	private float reward;
	
	public String getPatternID()
	{
		return this.patternID;
	}
	public void setPatternID(String id)
	{
		this.patternID = id;
	}
	
	public void setFrom(int f)
	{
		this.from = f;
	}
	public void setTo(int t)
	{
		this.to = t;
	}
	public int getFrom()
	{
		return this.from;
	}
	public int getTo()
	{
		return this.to;
	}
	
	public void setReward(float f)
	{
		this.reward = f;
	}
	public InteractionString()
	{
		interactionString = "";
		sentenceList = new ArrayList<String>();
		reward = (float) 0.0;
	}
	
	public InteractionString(String is)
	{
		interactionString = is;
		sentenceList = new ArrayList<String>();
		reward = (float) 0.0;
	}
	
	public InteractionString(String id, String is, String sentence)
	{
		this.patternID = id;
		interactionString = is;
		sentenceList = new ArrayList<String>();
		sentenceList.add(sentence);
		reward = (float) 0.0;
	}
	
	public InteractionString(String id, String is, ArrayList<String> sentenceList) 
	{
		this.patternID = id;
		interactionString = is;
		this.sentenceList = sentenceList;
		reward = (float) 0.0;
	}

	public void addSentence(String sentence)
	{
		sentenceList.add(sentence);
	}
	
	public void addInteraction(String i)
	{
		interactionString = interactionString + i;
	}
	
	public String getInteractionString()
	{
		return this.interactionString;
	}
	
	public ArrayList<String> getSentenceList()
	{
		return this.sentenceList;
	}
	
	public boolean equals(InteractionString o)
	{
		if((o.getFrom()==this.from) && (o.getTo() == this.to))
			return true;
		else 
			return false;
	}
	
	public String toString()
	{
		return this.from+"-"+this.patternID+"("+this.reward+")"+"-"+this.to;
	}
	
	public float getReward()
	{
		return this.reward;
	}
	
	public void addReward(float point)
	{
		this.reward = this.reward + point;
	}
	@Override
	public int compareTo(InteractionString o) {
		return from.compareTo(o.getFrom());
	}
	
	@Override
	public boolean equals(Object o)
	{
		InteractionString is = (InteractionString) o;
		if(is.toString().equals(this.toString()))
		{
			return true;
		}
		
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
}
