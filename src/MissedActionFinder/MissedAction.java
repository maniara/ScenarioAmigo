package MissedActionFinder;

import java.util.ArrayList;

import Entity.Sentence;
import PatternGenerator.PatternFragment;

public class MissedAction{// implements Comparable {
	private int startSeq;
	private int missedSeqOfPattern;
	private String subject;
	private String verb;
	private PatternFragment pf;
	private ArrayList<Sentence> targetSentences;
	private ArrayList<Sentence> wholeSentence;
	
	public MissedAction(int actionSequence, String subject, String verb,
			PatternFragment pf, ArrayList<Sentence> targetSentences, ArrayList<Sentence> wholeSentence) {
		super();
		if(targetSentences.get(0).getVerb().equals("ScenarioStart"))
			this.startSeq = 1;
		else
			this.startSeq = targetSentences.get(0).getSentenceSeq();
		this.missedSeqOfPattern = actionSequence;
		this.subject = subject;
		this.verb = verb;
		this.pf = pf;
		this.targetSentences = targetSentences;
		this.wholeSentence = wholeSentence;
	}

	public String getSubject() {
		return subject;
	}
	public String getVerb() {
		return verb;
	}
	
	public String getActionString()
	{
		return this.subject.substring(0, 1)+":"+this.verb;
	}
	/*@Override
	public int compareTo(Object arg0) {
		MissedAction o = (MissedAction) arg0;
		if(this.prevSequence != o.getPrevSequence())
			return this.prevSequence - o.getPrevSequence();
		else
			return this.actionSequence - o.actionSequence;
	}*/
	
	@Override
	public String toString()
	{
		return subject+" "+verb+"(next to "+this.beforeSentence()+"["+this.prevIndexOfMissed()+"]) is missed";
	}
	
	public int getMissedActionSeq()
	{
		int last = this.startSeq-1;
		int index = missedSeqOfPattern-1;
		
		return last + index;
	}
	
	//[TODO]
	public int prevIndexOfMissed()
	{
		int missed = missedSeqOfPattern;
		if(pf.getVerbList().get(0).equals("s:ScenarioStart"))
			missed--;
		
		if(missedSeqOfPattern == 1 && startSeq==1)
			return 0;
		else
		{
			int startIndex = 0;
			for(int i=0;i<this.wholeSentence.size();i++)
			{
				Sentence s = this.wholeSentence.get(i);
				if(s.getSentenceSeq() == this.startSeq)
					startIndex =  i-1;
			}
			if (this.pf.getVerbList().size() - this.targetSentences.size() == 1)
				return startIndex+missed;
			else
			{
				int missedCount=0;
				for(int i=0,j=0;i<missedSeqOfPattern;)
				{
					if(pf.getVerbList().size()<= j || targetSentences.size()<=i)
						break;
					
					if(pf.getVerbList().get(j).equals(targetSentences.get(i))){
						i++;
						j++;
					}
					else{
						if(j==0)
							return startIndex+missed;
						missedCount++;
						j++;
					}
				}
				return startIndex+missed-missedCount;
			}
			
		}
	}
	
	public String beforeSentence()
	{
		if(this.prevIndexOfMissed() == -2)
			System.out.println("");
		if(missedSeqOfPattern == 1 && startSeq==1)
			return "ScenarioStart";
		else
			return this.wholeSentence.get(this.prevIndexOfMissed()).toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		MissedAction ma = (MissedAction) o;
		
		return ma.toString().equals(this.toString());
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}

	public int getStartSeq() {
		return startSeq;
	}

	public int getMissedSeqOfPattern() {
		return missedSeqOfPattern;
	}
	
	public boolean isLastIndex()
	{
		if(this.getMissedSeqOfPattern()+1 == this.pf.getVerbList().size())
			return true;
		else 
			return false;
	}

	public ArrayList<Sentence> getTargetSentences() {
		return targetSentences;
	}
	
	
	
}
