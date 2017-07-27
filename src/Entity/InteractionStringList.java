package Entity;

import java.util.ArrayList;

public class InteractionStringList extends ArrayList<InteractionString>{
	private int from;
	private int to;
	private double totalweight;
	private boolean visited;
	
	public InteractionStringList(int from, int to)
	{
		this.from = from;
		this.to = to;
		this.totalweight = 0.0;
		this.visited = false;
	}
	
	public InteractionStringList(int from, int to, InteractionString is)
	{
		this(from,to);
		this.add(is);
		this.totalweight = is.getReward();
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

	public double getTotalweight() {
		return totalweight;
	}
	
	

}
