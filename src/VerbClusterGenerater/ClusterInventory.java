package VerbClusterGenerater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import WordDistanceCalculator.JAWSController;
import WordDistanceCalculator.VerbDistanceList;

public class ClusterInventory extends ArrayList<Cluster>{
	
	public ClusterInventory()
	{}
	
	public ClusterInventory(HashSet<String> verbSet)
	{
		for(String v : verbSet)
		{
			this.add(new Cluster(v));
		}
	
	}
	
	public ClusterInventory(ArrayList<String> verbSet)
	{
		for(String v : verbSet)
		{
			this.add(new Cluster(v));
		}
	
	}
	
	public void deleteCluster(String clusterString)
	{
		for(Cluster c: this)
		{
			if(c.toString().equals(clusterString))
				this.remove(c);
		}
	}
	
	public void deleteCluster(Cluster c)
	{
		this.remove(c);
	}
	
	public Cluster getClusterFromString(String clusterString)
	{
		for(Cluster c: this)
		{
			if(c.toString().equals(clusterString))
				return c;
		}
		return null;
	}
	
	public Cluster getClusterFromContainedString(String clusterString)
	{
		for(Cluster c: this)
		{
			if(c.toString().contains(clusterString))
				return c;
		}
		return null;
	}
	
	public ArrayList<Cluster> getItem(int level)
	{
		ArrayList<Cluster> ret = new ArrayList<Cluster>();
		
		for(Cluster c : this)
		{
			if(c.getPhase() == level)
				ret.add(c);
		}
		
		return ret;
	}
	
	//public static void main(String args[])
	public double getSSB(int level)
	{
		double ssw = 0.0;
		
		ArrayList<Cluster> clusters = this.getItem(level);
		
		int size = 5;
		for(int i=0;i<clusters.size()-1;i++)
		{
			for(int j=i+1;j<clusters.size();j++)
			{
				ssw = ssw + getSumOfSquare(clusters.get(i),clusters.get(j));
			}
		}
		
		return ssw;
	}
	
	public static double getSumOfSquare(Cluster c1, Cluster c2)
	{
		double distance = 0.0;
		
		ArrayList<String> members = new ArrayList<String>();
		ArrayList<String> forDupCheck = new ArrayList<String>(); 
		
		for(String v1 : c1.getVerbList())
		{
			members.add(v1);
		}
		for(String v2 : c2.getVerbList())
		{
			members.add(v2);
		}
		
		ArrayList<String> tempTargets = members;
		
		for(String s1 : members)
		{
			for(String s2 : tempTargets)
			{
				if(s1.equals(s2))
				{
					continue;
				}
				
				else if(forDupCheck.contains(s1+s2) || forDupCheck.contains(s2+s1))
				{
					continue;
				}
				else
				{	
					double d = VerbDistanceList.getDistance(s1, s2);
					distance = distance + (d*d);
					forDupCheck.add(s1+s2);
				}
				
			}
		}
		
		return distance/members.size();
	}
	
	public void print()
	{
		int level = 0;
		
		while(true)
		{
			ArrayList<Cluster> cs = this.getItem(level);
			
			if(cs == null || cs.size() == 0)
				break;

			System.out.print("LV" + level + " >>");

			ArrayList<Cluster> levelList = this.getItem(level);
			for(int i=0;i<levelList.size();i++)
			{
				Cluster c = levelList.get(i);
				//System.out.print("("+c.toString()+");");
				
				if(c.equals(levelList.get(levelList.size()-1))){
					System.out.print("("+c.toString()+");");
					System.out.print("-"+c.getSumSquare()+"-"+c.getAvrDistanceBeforeClustered());
				}
			}
			
			System.out.println();
			
			level++;
		}
	}
	
	public Cluster mergeCluster(Cluster c1, Cluster c2, int phase, double ss)
	{
		//System.out.println("merging "+c1.getVerbList().get(0)+"-"+c2.getVerbList().get(0));
		for(Cluster c : this)
		{
			if(c.equals(c1) || c.equals(c2))
			{ 
				continue;
			}
			else
			{
				
			}
		}
		
		Cluster cl = new Cluster(phase);
		cl.setSumSquare(ss);
		
		for(String s:c1.getVerbList())
		{
			cl.getVerbList().add(s);
		}
		for(String s:c2.getVerbList())
		{
			cl.getVerbList().add(s);
		}
		
		cl.setAvrDistanceBeforeClustered(getAvrDistance(c1,c2));
		
		c1.setClustered(true);
		c2.setClustered(true);
		
		return cl;
	}
	
	private double getAvrDistance(Cluster c1, Cluster c2)
	{
		JAWSController controller = JAWSController.getController();
		
		int count = 0;
		double sum = 0.0;
		for(String v1 : c1.getVerbList())
		{
			for(String v2 : c2.getVerbList())
			{
				count++;
				sum = sum + controller.getDistance(v1, v2);
			}
		}
		
		return sum / count;
	}

}
