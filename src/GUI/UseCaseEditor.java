package GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;

import Entity.Flow;
import Entity.Sentence;
import Entity.UseCase;
import MissedActionFinder.ActionFinderController;
import MissedActionFinder.MissedAction;
import MySQLDataAccess.FlowAccessor;
import MySQLDataAccess.PatternAccessor;
import MySQLDataAccess.UseCaseAccessor;
import Recommender.RecommendController;

public class UseCaseEditor {

	protected Shell shell;
	private Text txt_ucName;
	private Text txt_ucDesc;
	private Text txt_preCond;
	private Text txt_postCond;
	private Text txt_ucid;
	private Combo cmb_includedOf;
	private Combo cmb_extendsOf;
	private EUCTable basicFlowTable;
	private CTabFolder AFTableFolder;
	private UseCase usecase;
	private String projectID;
	private boolean isEditMode;
	private ArrayList<AlternativeFlowTabItem> AFItemList;
	private String[] UCStringArray;
	private Text txt_actor;
	private String uc_id;
	
	///Edit mode
	public UseCaseEditor(UseCase uc, ArrayList<UseCase> uCList)
	{
		this.isEditMode = true;
		//this.UCList = uCList;
		this.usecase = uc;
		this.projectID = uc.getProjectID();
		FlowAccessor FA = new FlowAccessor();
		usecase.setFlowList(FA.getFlowList(projectID, uc.getUseCaseID(), 'u'));
		AFItemList = new ArrayList<AlternativeFlowTabItem>();
		makeUCStringList(uCList);
		openDisplay();
	}
	
	//Create mode
	/**
	 * @wbp.parser.constructor
	 */
	public UseCaseEditor(String projectID, ArrayList<UseCase> uCList)
	{
		this.projectID = projectID;
		//this.UCList = uCList;
		this.isEditMode = false;
		AFItemList = new ArrayList<AlternativeFlowTabItem>();
		makeUCStringList(uCList);
		openDisplay();
	}
	
	public UseCaseEditor(String ucid ,String projectID, ArrayList<UseCase> uCList)
	{
		System.out.println(ucid);
		this.projectID = projectID;
		//this.UCList = uCList;
		this.isEditMode = false;
		AFItemList = new ArrayList<AlternativeFlowTabItem>();
		makeUCStringList(uCList);
		//txt_ucid = new Text(shell, SWT.BORDER);
		this.uc_id = ucid;
		openDisplay();
		
	}
	
	private void openDisplay()
	{
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
	
	private void makeUCStringList(ArrayList<UseCase> UCList)
	{
		UCStringArray = new String[UCList.size()];
		for(int i=0;i<UCList.size();i++)
		{
			UseCase tmpUC = UCList.get(i);
			UCStringArray[i] = new String(tmpUC.getUseCaseID()+":"+tmpUC.getUseCaseName());
		}
	}

	protected void drawControls() {
		shell = new Shell();
		shell.setSize(692, 894);
		shell.setText("Use Case Editor");
		
		Label lblUseCaseId = new Label(shell, SWT.NONE);
		lblUseCaseId.setBounds(10, 18, 76, 15);
		lblUseCaseId.setText("Use Case ID");
		
		if(this.isEditMode)
		{
			Label lbl_ucid = new Label(shell,SWT.NONE);
			lbl_ucid.setBounds(141, 15, 153, 21);
			lbl_ucid.setText(this.usecase.getUseCaseID());
		}
		else
		{
			txt_ucid = new Text(shell, SWT.BORDER);
			if(this.uc_id != null)
				txt_ucid.setText(this.uc_id);
			txt_ucid.setBounds(141, 15, 153, 21);
		}
		
		Label lblUseCaseName = new Label(shell, SWT.NONE);
		lblUseCaseName.setBounds(10, 45, 94, 15);
		lblUseCaseName.setText("Use Case Name");
		
		txt_ucName = new Text(shell, SWT.BORDER);
		txt_ucName.setBounds(141, 42, 513, 21);
		if(this.isEditMode)
			txt_ucName.setText(this.usecase.getUseCaseName());
		
		Label lblUseCaseDescription = new Label(shell, SWT.NONE);
		lblUseCaseDescription.setBounds(10, 75, 120, 15);
		lblUseCaseDescription.setText("Use Case Description");
		
		txt_ucDesc = new Text(shell, SWT.BORDER);
		txt_ucDesc.setBounds(141, 69, 513, 46);
		if(this.isEditMode)
			txt_ucDesc.setText(this.usecase.getUseCaseDescription());
		
		Label lblUseActor = new Label(shell, SWT.NONE);
		lblUseActor.setBounds(10, 124, 94, 15);
		lblUseActor.setText("Actor");
		
		txt_actor = new Text(shell, SWT.BORDER);
		txt_actor.setBounds(141, 121, 513, 21);
		if(this.isEditMode)
			txt_actor.setText(this.usecase.getActor());
		
		Label lblPrecondition = new Label(shell, SWT.NONE);
		lblPrecondition.setBounds(10, 158, 76, 15);
		lblPrecondition.setText("PreCondition");
		
		txt_preCond = new Text(shell, SWT.BORDER);
		txt_preCond.setBounds(141, 152, 513, 21);
		if(this.isEditMode)
			txt_preCond.setText(this.usecase.getPreCondition());
		
		Label lblPostcondition = new Label(shell, SWT.NONE);
		lblPostcondition.setBounds(10, 191, 76, 15);
		lblPostcondition.setText("PostCondition");
		
		txt_postCond = new Text(shell, SWT.BORDER);
		txt_postCond.setBounds(141, 183, 513, 21);
		if(this.isEditMode)
			txt_postCond.setText(this.usecase.getPostCondition());
				
		Label lblIncludedOf = new Label(shell, SWT.NONE);
		lblIncludedOf.setBounds(10, 224, 76, 15);
		lblIncludedOf.setText("Included Of");
		
		cmb_includedOf = new Combo(shell, SWT.NONE);
		cmb_includedOf.setBounds(141, 216, 513, 23);
		cmb_includedOf.setItems(UCStringArray);
		if(this.isEditMode && !this.usecase.getIncludedOf().equals("") && !(this.usecase.getIncludedOf() == null))
		{
			String str = "";
			for(String s:this.UCStringArray)
			{
				if(s.startsWith(this.usecase.getIncludedOf()))
					str = s;
			}
			cmb_includedOf.setText(str);
		}
		
		Label lblExcendsOf = new Label(shell, SWT.NONE);
		lblExcendsOf.setBounds(10, 260, 76, 15);
		lblExcendsOf.setText("Extends Of");
		
		cmb_extendsOf = new Combo(shell, SWT.NONE);
		cmb_extendsOf.setBounds(141, 252, 513, 23);
		cmb_extendsOf.setItems(UCStringArray);
		if(this.isEditMode && !this.usecase.getExtendsOf().equals("") && !(this.usecase.getExtendsOf() == null))
		{
			String str = "";
			for(String s:this.UCStringArray)
			{
				if(s.startsWith(this.usecase.getExtendsOf()))
					str = s;
			}
			cmb_extendsOf.setText(str);
		}
		
		Label lblBasicFlow = new Label(shell, SWT.NONE);
		lblBasicFlow.setBounds(10, 291, 56, 15);
		lblBasicFlow.setText("Basic Flow");
		
		if(this.isEditMode)
		{
			//System.out.println(this.usecase.getFlowList());
			for(Flow f : this.usecase.getFlowList())
			{
				if(!f.getIsAlternative())
				{
					//System.out.println("create basic flow");
					basicFlowTable = new EUCTable(shell, f.getSentenceList(), false);
					basicFlowTable.setBounds(141, 311, 513, 166);
				}
			}
		}
		else
		{
			basicFlowTable = new EUCTable(shell, false);
			basicFlowTable.setBounds(141, 282, 513, 166);
		}
		
		Button btn_addOneLine = new Button(shell, SWT.NONE);
		btn_addOneLine.setBounds(568, 452, 86, 25);
		btn_addOneLine.setText("New Step");
		btn_addOneLine.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent arg0) {
				basicFlowTable.addLine();
			}});
				
		Label lblAlternativeFlow = new Label(shell, SWT.NONE);
		lblAlternativeFlow.setBounds(10, 492, 94, 15);
		lblAlternativeFlow.setText("Alternative Flow");
		
		AFTableFolder = new CTabFolder(shell, SWT.BORDER);
		AFTableFolder.setBounds(130, 492, 524, 273);
		AFTableFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		if(this.isEditMode)
		{
			for(Flow f : this.usecase.getFlowList())
			{
				if(f.getIsAlternative())
				{
					//System.out.println("dkfke");
					makeAlternativeTable(f);
				}
			}
			
		}
		
		this.makeAlternativeTable();

		
		/*TableEditor editor = new TableEditor(basicFlowTable);
		editor.horizontalAlignment = SWT.LEFT;
		basicFlowTable*/
				
		Button btn_AFAddOneLine = new Button(shell, SWT.NONE);
		btn_AFAddOneLine.setBounds(416, 771, 94, 25);
		btn_AFAddOneLine.setText("Add One Line");
		btn_AFAddOneLine.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent arg0) {
				Table tmp = (Table) AFItemList.get(AFTableFolder.getSelectionIndex()).getAlternativeFlowTable().getTable();
				@SuppressWarnings("unused")
				TableItem item = new TableItem(tmp, SWT.NONE);
			}});
		
		Button btnAFlow = new Button(shell, SWT.NONE);
		btnAFlow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AFItemList.get(AFItemList.size()-1).setTitle();
				makeAlternativeTable();
				AFTableFolder.setSelection(AFItemList.size()-1);
				
			}
		});
		btnAFlow.setBounds(516, 771, 138, 25);
		btnAFlow.setText("New Alternative Flow");
		
		Button btn_engVerb = new Button(shell, SWT.NONE);
		btn_engVerb.setBounds(200, 771, 200, 25);
		btn_engVerb.setText("Confirm Main Verbs");
		btn_engVerb.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//new PatternAccessor().addPatternList(patternSet);
				//ActionFinderController.findMissedAction(usecase.getBasicFlowSentences());
				new MainVerbList(usecase);
			}
		});
		
		Button btnFindingMissedAction = new Button(shell,SWT.NONE);
		btnFindingMissedAction.setBounds(60, 821, 170, 25);
		btnFindingMissedAction.setText("Find Omitted Steps");
		btnFindingMissedAction.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//new PatternAccessor().addPatternList(patternSet);
				ActionFinderController afc  = new ActionFinderController();
				HashSet<MissedAction> missedActionMap = afc.findMissedAction(usecase.getBasicFlowSentences(),false,false);
				//Collections.sort(missedActionMap);
				MessageBox box = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
				String msg = "";
				for(MissedAction action : missedActionMap)
				{
					msg = msg + action+"\n";
				}
				box.setMessage(msg);
				
				box.open();
				
			}
		});
		
		Button btnRecommend = new Button(shell,SWT.NONE);
		btnRecommend.setBounds(239, 821, 170, 25);
		btnRecommend.setText("Get Recommended AF");
		btnRecommend.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				//ProgressBar pb = new ProgressBar(shell,SWT.SMOOTH);
				//pb.setSelection(60);
				//pb.setBounds(80 , 797, 200, 20);
				UseCase newUC = makeUseCaseObject();
				RecommendController RC = new RecommendController();
				RC.getAlternative(AFTableFolder,AFItemList,newUC.getBasicFlowSentences());
			}
			});

		Button btnConfirm = new Button(shell, SWT.NONE);
		btnConfirm.setBounds(416, 821, 76, 25);
		btnConfirm.setText("Confirm");
		btnConfirm.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				UseCase newUC = makeUseCaseObject();
				UseCaseAccessor UCA = new UseCaseAccessor();
				
				if(isEditMode)
				{
					UCA.deleteUseCase(projectID, newUC.getUseCaseID());
				}

				UCA.addUseCase(newUC);
				
				shell.close();
			}});
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnCancel.setBounds(578, 821, 76, 25);
		btnCancel.setText("Cancel");
		
		Button btnDeleteUc = new Button(shell, SWT.NONE);
		btnDeleteUc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				UseCaseAccessor UCA = new UseCaseAccessor();
				UCA.deleteUseCase(projectID, usecase.getUseCaseID());
				shell.close();
			}
		});
		btnDeleteUc.setBounds(498, 821, 76, 25);
		btnDeleteUc.setText("Delete UC");
	}

	private void makeAlternativeTable()
	{
		AlternativeFlowTabItem AFItem = new AlternativeFlowTabItem(AFTableFolder,false);
		AFItemList.add(AFItem);
	}
	
	private void makeAlternativeTable(Flow flow)
	{
		AlternativeFlowTabItem AFItem = new AlternativeFlowTabItem(AFTableFolder, flow,false);
		AFItemList.add(AFItem);
	}
	
	private UseCase makeUseCaseObject()
	{
		String ucid = null;
		if(this.isEditMode)
			ucid = this.usecase.getUseCaseID();
		else
			ucid = this.txt_ucid.getText();
		//System.out.print(isEditMode+ " id " + ucid+ this.txt_ucid.getText());
		
		String ext = null;
		if(this.cmb_extendsOf.getSelectionIndex() >= 0)
			ext = this.cmb_extendsOf.getItem(this.cmb_extendsOf.getSelectionIndex()).split(":")[0];
		String inc = null;
		if(this.cmb_includedOf.getSelectionIndex() >= 0)
			inc = this.cmb_includedOf.getItem(this.cmb_includedOf.getSelectionIndex()).split(":")[0];
		
		String pre = this.txt_preCond.getText();
		String post = this.txt_postCond.getText();
		String desc = this.txt_ucDesc.getText();
		String name = this.txt_ucName.getText();
		String actor = this.txt_actor.getText();
		
		UseCase object = new UseCase(projectID, ucid, name, actor,pre, post, desc);
		if(ext != null)
		{
			object.setExtendsOf(ext);
		}
		
		if(inc != null)
		{
			object.setIncludedOf(inc);
		}
		
		//basic flow
		//boolean isAlternative, String startOrder, String flowID, ArrayList<Sentence> sentenceList
		ArrayList<Sentence> l = this.makeSentenceList(this.basicFlowTable, ucid, ucid+"-1","");
		
		object.addFlow(new Flow(projectID, ucid, false, "1", ucid+"-1",l,'u'));
		
		//alternative flow
		for(AlternativeFlowTabItem item : this.AFItemList)
		{
			if(item.getStartOrder().equals("") || item.getStartOrder() == null)
				continue;
			object.addFlow(new Flow(projectID, ucid, true, item.getStartOrder(), ucid+"-"+item.getStartOrder(), this.makeSentenceList(item.getAlternativeFlowTable(), ucid, ucid+"-"+item.getStartOrder(),item.getStartOrder()), item.getStartCondition(),'u' ));
		}
		
		return object;
	}
	
	private ArrayList<Sentence> makeSentenceList(EUCTable table, String usecaseID, String flowID, String seqHead)
	{
		Table tmpTable = table.getTable();
		ArrayList<Sentence> list = new ArrayList<Sentence>();
		
		for(int i=0; i < tmpTable.getItemCount(); i++)
		{
			TableItem item = table.getTable().getItem(i);

			if (item.getText(0).equals("") && item.getText(1).equals("") && item.getText(2).equals("") && item.getText(3).equals(""))
			{
				continue;
			}
			char type = 0;
			String contents = null;
			String order = null;
			//user action side
			if (!item.getText(1).equals(""))
			{
				type = 'u';
				order = item.getText(0);
				//if sequencd is empty add sequence
				if(order.equals("") || order == null)
					order = seqHead+(i+1)+ "";
				contents = item.getText(1);
			}
			
			//system response side
			else if (!item.getText(3).equals(""))
			{
				type = 's';
				order = item.getText(2);
				//if sequence is empty add sequence
				if(order.equals("") || order == null)
					order = seqHead+(i+1)+ "";
				contents = item.getText(3);
			}
			System.out.println("sentence added");
			list.add(new Sentence(projectID, usecaseID, flowID, order,  contents, type, i, false, false,"n"));
		}
		//System.out.println(list);
		return list;
	}

}
