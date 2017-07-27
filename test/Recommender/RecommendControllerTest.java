package Recommender;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

public class RecommendControllerTest {
	
	@Test
	public void getVerbTest()
	{
		//'The system requests the PIN' has no verb
		//'The system displays amount' has no verb
		String v = new RecommendController().getVerb("A user inserts a card.");
		System.out.println(v);
		//assertTrue(v.equals("presses"));
	}
	
	@Test
	public void parseSentenceTest()
	{
		System.out.println(new RecommendController().parseSentence("The system displays the amount"));
	}
}
