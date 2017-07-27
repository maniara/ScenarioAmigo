package MissedActionFinder;

import java.util.ArrayList;

import Entity.InteractionString;

public class PatternPathRoadList extends ArrayList<PatternPathRoad>{
	private int from;
	private int to;
	private double totalweight;
	private boolean visited;
	
	public PatternPathRoadList(int from, int to)
	{
		this.from = from;
		this.to = to;
		this.totalweight = 0.0;
		this.visited = false;
	}
	
	public PatternPathRoadList(int from, int to, PatternPathRoad is)
	{
		this(from,to);
		this.add(is);
		this.totalweight = is.getWeight();
	}
	
	public void setTotalWeight(double w)
	{
		this.totalweight = w;
	}
	
	public void addWeight(double w)
	{
		this.totalweight = this.totalweight + w;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public double getTotalWeight() {
		return totalweight;
	}

}
