package WordDistanceCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class VerbDistanceList extends ArrayList<Distance>{
	
	private static ArrayList<Distance> distanceList;
	private static HashSet<String> verbList;
	
	public static void makeDistanceList(HashSet<Distance> distance) {
		distanceList = new ArrayList<Distance>(distance);
		verbList = new HashSet<String>();
		for(Distance d : distance )
		{
			verbList.add(d.verb1);
			verbList.add(d.verb2);
		}
		
		Collections.sort(distanceList);
	}

	public static HashSet<String> getVerbList() {
		return verbList;
	}

	public static double getDistance(String w1, String w2)
	{
		for(Distance d : distanceList)
		{
			if((d.verb1.equals(w1) && d.verb2.equals(w2)) || (d.verb1.equals(w2) && d.verb2.equals(w1)))
			{
				return d.distance;
			}
		}
		
		return 10.0;
	}

}
