package Recommender;

import java.util.ArrayList;
import java.util.HashSet;

import Entity.InteractionString;
import Entity.InteractionStringList;

public class OptimalPatternCalculator 
{
	private static HashSet<InteractionString> routeList;
	private static InteractionStringList[][] isArray;
	private static int length;
	private static float m;

	
	public static ArrayList<InteractionString> getOptimalRoute(int len, HashSet<InteractionString> list)
	{
		length = len+1;
		System.out.println("length:"+len);
		routeList = list;
		isArray = new InteractionStringList[length][length];
		makeWeightMatrix();
		
		//longest path from 0 to i
		for(int i=2; i<length;i++)
		{
			if(i == 8)
				System.out.println("");
			double stopLast,direct;
			for(int j=1 ; j<i; j ++)
			{
				try{
					stopLast = isArray[0][j].getTotalweight() + isArray[j][i].getTotalweight();
				}
				catch(NullPointerException e)
				{
					continue;
				}
				
				try{
					direct = isArray[0][i].getTotalweight();
				}
				catch(NullPointerException e)
				{
					InteractionStringList newList = mergyInteractionStringList(isArray[0][j],isArray[j][i]); 
					isArray[0][i] = newList;
					continue;
				}
				
				if(stopLast > direct)
				{
					InteractionStringList newList = mergyInteractionStringList(isArray[0][j],isArray[j][i]); 
					isArray[0][i] = newList;
				}
			}
		}
		
		System.out.println("--- Merged ---");
		for(int k=0;k<isArray.length;k++)
		{
			InteractionStringList[] ais = isArray[k];
			for(int l=0;l<ais.length;l++)
			{
				InteractionStringList isl = ais[l];
				if(isl != null)
				{
					String txt = "["+k+","+l+"]";
					for(InteractionString is : isl)
					{
						txt = txt + "," + is;
					}
					System.out.println(txt);
				}
					
			}
		}
		
		ArrayList<InteractionString> optIs = new ArrayList<InteractionString>();
		
		for(InteractionString is : isArray[0][len])
		{
			optIs.add(is);
		}
		
		return optIs;
	}

	
	private static InteractionStringList mergyInteractionStringList(
			InteractionStringList isl1,
			InteractionStringList isl2) {
		InteractionStringList isl = new InteractionStringList(isl1.getFrom(),isl2.getTo());
		isl.addAll(isl1);
		isl.addAll(isl2);
		isl.setTotalWeight(isl1.getTotalweight() + isl2.getTotalweight());

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
	
	//shortest path based (buggy)
	/*public static ArrayList<InteractionString> getOptimalRoute1(int len, ArrayList<InteractionString> list) 
	{
		m = 1000f;
		length = len+1;
		routeList = list;
		
		isArray = new InteractionString[length][length];
		makeWeightMatrix();
		//printArray();
		
		//Dikstra Algorithm
		InteractionString[] via = new InteractionString[length];
		int[] v = new int[length];
		float[] distance = new float[length];
		
		for (int j = 0; j < length; j++) 
		{
			v[j] = 0;
			distance[j] = m;
		}
		
		distance[0] = 0;
		float min;
		int k = 0;
		
		for(int i=0;i<length;i++)
		{
			min = m;
			//k = 0;
			for(int j=0;j<length;j++)
			{
				if(v[j] == 0 && distance[j] < min)
				{
					k=j;
					min = distance[j];
				}
			}
			
			v[k] = 1;
			
			if(min == m)
				break;
			
			for(int j=0;j<length;j++)
			{
				if(distance[j] > distance[k] + isArray[k][j].getReward())
				{
					distance[j] = distance[k] + isArray[k][j].getReward();
					via[j] = isArray[k][j];
				}
			}
		}
		
		//System.out.println("distance = "+distance[6]);
		
		ArrayList<InteractionString> retList = new ArrayList<InteractionString>();
		k = length - 1;
		while(true)
		{
			for(InteractionString i : via)
			{
				if(i == null)
					continue;
				if(i.getTo() == k)
				{
					retList.add(i);
					k=i.getFrom();
				}
			}
			if(k == 0)
				break;
		}
		return retList;
	}*/
	
	private static void makeWeightMatrix()
	{
		for(InteractionString is : routeList)
		{
			int from = is.getFrom();
			int to = is.getTo();
			if(from>length ||to>length)
				continue;
			if(isArray[from][to] == null)
				isArray[from][to] = new InteractionStringList(from,to,is);
			else
			{
				if(isArray[from][to].getTotalweight()<is.getReward())
					isArray[from][to] = new InteractionStringList(from,to,is);
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
		
		System.out.println("--- Path weight ---");
		for(InteractionStringList[] ais : isArray)
		{
			for(InteractionStringList is : ais)
			{
				if(is != null)
					System.out.println(is.get(0).toString());
			}
		}
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
