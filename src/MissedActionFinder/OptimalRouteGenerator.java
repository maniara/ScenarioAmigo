package MissedActionFinder;

import java.util.ArrayList;

public class OptimalRouteGenerator 
{
	private static ArrayList<PatternPathRoad> routeList;
	private static PatternPathRoadList[][] pathArray;
	private static int length;
	private static float m;

	
	public static ArrayList<PatternPathRoad> getOptimalRoute(int len, ArrayList<PatternPathRoad> roadList)
	{
		length = len+1;
		//System.out.println("length:"+len);
		routeList = roadList;
		pathArray = new PatternPathRoadList[length][length];
		makeWeightMatrix();
		
		//longest path from 0 to i
		for(int i=2; i<length;i++)
		{
			double stopLast,direct;
			for(int j=1 ; j<i; j ++)
			{
				try{
					stopLast = pathArray[0][j].getTotalWeight() + pathArray[j][i].getTotalWeight();
				}
				catch(NullPointerException e)
				{
					continue;
				}
				
				try{
					direct = pathArray[0][i].getTotalWeight();
				}
				catch(NullPointerException e)
				{
					PatternPathRoadList newList = mergyRoad(pathArray[0][j],pathArray[j][i]); 
					pathArray[0][i] = newList;
					continue;
				}
				
				if(stopLast > direct)
				{
					PatternPathRoadList newList = mergyRoad(pathArray[0][j],pathArray[j][i]); 
					pathArray[0][i] = newList;
				}
			}
		}
		
		//System.out.println("--- Merged ---");
		for(int k=0;k<pathArray.length;k++)
		{
			PatternPathRoadList[] ais = pathArray[k];
			for(int l=0;l<ais.length;l++)
			{
				PatternPathRoadList isl = ais[l];
				if(isl != null)
				{
					String txt = "["+k+","+l+"]";
					for(PatternPathRoad is : isl)
					{
						txt = txt + "," + is;
					}
					//System.out.println(txt);
				}
					
			}
		}
		
		ArrayList<PatternPathRoad> optIs = new ArrayList<PatternPathRoad>();
		
		for(PatternPathRoad is : pathArray[0][len])
		{
			optIs.add(is);
		}
		
//		//print matched patterns
//		for(PatternPathRoad ois:optIs)
//		{
//			System.out.println(ois);
//		}
//		
		return optIs;
	}

	
	private static PatternPathRoadList mergyRoad(
			PatternPathRoadList isl1,
			PatternPathRoadList isl2) {
		PatternPathRoadList isl = new PatternPathRoadList(isl1.getFrom(),isl2.getTo());
		isl.addAll(isl1);
		isl.addAll(isl2);
		isl.setTotalWeight(isl1.getTotalWeight() + isl2.getTotalWeight());

		return isl;
	}


	private static int longestPath(int vertex, boolean visited[], int adjMatrix[][]) {
        int dist, max = 0;
        visited[vertex] = true;

        for (int u = 0; u < adjMatrix[vertex].length; u++) {
            if (adjMatrix[vertex][u] != -1) {
                if (!visited[u]) {
                    dist = adjMatrix[vertex][u] + longestPath(u, visited, adjMatrix);
                    if (dist > max) {
                        max = dist;
                    }
                }
            }
        }
        visited[vertex] = false;
        return max;
    }
	
	private static void makeWeightMatrix()
	{
		for(PatternPathRoad is : routeList)
		{
			int from = is.getFrom();
			int to = is.getTo();
			if(from>length ||to>length)
				continue;
			if(pathArray[from][to] == null)
				pathArray[from][to] = new PatternPathRoadList(from,to,is);
			else
			{
				if(pathArray[from][to].getTotalWeight()<is.getWeight())
					pathArray[from][to] = new PatternPathRoadList(from,to,is);
			}
		}
		
		/*for(int i=0;i<length;i++)
		{
			for(int j=0;j<length;j++)
			{
				if(i==j)
				{
					 InteractionString tmpIs =  new InteractionString();
					 //tmpIs.addReward(m);
					 isArray[i][j] = tmpIs;
				}
				if(isArray[i][j] == null)
				{
					 InteractionString tmpIs =  new InteractionString();
					 tmpIs.addReward(m);
					 isArray[i][j] = tmpIs;
				}
				else
				{
					//for shortest path
					isArray[i][j].setReward(isArray[i][j].getReward());
				}
			}
		}*/
		
		//System.out.println("--- Path weight ---");
		/*for(PatternPathRoadList[] ais : pathArray)
		{
			for(PatternPathRoadList is : ais)
			{
				if(is != null)
					System.out.println(is.get(0).toString());
			}
		}*/
	}

	
	/*private static void printArray()
	{
		for(int i=0;i<routeList.size();i++)
			System.out.println(routeList.get(i).getReward());
		
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<length;j++)
			{
				System.out.print(isArray[i][j].getReward()+" ");
			}
			System.out.println();
		}
	}*/

}
