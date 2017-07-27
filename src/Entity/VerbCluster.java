package Entity;

import java.util.ArrayList;

public class VerbCluster {
	private String representives;
	private String verbs;
	private boolean isCustom;
	private String subjectType;
	
	public VerbCluster(String rep, String verbs, String iscustom, String subjectType)
	{
		this.representives = rep;
		this.verbs = verbs;
		if(iscustom.equals("0"))
			this.isCustom = false;
		else if(iscustom.equals("1"))
			this.isCustom = true;
		this.subjectType = subjectType;
	}
	
	public void addVerb(String verb)
	{
		this.verbs = this.verbs+";"+verb;
	}

	public String getRepresentives() {
		return representives;
	}

	public void setRepresentives(String representives) {
		this.representives = representives;
	}

	public String getVerbs() {
		return verbs;
	}
	
	public ArrayList<String> getVerbList()
	{
		ArrayList<String> ret =  new ArrayList<String>();
		for(String v : verbs.split(";"))
		{
			ret.add(v);
		}
		return ret;
	}

	public void setVerbs(String verbs) {
		this.verbs = verbs;
	}

	public boolean isCustom() {
		return isCustom;
	}

	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}
	
	@Override
	public String toString()
	{
		return this.representives+":"+this.getVerbs();
	}
	
	

}
