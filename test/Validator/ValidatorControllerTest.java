package Validator;

import java.util.ArrayList;
import org.junit.Test;

import MySQLDataAccess.MissedResultAccessor;
import MySQLDataAccess.OverExtractedResultAccessor;
import MySQLDataAccess.ValidationResultAccessor;
import ToolSettings.Thresholds;

public class ValidatorControllerTest {
	
	private ArrayList<String> getIndustryProjectList()
	{
		ArrayList<String> prjList = new ArrayList<String>();
		prjList.add("SKP");
		prjList.add("COS");
		prjList.add("META");
		prjList.add("UIS");
		prjList.add("PAY");
		return prjList;
	}
	
	private ArrayList<String> getAcademicProjectList()
	{
		ArrayList<String> prjList = new ArrayList<String>();

		prjList.add("ATM");
		prjList.add("OSS");
		prjList.add("PRS");
		prjList.add("POS");
		prjList.add("TMS");
		prjList.add("STS");
		prjList.add("OPS");
		prjList.add("HCS");
		prjList.add("EMS");
		prjList.add("ATC");
		return prjList;
	}
	
	private ArrayList<String> getAllProjectList()
	{
		ArrayList<String> prjList = new ArrayList<String>();
		prjList.add("SKP");
		prjList.add("COS");
		prjList.add("META");
		prjList.add("UIS");
		prjList.add("PAY");
		prjList.add("ATM");
		prjList.add("OSS");
		prjList.add("PRS");
		prjList.add("POS");
		prjList.add("TMS");
		prjList.add("STS");
		prjList.add("OPS");
		prjList.add("HCS");
		prjList.add("EMS");
		prjList.add("ATC");
		return prjList;
	}
	
	
	
	
	private void printResult(ArrayList<Result> resultList) {
		int tot_try=0;
		int tot_ext=0;
		int tot_cor=0;
		String prj = "";
		for(Result r : resultList)
		{
			if(!prj.equals(r.getProjectCode())){
				prj = r.getProjectCode();
				System.out.println("---"+r.getProjectCode()+"---");
			}
			System.out.println(r);
			tot_try = tot_try + r.getTryNum();
			tot_ext = tot_ext + r.getExtracted();
			tot_cor = tot_cor + r.getCorrect();
		}
		
		System.out.println("=== Total ===");
		System.out.println(tot_try+":"+tot_ext+":"+tot_cor);
	}
	
	@Test
	public void doWholeValidation()
	{
		boolean dbStore = false;
		boolean printLog = false;
		if(dbStore){
			ValidationResultAccessor vra = new ValidationResultAccessor();
			vra.deleteAllResult();
			
			OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
			oera.deleteAllResult();
			
			MissedResultAccessor mra = new MissedResultAccessor();
			mra.deleteAllResult();
		}

		ArrayList<String> prjList = this.getAllProjectList();
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		//Thresholds.Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE = {0.2,0.8};
		for(String prj : prjList){
			ValidatorController v = new ValidatorController();
			Result r = v.doSentenceValidation(prj,dbStore,printLog);
			r.setProjectCode(prj);
			resultList.add(r);
		}
		printResult(resultList);
	}
	
	@Test
	public void doNoExceptValidation()
	{
		ArrayList<String> prjList = this.getAllProjectList();
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		//Thresholds.Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE = {0.2,0.8};
		for(String prj : prjList){
			ValidatorController v = new ValidatorController();
			Result r = v.doNoExceptValidation(prj,false);
			r.setProjectCode(prj);
			resultList.add(r);
		}
		printResult(resultList);
	}
	
	
	
	@Test
	public void doCompleteScenarioValidation()
	{
		ArrayList<String> prjList = this.getIndustryProjectList();
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		//Thresholds.Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE = {0.2,0.8};
		for(String prj : prjList){
			ValidatorController v = new ValidatorController();
			Result r = v.doCompleteScenarioValidation(prj);
			r.setProjectCode(prj);
			resultList.add(r);
		}
		printResult(resultList);
	}
	
	@Test
	public void doOneProjectValidation()
	{
		boolean dbStore = false;
		boolean printLog = false;
		if(dbStore){
			ValidationResultAccessor vra = new ValidationResultAccessor();
			vra.deleteAllResult();
			
			OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
			oera.deleteAllResult();
			
			MissedResultAccessor mra = new MissedResultAccessor();
			mra.deleteAllResult();
		}
		String prj = "ATC";
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		ValidatorController v = new ValidatorController();
		Result r = v.doSentenceValidation(prj,dbStore,printLog);
		r.setProjectCode(prj);
		resultList.add(r);
		printResult(resultList);
	}
	
	@Test
	public void doOneScenarioValidation()
	{
		String prj = "ATC";
		String uc = "ATC4";
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		ValidatorController v = new ValidatorController();
		Result r = v.doOneScenarioValidation(prj, uc,false);
		r.setProjectCode(prj);
		resultList.add(r);
		printResult(resultList);
	}
	
	@Test
	public void doOneTryValidation()
	{
		String prj = "HCS";
		String uc = "HCS1";
		int omit = 3;
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		ValidatorController v = new ValidatorController();
		Result r = v.doOneTryValidation(prj, uc, omit, false);
		r.setProjectCode(prj);
		resultList.add(r);
		printResult(resultList);
	}
	
	@Test
	public void doSampleValidation()
	{
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

		boolean dbStore = false;
		boolean printLog = false;
		if(dbStore){
			ValidationResultAccessor vra = new ValidationResultAccessor();
			vra.deleteAllResult();
			
			OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
			oera.deleteAllResult();
			
			MissedResultAccessor mra = new MissedResultAccessor();
			mra.deleteAllResult();
		}

		ArrayList<String> prjList = this.getAllProjectList();
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		//Thresholds.Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE = {0.2,0.8};
		for(String prj : prjList){
			ValidatorController v = new ValidatorController();
			Result r = v.doSentenceValidation(prj,dbStore,printLog,"y");
			r.setProjectCode(prj);
			resultList.add(r);
		}
		printResult(resultList);
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
	}
	
	@Test
	public void doImportanceValidation()
	{
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

		boolean dbStore = false;
		boolean printLog = false;
		if(dbStore){
			ValidationResultAccessor vra = new ValidationResultAccessor();
			vra.deleteAllResult();
			
			OverExtractedResultAccessor oera = new OverExtractedResultAccessor();
			oera.deleteAllResult();
			
			MissedResultAccessor mra = new MissedResultAccessor();
			mra.deleteAllResult();
		}

		ArrayList<String> prjList = this.getAllProjectList();
		ArrayList<Result> resultList = new ArrayList<Result>();
				
		//Thresholds.Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE = {0.2,0.8};
		for(String prj : prjList){
			ValidatorController v = new ValidatorController();
			Result r = v.doSentenceValidation(prj,dbStore,printLog,"2");
			r.setProjectCode(prj);
			resultList.add(r);
		}
		printResult(resultList);
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
	}
	


}
