package PatternGenerator;

import org.junit.Test;

public class PatternFragmentTest {
	
	@Test
	public void getWeightByArcTanTest()
	{
		System.out.print("ArcTan : ");
		System.out.println(new PatternFragment().getWeightByArcTan(3, 9));
	}
	
	@Test
	public void getWeightBySoftsignTest()
	{
		System.out.print("Softsign : ");
		System.out.println(new PatternFragment().getWeightBySoftsign(3, 9));
	
	}
	
	@Test
	public void getWeightBySigmoidFunctionTest()
	{
		System.out.print("Sigmoid : ");
		System.out.println(new PatternFragment().getWeightBySigmoidFunction(3, 9));
	
	}

}
