package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ActionFlowGraph.ActionFlowGraphGenerator;
import ActionFlowGraph.GraphViewer;
import Entity.Sentence;
import Entity.VerbFrequency;
import MySQLDataAccess.DictionaryAccessor;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.SentenceAccessor;
import MySQLDataAccess.VerbClusterAccessor;
import MySQLDataAccess.VerbFrequencyAccessor;
import PatternGenerator.PatternGeneratorController;
import PatternGenerator.PatternSetInstance;
import PatternGenerator.CoOccurenceProb;
import PatternGenerator.PatternFragmentSet;
import Recommender.MainVerbExtractor;
import ToolSettings.Thresholds;
import VerbClusterGenerater.BaseFormExtractor;
import VerbClusterGenerater.ClusterCreatorController;
import VerbClusterGenerater.Generator;
import VerbClusterGenerater.VerbClusterInstance;
import PatternGenerator.PatternTreeViewer;

import org.eclipse.swt.events.SelectionAdapter;

public class PatternGenerator {
	
	protected Shell shell;
	private Label label;
	private ActionFlowGraphGenerator afg;

	public PatternGenerator()
	{
		shell = new Shell();
		shell.setSize(430, 400);
		shell.setText("Generating Pattern Controller");

		Display display = Display.getDefault();
		drawControls();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void drawControls() {
		Button btnVerbGenerator = new Button(shell,SWT.PUSH);
		btnVerbGenerator.setBounds(33, 26, 178, 25);
		
		//1:
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 5, 117, 15);
		lblNewLabel.setText("1: Extract Main Verb");
		
		btnVerbGenerator.setText("Extract and Set Main Verb");
		btnVerbGenerator.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				setMainVerbOfTrainingSentence();
			}
		});

		btnVerbGenerator.pack();
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new MainVerbList();
			}
		});
		btnNewButton.setBounds(210, 26, 153, 25);
		btnNewButton.setText("Edit Main Verbs");
		
		//2:
		
		Label lblClusteringVerbs = new Label(shell, SWT.NONE);
		lblClusteringVerbs.setBounds(10, 64, 117, 15);
		lblClusteringVerbs.setText("2: Clustering Verbs");
		
		Button editDic = new Button(shell, SWT.NONE);
		editDic.setBounds(33, 85, 153, 25);
		editDic.setText("Edit Verb Dictionary");
		editDic.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new VerbClusterList(true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		/*Button distanceClusterButton = new Button(shell, SWT.NONE);
		distanceClusterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new VerbClusterAccessor().deleteAllGeneratedCluster();
				makeDistancedBasedCluster();
				System.out.println("Clustering Complete");
			}
		});
		distanceClusterButton.setBounds(33, 116, 153, 25);
		distanceClusterButton.setText("Distance based Clustering");
		
		Button synomymClusteringButton = new Button(shell, SWT.NONE);
		synomymClusteringButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new VerbClusterAccessor().deleteAllGeneratedCluster();
				makeSynonymBasedCluster();
			}
		});
		synomymClusteringButton.setBounds(210, 116, 153, 25);
		synomymClusteringButton.setText("Synonym based Clustering");*/
		
		Button dicClusterButton = new Button(shell, SWT.NONE);
		dicClusterButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new VerbClusterAccessor().deleteAllGeneratedCluster();
				makeDicBasedCluster();
				System.out.println("=== Clustering Complete ===");
			}
		});
		dicClusterButton.setBounds(33, 116, 153, 25);
		dicClusterButton.setText("Dictionary based Clustering");
		
		
		Button editClusterButton = new Button(shell, SWT.NONE);
		editClusterButton.setBounds(210, 116, 153, 25);
		editClusterButton.setText("Show Verb Cluster");
		
		editClusterButton.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				new VerbClusterList(false);
			}
		});
		
		Label lblFlowGraph = new Label(shell, SWT.NONE);
		lblFlowGraph.setText("3. Flow Graph");
		lblFlowGraph.setBounds(10, 183, 117, 15);

		
		Button btnNewButton_3 = new Button(shell, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				afg = new ActionFlowGraphGenerator();
				afg.makeActionFlowGraph();
			}
		});
		btnNewButton_3.setBounds(33, 204, 153, 25);
		btnNewButton_3.setText("Generate Graph");
		
		Button btnNewButton_4 = new Button(shell, SWT.NONE);
		//btnNewButton_4.setEnabled(false);
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(afg !=null)
					afg.drawGraph();
			}
		});
		btnNewButton_4.setBounds(210, 204, 153, 25);
		btnNewButton_4.setText("Show Graph!");

		Label lblcunc = new Label(shell, SWT.NONE);
		lblcunc.setText("4. Generate Patterns");
		lblcunc.setBounds(10, 250, 117, 15);
		
		Button btnNewButton_5 = new Button(shell, SWT.NONE);
		btnNewButton_5.setBounds(33, 270, 153, 25);
		btnNewButton_5.setText("Extract Patterns");
		btnNewButton_5.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				generatePattern();

			}
		});
		
		Button btnNewButton_6 = new Button(shell, SWT.NONE);
		btnNewButton_6.setBounds(210, 270, 153, 25);
		btnNewButton_6.setText("Show Pattern Tree");
		btnNewButton_6.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				drawPatternTree();
			}
		});
		
		Button savePatternsButton = new Button(shell, SWT.NONE);
		savePatternsButton.setBounds(33, 320, 300, 25);
		savePatternsButton.setText("Save Patterns");
		savePatternsButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new PatternAccessor().addPatternList();
			}
		});
	}
	
	private void generatePattern()
	{
		System.out.println("=== Patterns Extracting ===");
		if(afg == null)
		{
			afg = new ActionFlowGraphGenerator();
			afg.makeActionFlowGraph();
		}
		PatternGeneratorController cont = new PatternGeneratorController(afg.getGraph());
		
		PatternSetInstance.PatternSet = cont.makePatterns(false, null);
		
		System.out.println("=== Patterns Extracted ===");
	}
	
	private void drawPatternTree()
	{
		new PatternTreeViewer().draw();
	}
	
	/*private void makeSynonymBasedCluster()
	{
		HashSet<String> userVerb = new HashSet<String>();
		HashSet<String> systemVerb = new HashSet<String>();
		
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getAllTrainingSentenceList();
		ArrayList<VerbFrequency> freqs = makeVerbFrequency(sentenceList);
		
		HashMap<String,Double> freqMap = new HashMap<String,Double>();
		for(VerbFrequency f : freqs)
		{
			freqMap.put(f.getVerb(), f.getFrequency());
		}
		
		for(Sentence s : sentenceList)
		{			
			if (s.getVerb() == null || s.getVerb().equals(""))
				continue;

			if (s.getSentenceType() == 'u')
				userVerb.add(s.getVerb());
			else if (s.getSentenceType() == 's')
				systemVerb.add(s.getVerb());

		}
		
		//remove in custom
		VerbClusterAccessor vca = new VerbClusterAccessor();
		HashMap<String, ArrayList<String>> vMap = vca.getAllVerbOfCustomCluster();
		for (String v : vMap.get("u"))
			userVerb.remove(v);
		for (String v : vMap.get("s"))
			systemVerb.remove(v);
		
		HashMap<String,String> userVerbClusterMap = ClusterCreatorController.createSynonymBasedCluster(userVerb,freqMap);
		HashMap<String,String> systemVerbClusterMap = ClusterCreatorController.createSynonymBasedCluster(systemVerb,freqMap);
		
		VerbClusterInstance.UserVerbCluster = userVerbClusterMap;
		VerbClusterInstance.SystemVerbCluster = systemVerbClusterMap;
		
		vca = new VerbClusterAccessor();
		
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
	}*/
	
	public void makeDicBasedCluster()
	{
		new Generator().makeVerbCluster();
	}
	
	/*private void makeDistancedBasedCluster()
	{
		
		HashSet<String> userVerb = new HashSet<String>();
		HashSet<String> systemVerb = new HashSet<String>();
		
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getAllTrainingSentenceList();
		ArrayList<VerbFrequency> freqs = makeVerbFrequency(sentenceList);
		
		for(Sentence s : sentenceList)
		{			
			if(s.getVerb() == null || s.getVerb().equals(""))
				continue;
			
			for(VerbFrequency f : freqs)
			{
				if(f.getType().equals(s.getSentenceType()+"") && f.getVerb().equals(s.getVerb()))
				{
					if(f.getFrequency()>Thresholds.Verb_Occurence_Threshold)
					{
						if(s.getSentenceType() == 'u')
							userVerb.add(s.getVerb());
						else if
						(s.getSentenceType() == 's')
							systemVerb.add(s.getVerb());
					}
				}
			}


		}
		
		//remove in custom
		VerbClusterAccessor vca = new VerbClusterAccessor();
		HashMap<String, ArrayList<String>> vMap = vca.getAllVerbOfCustomCluster();
		
		for(String v : vMap.get("u"))
			userVerb.remove(v);
		for(String v : vMap.get("s"))
			systemVerb.remove(v);

		//Create clusters
		HashMap<String,String> userVerbClusterMap = ClusterCreatorController.createDistanceBasedCluster(userVerb);
		HashMap<String,String> systemVerbClusterMap = ClusterCreatorController.createDistanceBasedCluster(systemVerb);
		
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

		
	}*/

	private void setMainVerbOfTrainingSentence() {
		MainVerbExtractor ext = new MainVerbExtractor();
		SentenceAccessor sa = new SentenceAccessor();
		ArrayList<Sentence> sentenceList = sa.getNoVerbTrainingSentenceList();
		for(Sentence s : sentenceList)
			
		{
			System.out.print(s.getSentenceContents() + ":");
			if(s.getSentenceContents().equals("Supplier requests next delivery order."))
				System.out.println("");
			String verb = ext.getMainVerb(s.getSentenceContents());
			System.out.println(verb);
			if(verb != null)
			{
				try{
					verb = BaseFormExtractor.getBaseVerbForm(verb);
				}
				catch(Exception e)
				{}
				finally
				{
					s.setVerb(verb);
					sa.updateVerb(s);
				}
			}
		}
	}
}
