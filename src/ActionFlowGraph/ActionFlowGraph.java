package ActionFlowGraph;

import java.util.ArrayList;

public class ActionFlowGraph {
	private ArrayList<AFGNode> nodeList;
	private ArrayList<AFGEdge> edgeList;
	private int maxOccurEdge;
	
	public ActionFlowGraph()
	{
		nodeList = new ArrayList<AFGNode>();
		edgeList = new ArrayList<AFGEdge>();
	}
	
	public boolean isNodeContains(String type, String verb)
	{
		for(AFGNode node : this.nodeList)
		{
			if(node.equals(type, verb))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isEdgeContiains(AFGNode from, AFGNode to)
	{
		for(AFGEdge edge : this.edgeList)
		{
			if(edge.equals(from, to))
				return true;
		}
		
		return false;
	}
	
	public boolean isEdgeContains(String from, String to)
	{
		for(AFGEdge edge : this.edgeList)
		{
			if(edge.getFromNode().toString().equals(from) && edge.getToNode().toString().equals(to))
				return true;
		}
		
		return false;
	}
	
	public void addNode(AFGNode node)
	{
		this.nodeList.add(node);
	}
	
	public void addEdge(AFGEdge edge)
	{
		this.edgeList.add(edge);
	}
	
	public ArrayList<String> getNodeStringList()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(AFGNode node: this.getNodeList())
		{
			list.add(node.toString());
		}
		
		return list;
	}
	
	public ArrayList<String> getEdgeStringList()
	{
		ArrayList<String> list = new ArrayList<String>();
		for(AFGEdge edge: this.getEdgeList())
		{
			list.add(edge.getFromNode().toString());
			list.add(edge.getToNode().toString());
		}
		
		return list;
	}

	public AFGNode getNode(String type, String verb) {
		
		for(AFGNode node : this.nodeList)
		{
			if(node.equals(type,verb))
				return node;
		}
		
		return null;
	}
	
	public AFGEdge getEdge(AFGNode from, AFGNode to)
	{
		for(AFGEdge edge : this.edgeList)
		{
			if(edge.equals(from, to))
				return edge;
		}
		
		return null;
	}
	
	public AFGEdge getEdge(String from, String to)
	{
		for(AFGEdge edge : this.edgeList)
		{
			if(edge.getFromNode().toString().equals(from) && edge.getToNode().toString().equals(to))
				return edge;
		}
		return null;
	}
	
	public void printGraph()
	{
		for(AFGNode node : this.nodeList)
		{
			System.out.println(node.toString());
		}
		for(AFGEdge edge : this.edgeList)
		{
			System.out.println(edge.toString());
		}
	}
	
	public ArrayList<AFGNode> getNodeList() {
		return nodeList;
	}

	public ArrayList<AFGEdge> getEdgeList() {
		return edgeList;
	}

	public int getMaxOccurEdge() {
		return maxOccurEdge;
	}

	public void setMaxOccurEdge(int maxOccurEdge) {
		this.maxOccurEdge = maxOccurEdge;
	}
	
	public void calculateWeight()
	{
		for(AFGEdge edge : this.edgeList)
		{
			edge.calculateWeight(this.maxOccurEdge);
		}
	}
	
	public boolean hasPattern(ArrayList<String> candidate)
	{
		for(int i=0;i<candidate.size();i++)
		{
			if(i+1 < candidate.size())
			{
				if(this.isEdgeContains(candidate.get(i), candidate.get(i+1)))
				{
					continue;
				}
				else
				{
					return false;
				}
			}
		}
		
		return true;
	}

	public double calculateTotalWeight(ArrayList<String> verbList) {
		
		double ret = 0.0;
		for(int i=0;i<verbList.size();i++)
		{
			if(i+1 < verbList.size())
			{
				ret = ret + getEdge(verbList.get(i), verbList.get(i+1)).getWeight();
			}
		}
		return ret;
	}
	
	public double calculateTotalRI(ArrayList<String> verbList) {
		
		double ret = 0.0;
		for(int i=0;i<verbList.size();i++)
		{
			if(i+1 < verbList.size())
			{
				ret = ret + getEdge(verbList.get(i), verbList.get(i+1)).getRelatedInterconectivity();
			}
		}
		return ret;
	}
				
}
