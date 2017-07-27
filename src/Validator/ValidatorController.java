package Validator;

import java.util.ArrayList;
import java.util.HashSet;

import ActionFlowGraph.ActionFlowGraph;
import ActionFlowGraph.ActionFlowGraphGenerator;
import Entity.Project;
import Entity.Sentence;
import Entity.UseCase;
import Entity.VerbCluster;
import MissedActionFinder.ActionFinderController;
import MissedActionFinder.MissedAction;
import MySQLDataAccess.MissedResultAccessor;
import MySQLDataAccess.OverExtractedResultAccessor;
import MySQLDataAccess.ProjectAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.UseCaseAccessor;
import MySQLDataAccess.ValidationResultAccessor;
import PatternGenerator.PatternGeneratorController;
import PatternGenerator.PatternFragment;
import PatternGenerator.PatternFragmentSet;
import VerbClusterGenerater.Generator;

public class ValidatorController {
	
	private String targetProjectId;
	private ArrayList<VerbCluster> clusterList;
	private ActionFlowGraph flowGraph;
	private PatternFragmentSet patterns;
	private ArrayList<UseCase> targetProject;
	//ArrayList<UseCase> randomRemovedTargetProject;
	
	public void setPatternSet(String targetProjectId)
	{
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,true);
		getFlowGraph(targetProjectId);
		
		getTargetProject(targetProjectId);
	}
	
	public Result doSentenceValidation(String targetProjectId, boolean dbStore, boolean printLog){
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,true);
		getFlowGraph(targetProjectId);
		getPatterns(targetProjectId);
		getTargetProject(targetProjectId);
		return validate(dbStore,printLog,"n");
	}
	
	public Result doSentenceValidation(String targetProjectId, boolean dbStore, boolean printLog, String forSample){
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,true);
		getFlowGraph(targetProjectId);
		getPatterns(targetProjectId);
		getTargetProject(targetProjectId);
		return validate(dbStore,printLog,forSample);
	}
	
	public Result doSentenceValidation(String targetProjectId, boolean dbStore, boolean printLog, boolean makePattern){
		//this.targetProjectId = targetProjectId;
		if(makePattern)
		{
			getVerbCluster(targetProjectId,true);
			getFlowGraph(targetProjectId);
			//getPatterns(targetProjectId);
			getTargetProject(targetProjectId);
		}
		getPatterns(targetProjectId);
		return validate(dbStore,printLog,"n");
	}
	
	public Result doNoExceptValidation(String targetProjectId, boolean dbStore){
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,false);
		getFlowGraph();
		getPatterns();
		getTargetProject(targetProjectId);
		return validate(dbStore,true,"n");
	}
	
	public Result doCompleteScenarioValidation(String targetProjectId)
	{
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,false);
		getFlowGraph();
		getPatterns();
		getTargetProject(targetProjectId);
		return validateCompleteScenario();
	}
	
	public Result doOneScenarioValidation(String targetProjectId, String ucId, boolean dbStore){
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,true);
		getFlowGraph(targetProjectId);
		getPatterns(targetProjectId);
		getTargetScenario(targetProjectId,ucId);
		return validate(dbStore,true,"n");
	}
	
	public Result doOneTryValidation(String targetProjectId, String ucId, int omitNum,boolean dbStore){
		this.targetProjectId = targetProjectId;
		getVerbCluster(targetProjectId,true);
		getFlowGraph(targetProjectId);
		getPatterns(targetProjectId);
		getTargetScenario(targetProjectId,ucId);
		return validate(omitNum,dbStore,true);
	}
	
	private void getTargetScenario(String targetProjectId,String ucId){
		UseCaseAccessor ua = new UseCaseAccessor();
		ArrayList<UseCase> ucList= ua.getUseCaseList(targetProjectId);
		
		targetProject = new ArrayList<UseCase>();
		for(UseCase uc : ucList)
		{
			if(uc.getUseCaseID().equals(ucId))
			{
				targetProject.add(uc);
				break;
			}
		}
	}

	private void getTargetProject(String targetProjectId) {
		UseCaseAccessor ua = new UseCaseAccessor();
		targetProject = ua.getUseCaseList(targetProjectId);
		//System.out.println("--- "+ targetProject.size()+" use case loaded ---");
		
	}
	
	private Result validateCompleteScenario(){
		int totalTry = 0;
		int correct = 0;

		for(UseCase uc : targetProject)
		{
			totalTry++;
			ArrayList<Sentence> sentenceList = new SentenceAccessor().getBasicFlowSentenceList(uc.getProjectID(), uc.getUseCaseID());
			ActionFinderController afc = new ActionFinderController(patterns,clusterList);
			afc.findRepresentiveVerb(sentenceList);
			System.out.print("Original Scenario : ");
			for(Sentence sen : sentenceList)
			{
				System.out.print(sen.getSentenceType()+":"+sen.getRepresentVerb()+"-");
			}
			System.out.println("");
			
			HashSet<MissedAction> missedActionMap = afc.findMissedAction(sentenceList,true,true);
			
			if(missedActionMap.size() == 0)
				correct++;
		}
		
		return new Result(totalTry,0,correct);
	}

	private Result validate(boolean dbStore, boolean printLog, String forSample) {
		ValidationResultAccessor vra = new ValidationResultAccessor();
		OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
		MissedResultAccessor mra = new MissedResultAccessor();
		
		if(printLog)
			System.out.println("Try;UCID;Origin;MissedAction;Input;MissedRoute;Result;Extracted;Found");
		int totalTry = 0;
		int correct = 0;
		int incorrect = 0;
		int allExtracted = 0;
		for(UseCase uc : targetProject)
		{
			//UseCase uc = targetProject.get(0);
			ArrayList<Sentence> originSentenceList = new SentenceAccessor().getBasicFlowSentenceList(uc.getProjectID(), uc.getUseCaseID());
			ActionFinderController afc = new ActionFinderController(patterns,clusterList);
			afc.findRepresentiveVerb(originSentenceList);
//			
			for(int i=0;i<originSentenceList.size();i++){
				if (forSample.equals("y")) {
					if (forSample.equals("y") && originSentenceList.get(i).getIsSample().equals("n"))
						continue;
					if (!forSample.equals("y")) {
						if (!originSentenceList.get(i).getIsSample().equals(forSample))
							continue;
					}
				}

				String missedResultString = "";
				int correctInThisTry = 0;
				String insertString = "";
				boolean findThisTry = false; 
				totalTry++;
//				if(totalTry != 2)
//					continue;
				Sentence removedSentence = originSentenceList.get(i);
				ArrayList<Sentence> sentenceList = new ArrayList<Sentence>(originSentenceList);
				sentenceList.remove(i);

				//print result part 1
				if(printLog)
				{
					System.out.print(totalTry+";"+uc.getUseCaseID()+";");
				}
				insertString = insertString + totalTry+";"+uc.getUseCaseID()+";";
				
				for(Sentence sen : originSentenceList)
				{
					if(printLog)
						System.out.print(sen.getSentenceType()+":"+sen.getRepresentVerb()+"-");
					insertString = insertString + sen.getSentenceType()+":"+sen.getRepresentVerb()+"-";
				}
				if(printLog)
					System.out.print(";"+removedSentence.getVerb()+"("+removedSentence.getSentenceOrder()+");");
				insertString = insertString + ";"+removedSentence.getVerb()+";"+removedSentence.getSentenceOrder()+";";
				
				for(Sentence sen : sentenceList)
				{
					if(printLog)
						System.out.print(sen.getSentenceType()+":"+sen.getRepresentVerb()+"-");
					insertString = insertString + sen.getSentenceType()+":"+sen.getRepresentVerb()+"-";
				}
				if(printLog)
					System.out.print(";");
				insertString = insertString + ";";
				//end of print

				HashSet<MissedAction> missedActionMap = afc.findMissedAction(sentenceList,true,printLog);
				insertString = insertString + afc.extRoute;
				if(printLog)
					System.out.print(missedActionMap+";");
				insertString =  insertString + missedActionMap.toString() + ";";
				missedResultString = insertString;
				
				int extedOfMA = 0;
				String MAString = "";
				for(MissedAction ma : missedActionMap)
				{
					allExtracted++;
					extedOfMA++;
					String removedAction = removedSentence.getVerbString();
					//String beforeAction = "";
					int removedActionNum = Integer.parseInt(removedSentence.getSentenceOrder());

					String missedAction = ma.getActionString();
					//String missedBeforeAction = ma.beforeSentence().toString();
					int MissedPrevAction = ma.prevIndexOfMissed();
					//System.out.println("REM: "+removedAction+"("+removedActionNum +"), GOT: "+missedAction +"("+(MissedPrevAction+1)+")");
					
					
					if(removedAction.equals(missedAction) && removedActionNum == MissedPrevAction+1){
					//if(removedAction.equals(missedAction)){
						correctInThisTry++;
						correct++;
						findThisTry = true;
					}
					else{
						incorrect++;
						
						String extString = insertString;
						extString = extString + missedAction + ";" + (MissedPrevAction+1);
						
						if(dbStore)
							oera.addResult(extString);
					}
				}

				if(correctInThisTry > 1)
					System.out.println("ERROR:"+insertString);
				
				if(printLog)
					System.out.println(extedOfMA+";"+findThisTry);
				insertString =  insertString + extedOfMA+";"+findThisTry+";";
				if(findThisTry && extedOfMA == 1)
					insertString = insertString + "TRUE";
				else
					insertString = insertString + "FALSE";
				
				if(dbStore){
					vra.addResult(insertString);
					if(!findThisTry)
						mra.addResult(missedResultString);
				}
			}

		}
		
		//System.out.println("Missed Sentence #: "+totalTry);
		//System.out.println("Extrected Action: "+allExtracted);
		//System.out.println("Correct: "+correct);
		
		return new Result(totalTry,allExtracted,correct);
	}
	
	private Result validate(int omitNum, boolean dbStore, boolean printLog) {
		ValidationResultAccessor vra = new ValidationResultAccessor();
		OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
		MissedResultAccessor mra = new MissedResultAccessor();
		
		System.out.println("Try;UCID;Origin;MissedAction;Input;MissedRoute;Result;Extracted;Found");
		int totalTry = 0;
		int correct = 0;
		int incorrect = 0;
		int allExtracted = 0;
		for(UseCase uc : targetProject)
		{
			//UseCase uc = targetProject.get(0);
			ArrayList<Sentence> originSentenceList = new SentenceAccessor().getBasicFlowSentenceList(uc.getProjectID(), uc.getUseCaseID());
			ActionFinderController afc = new ActionFinderController(patterns,clusterList);
			afc.findRepresentiveVerb(originSentenceList);
//			
			for(int i=0;i<originSentenceList.size();i++){
				if(originSentenceList.get(i).getSentenceSeq() != omitNum)
					continue;
				String missedResultString = "";
				int correctInThisTry = 0;
				String insertString = "";
				boolean findThisTry = false; 
				totalTry++;
//				if(totalTry != 2)
//					continue;
				Sentence removedSentence = originSentenceList.get(i);
				ArrayList<Sentence> sentenceList = new ArrayList<Sentence>(originSentenceList);
				sentenceList.remove(i);

				//print result part 1
				if(printLog)
				{
					System.out.print(totalTry+";"+uc.getUseCaseID()+";");
				}
				insertString = insertString + totalTry+";"+uc.getUseCaseID()+";";
				
				for(Sentence sen : originSentenceList)
				{
					if(printLog)
						System.out.print(sen.getSentenceType()+":"+sen.getRepresentVerb()+"-");
					insertString = insertString + sen.getSentenceType()+":"+sen.getRepresentVerb()+"-";
				}
				if(printLog)
					System.out.print(";"+removedSentence.getVerb()+"("+removedSentence.getSentenceOrder()+");");
				insertString = insertString + ";"+removedSentence.getVerb()+";"+removedSentence.getSentenceOrder()+";";
				
				for(Sentence sen : sentenceList)
				{
					if(printLog)
						System.out.print(sen.getSentenceType()+":"+sen.getRepresentVerb()+"-");
					insertString = insertString + sen.getSentenceType()+":"+sen.getRepresentVerb()+"-";
				}
				if(printLog)
					System.out.print(";");
				insertString = insertString + ";";
				//end of print

				HashSet<MissedAction> missedActionMap = afc.findMissedAction(sentenceList,true,printLog);
				insertString = insertString + afc.extRoute;
				if(printLog)
					System.out.print(missedActionMap+";");
				insertString =  insertString + missedActionMap.toString() + ";";
				missedResultString = insertString;
				
				int extedOfMA = 0;
				String MAString = "";
				for(MissedAction ma : missedActionMap)
				{
					allExtracted++;
					extedOfMA++;
					String removedAction = removedSentence.getVerbString();
					//String beforeAction = "";
					int removedActionNum = Integer.parseInt(removedSentence.getSentenceOrder());

					String missedAction = ma.getActionString();
					//String missedBeforeAction = ma.beforeSentence().toString();
					int MissedPrevAction = ma.prevIndexOfMissed();
					//System.out.println("REM: "+removedAction+"("+removedActionNum +"), GOT: "+missedAction +"("+(MissedPrevAction+1)+")");
					
					
					if(removedAction.equals(missedAction) && removedActionNum == MissedPrevAction+1){
					//if(removedAction.equals(missedAction)){
						correctInThisTry++;
						correct++;
						findThisTry = true;
					}
					else{
						incorrect++;
						
						String extString = insertString;
						extString = extString + missedAction + ";" + (MissedPrevAction+1);
						
						if(dbStore)
							oera.addResult(extString);
					}
				}

				if(correctInThisTry > 1)
					System.out.println("ERROR:"+insertString);
				
				if(printLog)
					System.out.println(extedOfMA+";"+findThisTry);
				insertString =  insertString + extedOfMA+";"+findThisTry+";";
				if(findThisTry && extedOfMA == 1)
					insertString = insertString + "TRUE";
				else
					insertString = insertString + "FALSE";
				
				if(dbStore){
					vra.addResult(insertString);
					if(!findThisTry)
						mra.addResult(missedResultString);
				}
			}

		}
		
		//System.out.println("Missed Sentence #: "+totalTry);
		//System.out.println("Extrected Action: "+allExtracted);
		//System.out.println("Correct: "+correct);
		
		return new Result(totalTry,allExtracted,correct);
	}

	private void getFlowGraph() {
		ActionFlowGraphGenerator afg = new ActionFlowGraphGenerator(clusterList);
		afg.makeActionFlowGraph();
		flowGraph = afg.getGraph();
		//afg.drawGraph();
	}
	
	private void getFlowGraph(String exceptedProjectID) {
		ActionFlowGraphGenerator afg = new ActionFlowGraphGenerator(clusterList, exceptedProjectID);
		afg.makeActionFlowGraph();
		flowGraph = afg.getGraph();
		//afg.drawGraph();
	}

	private void getVerbCluster(String targetProjectId, Boolean withExcept) {
		Generator vcGen = new Generator();
		if(withExcept)
			clusterList = vcGen.makeVerbClusterForValidation(targetProjectId);
		else
			clusterList = vcGen.makeVerbClusterForValidation("");
		//clusterList = vcGen.makeVerbCluster();
		//System.out.println(clusterList.size());
		//System.out.println("--- "+clusterList.size()+" cluster generated (SAMPLE-"+clusterList.get(0)+") ---");
		
	}
	
	private void getPatterns()
	{
		PatternGeneratorController cont = new PatternGeneratorController(flowGraph);
		patterns = cont.makePatterns(false, "");
		for(PatternFragment pf : patterns)
		{
			if(pf != null)
				System.out.println(pf);
		}
		//System.out.println("--- "+patterns.size()+" patterns generated ---");
	}
	
	private void getPatterns(String exceptedProjectID)
	{
		PatternGeneratorController cont = new PatternGeneratorController(flowGraph);
		patterns = cont.makePatterns(true, exceptedProjectID);
		/*for(PatternFragment pf : patterns)
		{
			if(pf != null)
				System.out.println(pf);
		}*/
		//System.out.println("--- "+patterns.size()+" patterns generated ---");
	}

}
