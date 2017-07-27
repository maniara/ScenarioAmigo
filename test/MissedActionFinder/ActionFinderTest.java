package MissedActionFinder;

import java.util.ArrayList;

import org.junit.Test;

import Entity.Sentence;

public class ActionFinderTest {
	@Test
	public void getPatternRoadWithSimilarityTest()
	{
		String sentenceListString = "u:request-s:modify-s:display";
		String patternString = "";
		
		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		
		for(String sent : sentenceListString.split("-"))
		{
			String type = sent.split(":")[0];
			String verb = sent.split(":")[1];
			
			sentenceList.add(new Sentence(type,verb));
		}
		
		//PatternFragment pf = new PatternFragment()
		
		
		
		
	//	new ActionFinderController().getPatternRoadWithSimilarity();
	}

}
