package PatternGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import ActionFlowGraph.ActionFlowGraph;
import Entity.Flow;
import Entity.Sentence;
import Entity.VerbCluster;
import MySQLDataAccess.FlowAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.VerbClusterAccessor;
import TemporalEntities.EntityStorage;
import ToolSettings.Thresholds;

public class PatternGeneratorController {
	

	private HashMap<String, HashSet<String>> flowIdInVerb;
	private ArrayList<CoOccurenceProb> matrix;
	private PatternFragmentSet pfSet;
	private ArrayList<Flow> flowList;
	private ActionFlowGraph graph;
	private HashMap<String, Integer> verbCount;
	
	public PatternGeneratorController(ActionFlowGraph graph)
	{
		this.graph = graph;
	}
	
	public PatternFragmentSet makePatterns(boolean forValidate, String projectId)
	{
		//HashSet<String> repVerbSet = new HashSet<String>(); 
		FlowAccessor fa = new FlowAccessor();
		if(forValidate)
			flowList = fa.getAllBasicFlowListForValidate(projectId);
		else
			flowList = fa.getAllBasicFlowList();
		
		EntityStorage.allFlowList = flowList;
		for(Flow f : flowList){
			if(f.getIsAlternative() == false)
				f.addStartNode();
		}
			
		verbCount = new HashMap<String, Integer>();
		
		VerbClusterAccessor vca = new VerbClusterAccessor();
		ArrayList<VerbCluster> clusters = vca.getAllClusters();
		
		//set the represent verbs
		for(Flow f : flowList)
		{
			for(Sentence sen : f.getSentenceList())
			{
				if(sen.getSentenceSeq() == 0)
					continue;
				for(VerbCluster vc : clusters)
				{
					if(vc.getSubjectType().equals(sen.getSentenceType()+""))
					{
						if(vc.getVerbs().contains(sen.getVerb()))
						{
							sen.setRepresentVerb(vc.getRepresentives());
							sen.setRepresentive(true);
							break;
						}
					}
				}
			}
			

			//System.out.print(f.getFlowID());
			for(Sentence sen : f.getSentenceList())
			{
				if(sen.hasRepresentive())
				{
					String verbString = sen.getSentenceType()+":"+sen.getRepresentVerb();
					
					if(!verbCount.keySet().contains(verbString))
					{
						verbCount.put(verbString, 1);
					}
					else
					{
						verbCount.put(verbString, verbCount.get(verbString)+1);
					}
				}
			}
		}
		
		removeMinorVerb();
		makePatternCandidate();
		filteringByGraph();
		filteringBySize();
		//printPFSet();
		//System.out.println("-----------------------------------");
		//filteringByCount();
		//filteringByWeight();
		filteringDuplicated();
		makeLengthFrequencyMap();

		calculateWeight();
		setRepVerbOfPattern();
		//printPFSet();

		
		/*for(PatternFragment pf : pfSet)
		{
			System.out.println(pf.toString() + "-" + pf.getPrevOccrCount());
		}*/
		
		//makePatternCandidate();
		//System.out.println(pfSet.size());

		return this.pfSet;
	}
	
	private void makeLengthFrequencyMap()
	{
		int totalOccur = 0;
		class LFElement{
			int count;
			int occur;
			
			void add(int occur)
			{
				this.count++;
				this.occur = this.occur+occur;
			}
			
			public LFElement(int occur)
			{
				this.occur = occur;
				this.count = 1;
			}
		}
		
		HashMap<Integer, LFElement> map = new HashMap<Integer, LFElement>();
		
		for(PatternFragment pf : this.pfSet)
		{
			totalOccur = totalOccur + pf.getConditionalOccrCount();
			int len = pf.getVerbList().size();
			
			if(map.containsKey(len)){
				map.get(len).add(pf.getConditionalOccrCount());
			}
			else{
				map.put(len, new LFElement(pf.getConditionalOccrCount()));
			}
		}
		
		PatternLenFreqMap.map = new HashMap<Integer,Double>();
		for(int len : map.keySet())
		{
			LFElement ele = map.get(len);
			PatternLenFreqMap.map.put(len,(double) ele.occur/(double) ele.count);
			
			//System.out.println(len+":"+PatternLenFreqMap.map.get(len));
		}
		
		System.out.println(totalOccur);
	}
	
	private void setRepVerbOfPattern()
	{
		for(PatternFragment pf : this.pfSet)
		{
			String verb = "";
			int min = 1000;
			for(String v : pf.getVerbList())
			{
				if(pfSet.getOccringCount(v) < min)
				{
					min = pfSet.getOccringCount(v);
					verb = v;
				}
			}
			if(min < 3)
			{
				pf.setRepresentVerb(verb);
			}
		}
	}
	
	private void filteringDuplicated()
	{
		// remove contained pattern in same count to other pattern
		ArrayList<PatternFragment> filtered =new ArrayList<PatternFragment>();
		for(PatternFragment pivot : pfSet)
		{
			for(PatternFragment pf : pfSet)
			{
				if(pivot.equals(pf))
					continue;
				if(pivot.toString().contains(pf.toString()))
				{
					if(pivot.getConditionalOccrCount() == pf.getConditionalOccrCount())
					{
						//System.out.println(pivot.toString()+"("+pivot.getConditionalOccrCount()+")"+"/"+pf.toString()+"("+pf.getConditionalOccrCount()+")");
						filtered.add(pf);
					}
				}
			}
		}
		pfSet.removeAll(filtered);
	}
	
	private void calculateWeight()
	{
		for(PatternFragment pf : this.pfSet)
		{
			//getWeightByArcTan
			//getWeightBySigmoidFunction
		    //getWeightBySoftsign
			pf.setCountFactor(pf.getWeightBySigmoidFunction(pf.getDistributionInLength(),0.0));
			pf.setSizeFactor(pf.getWeightByArcTan(pf.getVerbList().size(),pfSet.getAverageSizeOfPattern()));
			
			double totalWeight = 0.0;
			totalWeight = (Thresholds.Weight_Of_PatternWeight_COUNT_AVGRI_LENGHT[0]*pf.getCountFactor()) 
					+ (Thresholds.Weight_Of_PatternWeight_COUNT_AVGRI_LENGHT[1]*pf.getAverageRI()) 
					+ (Thresholds.Weight_Of_PatternWeight_COUNT_AVGRI_LENGHT[2]*pf.getSizeFactor());
			totalWeight = (double) Math.round(totalWeight * 1000)/1000;
			pf.setAdjustedWeight(totalWeight);
		}
	}
	
	private void filteringByCount()
	{
		ArrayList<PatternFragment> filtered =new ArrayList<PatternFragment>();
		for(PatternFragment pf : this.pfSet)
		{
			if(pf.getConditionalOccrCount() < 3)
			{
				filtered.add(pf);
			}
		}
		
		for(PatternFragment pf : filtered)
		{
			this.pfSet.remove(pf);

		}

		
	}
	
	private void filteringBySize() {
		ArrayList<PatternFragment> filtered =new ArrayList<PatternFragment>();
		for(PatternFragment pf : this.pfSet)
		{
			if(pf.getVerbList().size() < 3)
			{
				filtered.add(pf);
			}
		}
		
		for(PatternFragment pf : filtered)
		{
			this.pfSet.remove(pf);
		}
	}
	private void filteringByGraph() {
		ArrayList<PatternFragment> filtered =new ArrayList<PatternFragment>();
		for(PatternFragment pf : this.pfSet)
		{
			if(graph.hasPattern(pf.getVerbList()))
			{
				double weight = graph.calculateTotalWeight(pf.getVerbList());
				double ri = graph.calculateTotalRI(pf.getVerbList());
				pf.setTotalWeight(weight);
				pf.setTotalRI(ri);
				//System.out.println(pf.getVerbList()+" O");
			}
			else
			{
				filtered.add(pf);
				//System.out.println(pf.getVerbList()+" X");
			}
		}
		
		for(PatternFragment pf : filtered)
		{
			this.pfSet.remove(pf);

		}
	}

	private void printPFSet()
	{
		System.out.println("**** Printing FP candidate ****");
		int count = 0;
		for(PatternFragment pf : this.pfSet)
		{
			//System.out.println(pf.toString() + "/"+"/"+pf.getConditionalOccrCount()+"/" + pf.getWeightBySigmoidFunction(pf.getConditionalOccrCount(),pfSet.getAverageOccurenceOfPattern())+ "/" +pf.getAverageRI()+ "/" +pf.getWeightBySigmoidFunction(pf.getVerbList().size(),pfSet.getAverageSizeOfPattern()));
			System.out.println(pf.toString() + "/"+pf.getConditionalOccrCount()+"/" + pf.getCountFactor() +"/"+ pf.getAverageRI() +"/"+ pf.getSizeFactor() +"/"+ pf.getAdjustedWeight());
			count++;
		}
		System.out.println(count+"/"+this.pfSet.size()+" candidates are extracted, avg size is "+pfSet.getAverageSizeOfPattern() + ", avg occurence count is "+pfSet.getAverageOccurenceOfPattern());
	}
	
	private void removeMinorVerb()
	{
		ArrayList<String> remList = new ArrayList<String>();
		
		for(String v : verbCount.keySet())
		{
			if(!graph.getEdgeStringList().contains(v))
			{
				//System.out.println(graph.getEdgeStringList());
				remList.add(v);
			}
		}
		
		for(String s : remList)
		{
			verbCount.remove(s);
		}
	}
	
	private void makePatternCandidate()
	{
		Stack<PatternFragment> pfStack = new Stack<PatternFragment>();
		//make initial pattern candidates
		pfSet = new PatternFragmentSet();
		for(String v : verbCount.keySet())
		{
			if(v.equals("s:ScenarioStart"))
				System.out.print("");
			for(Flow f: this.flowList)
			{
				for(int i=0;i<f.getSentenceList().size()-1;i++)
				{
					Sentence sen = f.getSentenceBySeq(i);
					if(sen.getVerbString().equals(v))
					{

						//there is a sentence that has same verb in graph
						Sentence nextSen = f.getSentenceBySeq(i+1);
						String fragString = sen.getVerbString()+"-"+nextSen.getVerbString();

						if(pfSet.containVerbString(fragString))
						{
							PatternFragment pf = pfSet.getPatternFragment(fragString);
							pf.addConditionalOccrCount();
						}
						else
						{
							PatternFragment pf = new PatternFragment(sen.getVerbString());
							pf.setPrevOccrCount(verbCount.get(v));
							pf.setNextVerb(nextSen.getVerbString());
							pf.setConditionalOccrCount(1);
							this.pfSet.add(pf);
							pfStack.push(pf);
						}
					}
				}
			}
		}
		// end of initialize
		
		//Test init
//		for(PatternFragment pf :pfStack)
//		{
//			System.out.println("----");
//			System.out.println(pf.toString());
//		}
		//Test init
		
		while(!pfStack.empty())
		{
			PatternFragment can = pfStack.pop();
			if(can.toString().contains("Start"))
				System.out.print("");
			
			for(Flow f : this.flowList)
			{
				if(hasMatchedAction(f,can))
				{
					can.addConditionalOccrCount();
					ArrayList<String> nextVerbList = getNextVerbString(f,can);

					for(String s : nextVerbList)
					{
						//if existing candidate
						if(this.pfSet.containVerbString(can.toString()+"-"+s))
						{
							PatternFragment exitingPF = pfSet.getPatternFragment(can.toString()+"-"+s);
							exitingPF.addConditionalOccrCount();
						}
						//new candidate
						else{
							PatternFragment newPF = new PatternFragment();
							newPF.setPrevRepVerbs(can.getVerbList());
							newPF.setPrevOccrCount(can.getConditionalOccrCount());
							newPF.setNextVerb(s);
							newPF.setConditionalOccrCount(1);
							pfSet.add(newPF);
							pfStack.push(newPF);
						}
					}
				}
			}
		}
		
	}
		
	private boolean hasMatchedAction(Flow f, PatternFragment can) {
		for(int i=0;i<f.getSentenceList().size();i++)
		{
			Sentence s = f.getSentenceBySeq(i);
			if(s.getVerbString().equals(can.getPrevRepVerbs().get(0)))
			{
				for(int j=1;j<can.getVerbList().size();j++)
				{
					if(i+j >= f.getSentenceList().size())
						break;
					
					if(f.getSentenceBySeq(i+j).getVerbString().equals(can.getVerbList().get(j)))
					{
						if(j==can.getVerbList().size()-1)
						{
							//System.out.println("Matched");
							return true;
						}
						else 
							continue;
					}
					else
						break;
					
				}
			}
		}
		return false;

	}
	
	private ArrayList<String> getNextVerbString(Flow f, PatternFragment can)
	{
		ArrayList<String> retList = new ArrayList<String>();
		
		for(int i=0;i<f.getSentenceList().size();i++)
		{
			Sentence s = f.getSentenceBySeq(i);
			if(s.getVerbString().equals(can.getPrevRepVerbs().get(0)))
			{
				//for(int j=1;j<can.getVerbList().size();j++)
				int j=1;
				while(true)
				{
					if(i+j >= f.getSentenceList().size())
					{
						break;
					}
					else if(i+j < f.getSentenceList().size()+1)
					{
						if(j == can.getVerbList().size())
						{
							retList.add(f.getSentenceBySeq(i+j).getVerbString());
							break;
						}
						else
						{
							if(f.getSentenceBySeq(i+j).getVerbString().equals(can.getVerbList().get(j)))
							{
								j++;
								continue;
							}
							else
								break;
						}
					}

					

				}
			}
		}
		
		//DEBUG
//		System.out.println("----");
//		for(String s : retList)
//			System.out.println(s);
//		System.out.println("====");
		//DEBUG
		return retList;
		
	}

	public ArrayList<CoOccurenceProb> getCoOccurenceTable()
	{
		flowIdInVerb = new HashMap<String, HashSet<String>>();
		
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getAllTrainingSentenceList();
		
		VerbClusterAccessor vca = new VerbClusterAccessor();
		ArrayList<VerbCluster> clusters = vca.getAllClusters();
		
		for(Sentence sen : sentenceList)
		{
			for(VerbCluster vc : clusters)
			{
				if(vc.getSubjectType().equals(sen.getSentenceType()+""))
				{
					if(vc.getVerbs().contains(sen.getVerb()))
					{
						sen.setRepresentVerb(vc.getRepresentives());
					}
				}
			}
			String senString = sen.getSentenceType()+":"+sen.getRepresentVerb();
			if(!flowIdInVerb.containsKey(senString))
			{
				flowIdInVerb.put(senString, new HashSet<String>());
			}
			
			flowIdInVerb.get(senString).add(sen.getFlowID());
		}
		
	//	printMap();
		
		makeMatrix();
		
		return this.matrix;
	}
	
	private void makeMatrix() {
		matrix = new ArrayList<CoOccurenceProb>(); 
		Set<String> key1 = this.flowIdInVerb.keySet();
		Set<String> key2 = this.flowIdInVerb.keySet();
		
		for(String k1 : key1)
		{
			for(String k2 : key2)
			{
				CoOccurenceProb prob = new CoOccurenceProb(k1,k2);
				
				if(k1.equals(k2))
					continue;
				
				//if the pair is in the matrix then continue
				if(matrix.contains(prob))
					continue;
				
				int coOccurCount = 0;
				
				HashSet<String> fidSet1 = this.flowIdInVerb.get(k1);
				HashSet<String> fidSet2 = this.flowIdInVerb.get(k2);
				
				for(String fid : fidSet1)
				{
					if(fidSet2.contains(fid))
						coOccurCount++;
				}
				
				prob.setCoOccurProb(((double) coOccurCount / (double) (fidSet1.size()) + (double) coOccurCount / (double) fidSet2.size()) / 2);
				
				matrix.add(prob);
			}
		}
		//printMatrix();
	}
	
	public void printMatrix()
	{
		for(CoOccurenceProb prob : matrix)
		{
			if(prob.getCoOccurProb() > 0.2)
			System.out.println(prob.toString()+"::"+prob.getCoOccurProb());
		}
	}

	public void printMap()
	{
		for(String key:flowIdInVerb.keySet())
		{
			System.out.print(key+"-");
			for(String id:flowIdInVerb.get(key))
				System.out.print(id+",");
			System.out.println("");
		}
	}

}
