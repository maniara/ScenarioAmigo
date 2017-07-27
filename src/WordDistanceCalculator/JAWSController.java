package WordDistanceCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Resnik;

public class JAWSController {
	
	private WordNetDatabase database;
	private Resnik res;
	private static JAWSController controller;

	public static JAWSController getController()
	{
		if(controller == null)
		{
			controller = new JAWSController();
		}
		
		return controller;
				
	}
	private JAWSController()
	{
		//Wordnet directory
		String wordnetPath = getWordnetDirectory();
		String propPath = wordnetPath  + "//3.0//dict";
		
		//JAWS open
		if(wordnetPath != null)
		{
			System.setProperty("wordnet.database.dir", propPath);
		}
		database = WordNetDatabase.getFileInstance();
		
		//JWS open
		//String WNDir = "D:\\WordNet";
		JWS ws = new JWS(wordnetPath,"3.0");
		res = ws.getResnik();
	}
	
	private int getFreqIdx(String[] sarr, String word)
	{
		for(int i=0;i<sarr.length;i++)
		{
			String s = sarr[i];
			if(s.equals(word))
					return  i;
		}
		
		return 100;
	}
	
	private int getFreq(Synset ss, String word)
	{
		int freq = 0;
		
		freq = ss.getTagCount(ss.getWordForms()[getFreqIdx(ss.getWordForms(),word)]);
		if(freq == 0)
			return 1;
		else
			return freq;
	}
	
	public boolean isSynonym(String w1, String w2, double frequencyThreshold)
	{
		boolean result = false;
		Synset[] w1Synsets = database.getSynsets(w1, SynsetType.VERB);
		Synset[] w2Synsets = database.getSynsets(w2, SynsetType.VERB);
		
		int w1FullFreq = 0;
		int w2FullFreq = 0;
		
		for(Synset ss : w1Synsets)
		{
			int w1Freq = getFreq(ss,w1);
			//System.out.println(w1Freq);
			w1FullFreq = w1FullFreq + w1Freq;
		}
		
		for(Synset ss : w2Synsets)
		{
			w2FullFreq = w2FullFreq + getFreq(ss,w2);
		}
		
		for(Synset w1ss : w1Synsets)
		{
			for(Synset w2ss : w2Synsets)
			{
				double w1ssFreq = (double) getFreq(w1ss,w1)/w1FullFreq;
				double w2ssFreq = (double) getFreq(w2ss,w2)/w2FullFreq;
				
				if(w1ssFreq < frequencyThreshold || w2ssFreq < frequencyThreshold)
					continue;
				else
				{
					ArrayList<String> s1wf = new ArrayList<String>(Arrays.asList(w1ss.getWordForms()));
					ArrayList<String> s2wf = new ArrayList<String>(Arrays.asList(w2ss.getWordForms()));
					
					if(s1wf.contains(w2) && s2wf.contains(w1))
						return true;
				}
			}
		}
		
		
		return result;
		
	}
	
	public boolean isSynonym(String w1, String w2)
	{
		boolean result = false;
		Synset[] w1Synsets = database.getSynsets(w1, SynsetType.VERB);
		Synset[] w2Synsets = database.getSynsets(w2, SynsetType.VERB);
		
		int w1FullFreq = 0;
		int w2FullFreq = 0;
		
		for(Synset ss : w1Synsets)
		{
			int w1Freq = getFreq(ss,w1);
			//System.out.println(w1Freq);
			w1FullFreq = w1FullFreq + w1Freq;
		}
		
		for(Synset ss : w2Synsets)
		{
			w2FullFreq = w2FullFreq + getFreq(ss,w2);
		}
		
		for(Synset w1ss : w1Synsets)
		{
			for(Synset w2ss : w2Synsets)
			{
				double w1ssFreq = (double) getFreq(w1ss,w1)/w1FullFreq;
				double w2ssFreq = (double) getFreq(w2ss,w2)/w2FullFreq;
				
				ArrayList<String> s1wf = new ArrayList<String>(Arrays.asList(w1ss.getWordForms()));
				ArrayList<String> s2wf = new ArrayList<String>(Arrays.asList(w2ss.getWordForms()));
				
				if(s1wf.contains(w2) && s2wf.contains(w1))
					return true;
			}
		}
		
		
		return result;
		
	}
	
	
	public double getDistance(String w1, String w2)
	{
		//return maximum score among Average frequency ratio of two words * similarity each sense
		Synset[] w1Synsets = database.getSynsets(w1, SynsetType.VERB);
		Synset[] w2Synsets = database.getSynsets(w2, SynsetType.VERB);
		
		int w1FullFreq = 0;
		int w2FullFreq = 0;
		
		for(Synset ss : w1Synsets)
		{
			int w1Freq = getFreq(ss,w1);
			//System.out.println(w1Freq);
			w1FullFreq = w1FullFreq + w1Freq;
		}
		
		for(Synset ss : w2Synsets)
		{
			w2FullFreq = w2FullFreq + getFreq(ss,w2);
		}
		
		//System.out.println("FF : " + fullFreq);
		

		double max = 0.0;
		for(int i=0;i<w1Synsets.length;i++)
		{
			Synset w1Ss = w1Synsets[i];
			int w1Freq = getFreq(w1Ss,w1);
			for(int j=0;j<w2Synsets.length;j++)
			{
				Synset w2Ss = w2Synsets[j];
				int w2Freq = getFreq(w2Ss,w2);
				double freq = (((double) w1Freq/w1FullFreq) + ((double) w2Freq/w2FullFreq)) * 0.5;
				//System.out.println(w1Freq+":"+w2Freq+":"+fullFreq+":"+freq);
				
				double similarity = 0.0;
				try{
					similarity = (double)( res.res(w1, i+1, w2,j+1,"v") / 10.0);
					//similarity = (double)res.res(w1, i+1, w2,j+1,"v");
				}catch (Exception e){continue;}
				catch(StackOverflowError so){continue;}
				
				double score =  similarity * freq;
				//System.out.println(similarity + ":"+score);
				if(score > max)
				{
					//System.out.println(similarity + ":"+score);
					max = score;
				}
			}
		}
		
		return 1.0 - max;//(10.0 - max)/10.0;
	}
	
	
	public static void main(String args[])
	{
		JAWSController jc = new JAWSController();

		/*Synset[] synsets = jc.database.getSynsets("enter", SynsetType.VERB);
		
		for(Synset ss : synsets)
		{
			System.out.println(ss.getWordForms()[0]+":"+ss.getTagCount(ss.getWordForms()[getFreq(ss.getWordForms(),"enter")]));
		}*/
		

		System.out.println(jc.getDistance("browse", "search"));
		System.out.println(jc.getDistance("complete", "finish"));
		
	}
	
	public String getWordnetDirectory()
	{
		String path1 = "D:\\WordNet";
		String path2 = "C:\\Program Files (x86)\\WordNet";
		
		File f1 = new File(path1);
		File f2 = new File(path2);
	    // 파일 존재 여부 판단
	    if (f1.isDirectory()) {
	      return path1;
	    }
	    else if(f2.isDirectory()) {
	      return path2;
	    }
	    return null;
	}

}
