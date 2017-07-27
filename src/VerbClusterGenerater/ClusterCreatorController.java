package VerbClusterGenerater;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.plaf.synth.SynthSeparatorUI;

import Entity.Sentence;
import Entity.VerbCluster;
import Entity.VerbFrequency;
import MySQLDataAccess.VerbFrequencyAccessor;
import ToolSettings.Thresholds;
import WordDistanceCalculator.Distance;
import WordDistanceCalculator.JAWSController;
import WordDistanceCalculator.VerbDistanceList;

public class ClusterCreatorController {
	 
	private static HashSet<Distance> makeDistanceSet(HashSet<String> verbs) {
		
		JAWSController calc = JAWSController.getController();
		
		HashSet<Distance> retSet = new HashSet<Distance>();

		ArrayList<String> twinList = new ArrayList<String>(verbs);
		
		for(String v:verbs)
		{
			for(String t : twinList)
			{
				if(v.equals(t))
					continue;
				else if(retSet.contains(new Distance(v,t)))
					continue;
				else
				{
					double score = calc.getDistance(v, t);
					retSet.add(new Distance(v,t,score));
					//System.out.println("get similarity of "+v+","+t+":"+score);
				}
			}
			
			System.gc();
			
		}
		
		return retSet;
	}
	
	public static HashMap<String,String> createSynonymBasedCluster(HashSet<String> verbSet, HashMap<String, Double> freqMap)
	{
		ArrayList<String> minorVerbs = new ArrayList<String>();
		ArrayList<String> majorVerbs = new ArrayList<String>();
		
		//classify verbs by frequency
		for(String s:verbSet)
		{
			double freq = freqMap.get(s);
			if(freq>Thresholds.Clusteing_High_Freq_Criteria)
			{
				majorVerbs.add(s);
				System.out.println(s);
			}
			else
				minorVerbs.add(s);
		}
		
		ClusterInventory ci = new ClusterInventory(majorVerbs);
		
		for(String s1 : majorVerbs)
		{
			for(String s2 : majorVerbs)
			{
				if(s1.equals(s2))
						continue;
				else
				{
					boolean isSyn = JAWSController.getController().isSynonym(s1, s2, Thresholds.Word_Min_Frequency_On_Similarity);
					
					if(isSyn)
					{
						System.out.println(s1 + "-"+s2);

						Cluster c1 = ci.getClusterFromString(s1);
						Cluster c2 = ci.getClusterFromString(s2);
						
						if(c1 == null || c1 == null)
						{
							c1 = ci.getClusterFromContainedString(s1);
							c2 = ci.getClusterFromContainedString(s2);
						}
						
						if(c1 != c2)
						{
							ci.add(ci.mergeCluster(c1, c2, 0, 0));
							
							ci.deleteCluster(c1);
							ci.deleteCluster(c2);
						}
					}
					
				}
			}
		}
		//Add Minors into closest cluster
		JAWSController con = JAWSController.getController();
		for(String v : minorVerbs)
		{
			double closestDist = Double.MAX_VALUE;
			Cluster closestCluster = null;
			
			for(Cluster c:ci)
			{
				double totalDist = 0.0;
				for(String cv : c.getVerbList())
				{
					totalDist = totalDist + con.getDistance(cv, v);
				}
				double avrDist = totalDist/c.getVerbList().size();
				
				if(closestDist>avrDist)
				{
					closestDist = avrDist;
					closestCluster = c;
				}
			}
			//System.out.println(closestCluster.toString()+":"+v);
			closestCluster.getVerbList().add(v);
		}
		
		
		
		HashMap<String,String> clusterMap = new HashMap<String,String>();
		for(Cluster c:ci)
		{
			String representive = c.getVerbList().get(0);
			String verbs = "";
			//System.out.println("==");
			for(String s : c.getVerbList())
			{
				if(verbs.equals(""))
					verbs = verbs + s;
				else
					verbs = verbs + ";"+s;
			}
			
			clusterMap.put(representive,verbs);

		}
		

		
		
		return clusterMap;
	}

	public static HashMap<String, String> createDistanceBasedCluster(HashSet<String> verbSet)
	{
		HashSet<Distance> verbDistSet= new HashSet<Distance>();
		
		verbDistSet = makeDistanceSet(verbSet);
		VerbDistanceList list = new VerbDistanceList();
		list.makeDistanceList(verbDistSet);
		
		/*System.out.println("==========USER");

		double max = 9.0;
		
		for(Distance d : userDistList)
		{
			if(d.distance <= max)
				System.out.println(d);
		}
		
		System.out.println("==========SYSTEM");
		for(Distance d : systemDistList)
		{
			if(d.distance <= max)
				System.out.println(d);
		}*/
		
		//System.out.println(userVerbs);
		//System.out.println(systemVerbs);
		
		VerbClusterCreator userCluster = new VerbClusterCreator();
		ClusterInventory ci = userCluster.makeWardsHierarchicalCluster();
		
		ci.print();
		
		int optLevel = getOptimalClusterLevelByRSquare(ci);
		
		//System.out.println("------ Level : "+minLevel+"------");
		ArrayList<Cluster> minCluster = ci.getItem(optLevel);
		
		HashMap<String,String> clusterMap = new HashMap<String,String>(); 
		
		for(Cluster c:minCluster)
		{
			String representive = getRepresentiveVerb(c);
			String verbs = "";
			//System.out.println("==");
			for(String s : c.getVerbList())
			{
				if(verbs.equals(""))
					verbs = verbs + s;
				else
					verbs = verbs + ";"+s;
			}
			
			clusterMap.put(representive,verbs);

		}
		//VerbCluster systemCluster = new VerbCluster(systemDistList);
		//systemCluster.makeWardsHierarchicalCluster();
		
		return clusterMap;
	}
	
	private static int getOptimalClusterLevelByRSquare(ClusterInventory ci)
	{
		
		ArrayList<Double> R_SquareList = new ArrayList<Double>(); 
		
		int i = 0;
		
		System.out.println("R_Square; incresed; growth");

		while(true)
		{
			double SSB = ci.getSSB(i);
			double SSW = 0.0;
			ArrayList<Cluster> levelCluster = ci.getItem(i);
			if(levelCluster.size() == 0)
				break;
			
			for(Cluster c:levelCluster)
			{
				SSW = SSW+c.getSumSquare();
			}
			
			double R_Square = SSB / (SSB + SSW);
			R_SquareList.add(R_Square);
			
			double increseOfR_Square = 0.0;
			double growthOfR_Square = 0.0;
			if(i > 0)
			{//	System.out.println(R_SquareList.get(i-1)+" - "+R_SquareList.get(i));
				increseOfR_Square = R_SquareList.get(i-1) - R_SquareList.get(i);
			}
			
			if(i > 1)
				growthOfR_Square = (R_SquareList.get(i-1) - R_SquareList.get(i)) / (R_SquareList.get(i-2) - R_SquareList.get(i-1));
			
			if(growthOfR_Square>2 && increseOfR_Square>0.05)
			{
				System.out.println("Optimal Level : " + (i-2));
				return i-2;
			}
			
			System.out.print("LV"+i+">>");
			System.out.println( R_Square + ";" + increseOfR_Square + ";" + growthOfR_Square);
			
			i++;
		}
		
		/*for(int l = 0 ;l<R_SquareList.size()-1 ; l++)
		{
			System.out.println(l+"-"+(l+1)+"="+(R_SquareList.get(l) - R_SquareList.get(l+1)));
		}*/
		
		return 0;
	}
	
	private static int getOptimalClusterLevelByCustom(ClusterInventory ci)
	{
		double minATSS = 10000.0;
		int minLevel = 0;
		int i = 0;

		while(true)
		{
			double ss = 0.0;
			//System.out.println("----------------Level : "+i);
			ArrayList<Cluster> levelCluster = ci.getItem(i);
			if(levelCluster.size() == 0)
				break;
			
			for(Cluster c:levelCluster)
			{
				for(String s : c.getVerbList())
					System.out.println(s);
				//System.out.println(c.getSumSquare());
				ss = ss+c.getSumSquare();
			}
			
			double atss = ss / (double) levelCluster.size();
			//System.out.println("average total ss :"+atss);
			
			if(minATSS > atss)
			{
				minATSS = atss;
				minLevel = i;
			}
			
			i++;
		}
		
		return minLevel;
	}
	
	private static String getRepresentiveVerb(Cluster c)
	{
		double minDist = 1000.0;
		String repCandidate = c.getVerbList().get(0);
		for(String v1 : c.getVerbList())
		{
			double sumDist = 0.0;
			for(String v2 : c.getVerbList())
			{
				if(v1.equals(v2))
					continue;
				else
					sumDist = sumDist + JAWSController.getController().getDistance(v1, v2);
			}
			if(minDist>sumDist)
			{
				minDist = sumDist;
				repCandidate = v1;
			}
		}
		return repCandidate;
	}
	
	public static HashMap<String, String> createDicBasedCluster(ArrayList<VerbCluster> base, HashSet<String> verbs, HashMap<String, Double> freqMap, String subjectType) {
		//1. Remove cluster that not occur in verbs
		ArrayList<VerbCluster> notOccured = new ArrayList<VerbCluster>(); 
		for(VerbCluster vc :base)
		{
			boolean occured = false;
			for(String v : verbs)
			{
				if(vc.getVerbList().contains(v)){
					occured = true;
					break;
				}
			}
			if(!occured)
			{
				//System.out.println("Not occured "+ vc.getRepresentives());
				notOccured.add(vc);
			}
		}
		base.removeAll(notOccured);
		
		//2.Synonym and similar verbs are added in the Dic.
		ArrayList<String> clusteredVerbs = new ArrayList<String>(); 
		for(String v : verbs)
		{
			//pass if the verb is in dic
			if(isInDictionary(base,v)){
				//System.out.println(v+" is in Dic.");
				clusteredVerbs.add(v);
				continue;
			}

			//System.out.println("Considering : "+v);
			for(VerbCluster vc :base)
			{
				for(String dicItem : vc.getVerbList())
				{
					//SYNONYM
					if(JAWSController.getController().isSynonym(v, dicItem,0.2)){
					//	System.out.println(dicItem+":"+v+" are synonym");
						clusteredVerbs.add(v);
						vc.addVerb(v);
						break;
					}
					//DISTANCE
					double dist = JAWSController.getController().getDistance(v, dicItem);
					if( dist < Thresholds.Word_Max_Distance)
					{
						clusteredVerbs.add(v);
						vc.addVerb(v);
						System.out.println(dicItem+":"+v+" are similar ("+dist+")");
						break;
					}
				}
			}
			
		}
		
		//remove clustered verbs
		for(String cedv : clusteredVerbs)
		{
			verbs.remove(cedv);
		}
		
		//Print log
		if(subjectType.equals("u"))
		{
			//System.out.println("=== User verb Clustering ===");
		}
		if(subjectType.equals("s"))
		{
			//System.out.println("=== System verb Clustering ===");
		}
		//3. Create new cluster if the distance is in threshold. 
		HashSet<Distance> verbDistSet= new HashSet<Distance>();
		verbDistSet = makeDistanceSet(verbs);
		for(Distance d : verbDistSet)
		{
			//If the verb has high frequency and can be a one cluster
			if(d.distance < Thresholds.Word_Max_Distance){
				System.out.println("Find similar verb pair:"+d.toString());
				//not occured this time.
			}
		}

		ArrayList<String> highFreqVerbs = new ArrayList<String>();
		ArrayList<String> lowFreqVerbs = new ArrayList<String>();
		
		for(String v : verbs)
		{
			if(freqMap.get(v) > Thresholds.Clusteing_High_Freq_Criteria){
				//System.out.println(v+" is high");
				highFreqVerbs.add(v);
			}
			else{
				//System.out.println(v+" is low");
				lowFreqVerbs.add(v);
			}
			
		}
		
		//print frequency
//		System.out.println(subjectType+"High Freq.");
//		for(String s:highFreqVerbs)
//			System.out.print(s+",");
//		System.out.println(subjectType+"Low Freq.");
//		for(String s:lowFreqVerbs)
//			System.out.println(s+",");
		
		//High frequency is be a cluster
		for(String hv : highFreqVerbs)
		{
			VerbCluster c = new VerbCluster(hv, hv, "0", subjectType);
			base.add(c);
		}
		
		//Low one has to find similar cluster(
		for(String lv : lowFreqVerbs){
			//System.out.print(lv+"-->");
			getSimilarVerbByMax(base,lv).addVerb(lv);
		}
		
		/*for(String key : freqMap.keySet())
		{
			//System.out.println(vq.getVerb()+":"+vq.getFrequency());
			if(freqMap.get(key) > Thresholds.Verb_Occurence_Threshold && !(verbs.contains(key)) && !(isInDictionary(base,key)))
			{
				System.out.println(key+" is high"+verbs.contains(key));
				highFreqVerbs.add(key);
			}
			else
			{
				//System.out.println(key+" is low");
			}
		}*/
		
		HashMap<String,String> clusterMap = new HashMap<String,String>();
		for(VerbCluster c:base)
		{
			clusterMap.put(c.getRepresentives(),c.getVerbs());
		}
		
		return clusterMap;
	}
	
	private static VerbCluster getSimilarVerbByAverage(ArrayList<VerbCluster> cluster, String verb){
		double closestDist = Double.MAX_VALUE;
		VerbCluster closestCluster = null;
		
		JAWSController con = JAWSController.getController();
		
		for(VerbCluster c:cluster)
		{
			double totalDist = 0.0;
			for(String cv : c.getVerbList())
			{
				totalDist = totalDist + con.getDistance(cv, verb);
			}
			double avrDist = totalDist/c.getVerbList().size();
			
			if(closestDist>avrDist)
			{
				closestDist = avrDist;
				closestCluster = c;
			}
		}
		
		return closestCluster;
	}
	
	private static VerbCluster getSimilarVerbByMax(ArrayList<VerbCluster> cluster, String verb){
		double closestDist = Double.MAX_VALUE;
		VerbCluster closestCluster = null;
		
		JAWSController con = JAWSController.getController();
		
		for(VerbCluster c:cluster)
		{
			double minDist = Double.MAX_VALUE;
			for(String cv : c.getVerbList())
			{
				double dis = con.getDistance(cv, verb);
				if(minDist>dis){
					minDist = dis;
				}
			}
			
			if(closestDist>minDist)
			{
				closestDist = minDist;
				closestCluster = c;
			}
		}
		
		return closestCluster;
	}
	

	private static ArrayList<VerbFrequency> makeVerbFrequency(HashSet<String> verbs) {
		int totalCount = verbs.size();
		
		ArrayList<VerbFrequency> freqMap = new ArrayList<VerbFrequency>();
		
		for(String s : verbs)
		{
			VerbFrequency vf;
			vf = new VerbFrequency(s, totalCount);
			
			if(freqMap.contains(vf))
			{
				for(VerbFrequency f : freqMap)
				{
					if(f.equals(vf))
						f.addCount();
				}
			}
			else
			{
				freqMap.add(vf);
			}
		}
		//VerbFrequencyAccessor vfa = new VerbFrequencyAccessor();
		//vfa.createFrequencyTable(freqMap);
		return freqMap;
	}

	private static boolean isInDictionary(ArrayList<VerbCluster> base, String v) {
		for(VerbCluster vc : base)
		{
			if(vc.getVerbList().contains(v))
				return true;
		}
		return false;
	}
}	

