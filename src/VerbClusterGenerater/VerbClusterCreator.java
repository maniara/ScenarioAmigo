package VerbClusterGenerater;

import java.util.ArrayList;
import java.util.HashMap;
import WordDistanceCalculator.VerbDistanceList;

public class VerbClusterCreator {

	ClusterInventory clusters;
	
	public class ClusterPair
	{
		public Cluster c1;
		public Cluster c2;
		
		public ClusterPair(Cluster c1, Cluster c2)
		{
			this.c1 = c1;
			this.c2 = c2;
		}
		
		@Override
		public boolean equals(Object o)
		{
			ClusterPair cp = (ClusterPair)o;
			
			if((c1.toString()+c2.toString()).equals(cp.c1.toString()+cp.c2.toString()) || (c2.toString()+c1.toString()).equals(cp.c1.toString()+cp.c2.toString()))
			{
				return true;
			}
			else
				return false;
		}
	}
	
	public VerbClusterCreator() {
		this.clusters = new ClusterInventory();
	}

	public ClusterInventory makeWardsHierarchicalCluster()
	{
		makeInitialCluster();
		HashMap<ClusterPair, Double> distanceList;
		
		int it = 0;
		while(getCurrentClusterNumber()>1)
		{
			distanceList = null;
			distanceList = new HashMap<ClusterPair, Double>();
			
			it++;
			ArrayList<Cluster> v1List = getCurrentCluster();
			ArrayList<Cluster> v2List = getCurrentCluster();
			
			for(Cluster v1 : v1List)
			{
				for(Cluster v2 : v2List)
				{
					if(v1.toString().equals(v2.toString()))
						continue;

					double ss = ClusterInventory.getSumOfSquare(v1,v2);
					
					if(ss != 0.0) 
					{
						boolean isInMap = false;
						
						//if the cluster pair is not in map add it into map
						ClusterPair pair = new ClusterPair(v1, v2);
						for(ClusterPair cp : distanceList.keySet())
						{
							if(cp.equals(pair))
							{
								isInMap = true;
								break;
							}
						}
						
						if(isInMap == false)
							distanceList.put(new ClusterPair(v1, v2), ss);
					}
					//System.out.println(v1.verbList.get(0)+":"+v2.verbList.get(0)+":"+getSumOfSquare(v1,v2));
					//System.out.println(v1.get(0)+":"+v2.get(0)+":"+this.verbList.getDistance(v1.get(0), v2.get(0))+":"+ss);
				}
			}
			
			double minDist = Double.MAX_VALUE;
			ClusterPair minCp = null;

			for(ClusterPair cp : distanceList.keySet())
			{
				double dist = distanceList.get(cp);
				if(dist<minDist)
				{
					minCp = cp;
					minDist = dist;
				}
			}
			
			ArrayList<Cluster> remainCluster = shiftNextPhase(minCp.c1, minCp.c2, it);
			Cluster mergedCluster = clusters.mergeCluster(minCp.c1, minCp.c2, it, minDist);
			
			setClustersNextPhase();
			
			clusters.addAll(remainCluster);
			clusters.add(mergedCluster);
		}
		
		return clusters;
	}
	
	private void setClustersNextPhase()
	{
		for(Cluster c : this.clusters)
		{
			c.setClustered(true);
		}
	}
	
	private ArrayList<Cluster> shiftNextPhase(Cluster c1, Cluster c2, int it) 
	{
		ArrayList<Cluster> ret = new ArrayList<Cluster>();
		for(Cluster c : this.clusters)
		{
			if(c.isClustered())
				continue;
			else if(c.equals(c1) || c.equals(c2))
			{
				continue;
			}
			else
			{
				ret.add(copyCluster(c,it));
			}
		}
	
		return ret;
	}
	
	private Cluster copyCluster(Cluster c, int phase)
	{
		Cluster retC = new Cluster(phase);
		
		for(String s : c.getVerbList())
			retC.getVerbList().add(s);
		retC.setSumSquare(c.getSumSquare());
		
		return retC;
	}

	public ArrayList<Cluster> getCurrentCluster()
	{
		ArrayList<Cluster> ret = new ArrayList<Cluster>();
		for(Cluster c : this.clusters)
		{
			if(c.isClustered() == false)
				ret.add(c);
		}
		
		return ret;
	}
	
	public int getCurrentClusterNumber()
	{
		int count = 0;
		for(Cluster c : this.clusters)
		{
			if(c.isClustered() == false)
				count++;
		}
		
		return count;
	}
	

	
	
	private double getMinDistance(Cluster c1, Cluster c2)
	{
		double min = 10.0;
		
		for(String v1 : c1.getVerbList())
		{
			for(String v2 : c2.getVerbList())
			{
				double dist = VerbDistanceList.getDistance(v1,v2);
				if(dist < min)
					min = dist; 
			}
		}
		
		return min;
	}
	
	private void makeInitialCluster()
	{
		for(String v : VerbDistanceList.getVerbList())
		{
			this.clusters.add(new Cluster(0,v));
		}
		
	}
}
