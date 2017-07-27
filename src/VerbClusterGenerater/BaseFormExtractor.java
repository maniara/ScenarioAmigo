package VerbClusterGenerater;

import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.impl.file.Morphology;

public class BaseFormExtractor {
	public static String getBaseVerbForm(String verb)
	{
		System.setProperty("wordnet.database.dir", "D:\\WordNet\\3.0\\dict\\");
		WordNetDatabase database = WordNetDatabase.getFileInstance();
 
		Morphology id = Morphology.getInstance();
 
		String[] arr = id.getBaseFormCandidates(verb, SynsetType.VERB);
 
		return arr[0];
	}
	

}
