package Recommender;

import edu.smu.tspell.wordnet.*;
import rita.wordnet.RiWordnet;

public class WordSimilarityCalculator {
	
	/*public static void main(String[] args)
	{
		System.out.println(getSimilarity("offer","offer"));
	}*/
	
	private WordNetDatabase db;
	private RiWordnet wordnet;
	
	public WordSimilarityCalculator(){
	
		System.setProperty("wordnet.database.dir","D:\\WordNet\\2.1\\dict\\");
		db = WordNetDatabase.getFileInstance();
		
    	wordnet = new RiWordnet();
    	wordnet.setWordnetHome("C:\\Program Files (x86)\\WordNet\\2.0\\dict\\");
	}
	
	public float getReward(String word1, String word2)
	{
		if(word1.equals(word2))
			return (float) 2.0;
		else if(isSynonym(word1,word2))
			return (float) 1.5;
		else 
		{
			float sim = getSimilarity(word1,word2);
			if(sim == 0.0f)
				sim = 0.01f;
			
			return sim;
		}
	}
	
	private boolean isSynonym(String word1, String word2)
	{
		System.setProperty("wordnet.database.dir","D:\\WordNet\\2.1\\dict\\");
		WordNetDatabase db = WordNetDatabase.getFileInstance();

		Synset[] syn;
		String[] usage;
		
		syn = db.getSynsets(word1);
		
		for (int i = 0; i < syn.length; i++) {
			//System.out.println("Retrieved definition: "	+ syn[i].getDefinition());
			usage = syn[i].getWordForms();
			if (usage.length > 0) // if an example usage exists
			{
				for (int j = 0; j < usage.length; j++) {
					if(usage[j].equals(word2))
						return true;
				}
			}
		}
		return false;
	}
	
	private float getSimilarity(String word1, String word2)
	{
    	String word = "first name";
    	String pos = wordnet.getBestPos(word);
    	pos = wordnet.getBestPos(word1);
    	
    	float distance = wordnet.getDistance(word1, word2, pos);
    	//System.out.println(distance);
    	return (float) ((float)1.0 - distance);
	}
}
	
		

