package VerbClusterGenerater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Entity.Sentence;
import Entity.VerbCluster;
import Entity.VerbFrequency;
import MySQLDataAccess.DictionaryAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.VerbClusterAccessor;
import MySQLDataAccess.VerbFrequencyAccessor;

public class Generator {
	
	private HashSet<String> userVerb;
	private HashSet<String> systemVerb;
	
	public Generator(){
		userVerb = new HashSet<String>();
		systemVerb = new HashSet<String>();
	}
	
	public ArrayList<VerbCluster> makeVerbClusterForValidation(String targetProjectId){
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getTrainingSentenceListForValidation(targetProjectId);
		return performGenerator(sentenceList,false);
	}
	public ArrayList<VerbCluster> makeVerbCluster(){
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getAllTrainingSentenceList();
		return performGenerator(sentenceList,true);
	}
	
	private ArrayList<VerbCluster> performGenerator(ArrayList<Sentence> sentenceList, boolean needSave){

		for(Sentence s : sentenceList)
		{			
			if(s.getVerb() == null || s.getVerb().equals(""))
				continue;
			
			if(s.getSentenceType() == 'u')
				userVerb.add(s.getVerb());
			else if
			(s.getSentenceType() == 's')
				systemVerb.add(s.getVerb());
		}
		
		//Create clusters
		ArrayList<VerbFrequency> freqs = makeVerbFrequency(sentenceList);
		
		HashMap<String,Double> userFreqMap = new HashMap<String,Double>();
		HashMap<String,Double> systemFreqMap = new HashMap<String,Double>();
		for(VerbFrequency f : freqs)
		{
			if(f.getType().equals("u"))
				userFreqMap.put(f.getVerb(), f.getFrequency());
			else if(f.getType().equals("s"))
				systemFreqMap.put(f.getVerb(), f.getFrequency());
		}
		
		DictionaryAccessor da = new DictionaryAccessor();
		HashMap<String,String> userVerbClusterMap = ClusterCreatorController.createDicBasedCluster(da.getUserDictionary(),userVerb,userFreqMap,"u");
		HashMap<String,String> systemVerbClusterMap = ClusterCreatorController.createDicBasedCluster(da.getSystemDictionary(),systemVerb,systemFreqMap,"s");
		
		if(needSave){
			VerbClusterAccessor vca = new VerbClusterAccessor();
			for(String key:userVerbClusterMap.keySet())
			{
				String verbs = userVerbClusterMap.get(key);
				vca.addCluster(key, verbs, "u");
			}
			
			for(String key:systemVerbClusterMap.keySet())
			{
				String verbs = systemVerbClusterMap.get(key);
				vca.addCluster(key, verbs, "s");
			}
			return null;
		}
		
		else{
			ArrayList<VerbCluster> clusterList = new ArrayList<VerbCluster>();
			for(String key:userVerbClusterMap.keySet())
			{
				String verbs = userVerbClusterMap.get(key);
				clusterList.add(new VerbCluster(key,verbs,"1","u"));
			}
			
			for(String key:systemVerbClusterMap.keySet())
			{
				String verbs = systemVerbClusterMap.get(key);
				clusterList.add(new VerbCluster(key,verbs,"1","s"));
			}
			return clusterList;
		}
	}

	private ArrayList<VerbFrequency> makeVerbFrequency(ArrayList<Sentence> sentenceList) {
		int userTotalCount = 0;
		int systemTotalCount = 0;
		
		for(Sentence s : sentenceList)
		{
			if(s.getSentenceType()== 'u')
				userTotalCount++;
			if(s.getSentenceType()== 's')
				systemTotalCount++;
		}
		
		ArrayList<VerbFrequency> freqMap = new ArrayList<VerbFrequency>();
		
		for(Sentence s : sentenceList)
		{
			VerbFrequency vf;
			if(s.getSentenceType() == 'u')
				vf = new VerbFrequency(s.getVerb() , s.getSentenceType()+"", userTotalCount);
			else
				vf = new VerbFrequency(s.getVerb() , s.getSentenceType()+"", systemTotalCount);
			
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
		VerbFrequencyAccessor vfa = new VerbFrequencyAccessor();
		vfa.createFrequencyTable(freqMap);
		return freqMap;
	}

}
