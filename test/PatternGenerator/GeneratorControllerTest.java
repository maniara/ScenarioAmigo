package PatternGenerator;

import java.util.ArrayList;

import org.junit.Test;

import ActionFlowGraph.ActionFlowGraph;
import ActionFlowGraph.ActionFlowGraphGenerator;
import Entity.Flow;
import Entity.VerbCluster;
import MySQLDataAccess.FlowAccessor;
import TemporalEntities.EntityStorage;
import VerbClusterGenerater.Generator;

public class GeneratorControllerTest {
	
	private PatternFragmentSet makePatterns(String targetProject){
		
		Generator vcGen = new Generator();
		ArrayList<VerbCluster> clusterList = vcGen.makeVerbClusterForValidation(targetProject);
		
		ActionFlowGraphGenerator afg = new ActionFlowGraphGenerator(clusterList);
		afg.makeActionFlowGraph();
		ActionFlowGraph flowGraph = afg.getGraph();
		//afg.drawGraph();
		
		PatternGeneratorController cont = new PatternGeneratorController(flowGraph);
		PatternFragmentSet patterns = cont.makePatterns(true, targetProject);
		
		for(PatternFragment pf : patterns)
		{
			//String p = pf.toString()+"("+pf.getCountFactor()+","+pf.getAverageRI()+","+pf.getSizeFactor()+","+pf.getAdjustedWeight()+")";
			//System.out.println(p);
		}
		
		return patterns;

	}
	
	@Test
	public void makePatternTest(){
		makePatterns("");
	}
	
	@Test
	public void findAppliedPattern()
	{
		PatternFragmentSet pSet = makePatterns("");
		FlowAccessor fa = new FlowAccessor();
		ArrayList<Flow> flowList = EntityStorage.allFlowList;
		

		
		for(PatternFragment pf : pSet){
			System.out.println("-------------------");
			System.out.println(pf.toString());
			for(Flow f : flowList){
				if(f.getIsAlternative() == false){
					String fString = f.getFlowString();
			//		System.out.println(fString);
					if(f.getFlowString().contains(pf.toString()))
					{
						System.out.println(f.getProjectID()+":"+f.getUseCaseID()+":"+f.getFlowString());
					}
				}
			}
		}
	}

}
