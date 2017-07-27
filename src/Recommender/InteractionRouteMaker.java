package Recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import Entity.InteractionString;
import Entity.Sentence;
import MySQLDataAccess.SentenceAccessor;

public class InteractionRouteMaker
{
	private Stack<InteractionString> interactionStack;
	private ArrayList<InteractionString> interactionStringList;
	private String basicFlowString;
	private HashMap<String, ArrayList<InteractionString>> patternInteractionStringMap;
	private ArrayList<Sentence> basicFlowSentences;
	WordSimilarityCalculator calculator = new WordSimilarityCalculator();
	
	public InteractionRouteMaker(ArrayList<Sentence> basicFlowSentences)
	{
		this.basicFlowSentences = basicFlowSentences;
		basicFlowString = getInteractionList(basicFlowSentences);
		interactionStack = new Stack<InteractionString>();
		interactionStringList = new ArrayList<InteractionString>();
		SentenceAccessor SA = new SentenceAccessor();
		patternInteractionStringMap = SA.getInteractionStringMap();
		calculator = new WordSimilarityCalculator();
	}
	
	public ArrayList<InteractionString> getInteractionRouteList()
	{
		this.getCandidatePattern(0);
		while(!this.interactionStack.empty())
		{
			//System.out.println(this.interactionStack.size());
			InteractionString is = interactionStack.pop();
			if(is.getTo() == basicFlowSentences.size() || is.getFrom() == basicFlowSentences.size())
				continue;
			getCandidatePattern(is.getTo());
		}
		
		System.out.println("Candidate size:"+interactionStringList.size());
		
		removeSameRoute();
		
		System.out.println("Candidate size:"+interactionStringList.size());
		
		for(InteractionString is:interactionStringList )
		{
			//System.out.println(is);
			setWeight(is);
			
			//System.out.println(is);
		}
		
		removeSmallerRoute();
		
		return interactionStringList;
	}
	
	private void removeSmallerRoute()
	{
		ArrayList<InteractionString> removeList = new ArrayList<InteractionString>();
		
		for(InteractionString is : interactionStringList)
		{
			for(InteractionString ris : interactionStringList)
			{
				if(ris.getFrom() == is.getFrom() && ris.getTo() == is.getTo())
				{
					if(is.getReward() > ris.getReward())
					{
						removeList.add(ris);
					}
				}

			}
		}
		
		for(InteractionString is : removeList)
		{
			interactionStringList.remove(is);
		}
	}
	
	private void removeSameRoute()
	{
		ArrayList<InteractionString> removeList = new ArrayList<InteractionString>();
		
		for(InteractionString is : interactionStringList)
		{
			for(InteractionString ris : interactionStringList)
			{
				if(ris.toString().equals(is))
				{
					removeList.add(ris);
				}
			}
		}
		
		for(InteractionString is : removeList)
		{
			interactionStringList.remove(is);
		}
		
		
	}
	
	private void setWeight(InteractionString is) 
	{
		if(!is.getPatternID().equals("EMPTY"))
		{
			float w = 0.0f;
			for(int i=0, j=is.getFrom() ; i<is.getSentenceList().size();i++,j++)
			{
				String basicFlowVerb = basicFlowSentences.get(j).getVerb();
				if(is.getSentenceList().get(i).contains("/"))
				{
					float tempScore = 0.0f;
					for(int k=0;k<is.getSentenceList().get(i).split("/").length;k++)
					{
						String s = is.getSentenceList().get(i).split("/")[k];
						//System.out.println(s+":"+basicFlowVerb);
						float reward = calculator.getReward(s, basicFlowVerb);
						//System.out.println("Reward : "+reward+" tmpscre:"+tempScore);
						if(reward>tempScore)
							tempScore = reward;
					}
					w = w + tempScore;
				}
				
				else
				{
					//System.out.println(is.getPatternID());
					//System.out.println(i+":"+j);
					//System.out.println(is.getSentenceList().get(i)+":"+basicFlowVerb);
					float reward = calculator.getReward(is.getSentenceList().get(i), basicFlowVerb);
					//System.out.println(reward);
					w = w + reward;
				}
			}
			is.addReward(w);
		}
	}

	private void getCandidatePattern(int from) 
	{
		String bip = basicFlowString.substring(from);
		//ArrayList<InteractionString> retList = new ArrayList<InteractionString>();
		//ArrayList<InteractionString> candidateList = new ArrayList<InteractionString>();
		
		Iterator<String> mapIt = patternInteractionStringMap.keySet().iterator();
		ArrayList<InteractionString> candidateList = new ArrayList<InteractionString>();
		
		while(mapIt.hasNext())
		{
			String key = mapIt.next();
			ArrayList<InteractionString> tmpList = patternInteractionStringMap.get(key);

			for(InteractionString s:tmpList)
			{
				if(comparePattern(bip,s.getInteractionString()))
				{
					InteractionString tmpis = new InteractionString(key,s.getInteractionString(),s.getSentenceList());
					tmpis.setFrom(from);
					tmpis.setTo(from+s.getInteractionString().length());
					candidateList.add(tmpis);
					if(s.getTo() < basicFlowString.length())
						this.interactionStack.push(tmpis);
				}
			}
		}
		
		if(candidateList.isEmpty())
		{
			InteractionString emptyString = new InteractionString();
			emptyString.setFrom(from);
			emptyString.setTo(from+1);
			emptyString.setPatternID("EMPTY");
			emptyString.addReward(0.01f);
			candidateList.add(emptyString);
			if(emptyString.getTo() < basicFlowString.length())
				this.interactionStack.push(emptyString);
		}
		
		interactionStringList.addAll(candidateList);
	}
	
	//public static void main(String args[])
	private boolean comparePattern(String bip, String s) 
	{
		//String bip ="uususs";
		//String s = "uUSu";
		if(bip.startsWith(s) || bip.startsWith(s.toLowerCase()))
			return true;
			//System.out.println("true");
		else
		{
			if(hasRepeatable(s))
			{
				while(s.length()<=bip.length())
				{
					s=getRepeatString(s);
					//System.out.println("cp:"+s);
					if(bip.startsWith(s.toLowerCase()))
						return true;
				}
			}
		}
		return false;
	}
	

	private static boolean hasRepeatable(String s)
	{
		char[] ch = s.toCharArray();
		for(char c :ch)
		{
			if(c == 'U' || c == 'S')
				return true;
			else
				continue;
		}
		return false;
	}
	
	
	//public static void main(String args[])
	private static String getRepeatString(String s) 
	{
		//String s = "uUSUu";
		//int index = 0;
		char[] ch = s.toCharArray();
		String repeatPart = "";
		for(int i=0;i<ch.length;i++)
		{
			if(ch[i] == 'U' || ch[i] == 'S')
			{
				//index = i;
				repeatPart = repeatPart + ch[i];
				i++;
				while(ch[i] == 'U' || ch[i] == 'S')
				{
					repeatPart = repeatPart + ch[i];
					i++;
				}
			}
		}
		
		String[] sa = s.split(repeatPart);
		return sa[0]+repeatPart.toLowerCase()+repeatPart+sa[1];
		//System.out.println(sa[0]+repeatPart.toLowerCase()+repeatPart+sa[1]);
	}
	

	private String getInteractionList(ArrayList<Sentence> basicFlowSentences) 
	{
		String retStr = null;
		for(Sentence sen : basicFlowSentences)
		{
			if(retStr == null)
				retStr = ""+sen.getSentenceType();
			
			else
				retStr = retStr+sen.getSentenceType();
		}
		
		return retStr;
	}

}
