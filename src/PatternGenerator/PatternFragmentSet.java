package PatternGenerator;

import java.util.HashSet;

public class PatternFragmentSet extends HashSet<PatternFragment>{

	public boolean containVerbString(String verbString) {
		
		for(PatternFragment pf: this  )
		{
			if(pf.toString().equals(verbString))
				return true;
		}
		return false;
	}
	
	public PatternFragment get(int index)
	{
		return this.get(index);
	}

	public void addPrevVerbsCount(String verbString) {
		for(PatternFragment pf: this  )
		{
			if(pf.toString().equals(verbString))
				pf.setPrevOccrCount(pf.getPrevOccrCount()+1);
		}
	}
	
	public PatternFragment getPatternFragment(String verbString)
	{
		for(PatternFragment pf: this  )
		{
			if(pf.toString().equals(verbString))
				return pf;
		}
		return null;
	}
	
	public double getAverageSizeOfPattern()
	{
		int total = 0;
		for(PatternFragment pf : this)
		{
			total = total + pf.getVerbList().size();
		}
		
		double avg = (double) total/this.size();
		return (double) (Math.round(avg*1000)) / 1000;
				
	}
	
	public double getAverageOccurenceOfPattern()
	{
		int total = 0;
		for(PatternFragment pf : this)
		{
			total = total + pf.getConditionalOccrCount();
		}
		
		double avg = (double) total/this.size();
		return (double) (Math.round(avg*1000)) / 1000;
				
	}

	
	public int getLargestSize()
	{
		int count = 0;
		for(PatternFragment pf : this)
		{
			int t = pf.getVerbList().size();
			if(t > count)
				count = t;
		}
		
		return count;
	}
	
	public int getOccringCount(String verb)
	{
		int count = 0;
		for(PatternFragment p : this)
		{
			for(String s : p.getVerbList())
			{
				if(s.equals(verb))
					count++;
			}
		}
		return count;
	}

}
