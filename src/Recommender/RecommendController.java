package Recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.custom.CTabFolder;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import Entity.Flow;
import Entity.InteractionString;
import Entity.Sentence;
import GUI.AlternativeFlowTabItem;
import MySQLDataAccess.FlowAccessor;

public class RecommendController 
{
	private ArrayList<Sentence> basicFlowSentences;
	private static LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
	private static StanfordCoreNLP pipeline;
	//private ArrayList<Flow> patternList;
	
	public RecommendController()
	{
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize,ssplit, parse");
	    pipeline = new StanfordCoreNLP(props);
	}
	
	
	public void getAlternative(CTabFolder AFTableFolder, ArrayList<AlternativeFlowTabItem> AFItemList, ArrayList<Sentence> basicFlowSentences) 
	{
		//patternList = new FlowAccessor().getFlowList("Pattern", "%", 'p');
		this.basicFlowSentences = basicFlowSentences;
		//Get Verb
		for(Sentence s : basicFlowSentences)
		{
			s.setVerb((getVerb(s.getSentenceContents())));
		}
		
		InteractionRouteMaker IRM = new InteractionRouteMaker(basicFlowSentences);
		HashSet<InteractionString> routeList = new HashSet<InteractionString>(IRM.getInteractionRouteList()); 
		
		//print candidates
		System.out.println("--- Candidate patterns ---");
		//Collections.sort(routeList);
		ArrayList<String> cList = new ArrayList<String>();
		for(InteractionString is : routeList)
		{
			System.out.println(is.toString());
			/*if(!cList.contains(is.toString()))
			{
				cList.add(is.toString());
				
			}*/
		}
		
		ArrayList<InteractionString> patternSet = OptimalPatternCalculator.getOptimalRoute(this.basicFlowSentences.size(),routeList);
		Collections.sort(patternSet);
		
		//print candidates
		System.out.println("--- Optimal patterns ---");
		for(InteractionString is : patternSet)
		{
			System.out.println(is.toString());
		}
		
		for(InteractionString is: patternSet)
		{
			if(is.getPatternID().equals("EMPTY"))
				continue;
			ArrayList<Flow> AF = getAlternativeFlow(is,new FlowAccessor().getFlowList("Pattern", is.getPatternID(), 'p'));
			
			for(Flow f : AF)
			{
				AlternativeFlowTabItem AFItem = new AlternativeFlowTabItem(AFTableFolder, f,false);
				AFItemList.add(AFItem);
			}
		}
		/*for(InteractionString is : patternSet)
		{
			System.out.println(is.getFrom());
			System.out.println(is.getPatternID());
			System.out.println(is.getTo());
		}*/
	}

	private ArrayList<Flow> getAlternativeFlow(InteractionString is, ArrayList<Flow> flowList) 
	{
		ArrayList<Flow> AFList = new ArrayList<Flow>();
		Flow patternBasicFlow = null;
		for(Flow f : flowList)
		{
			if(!f.getIsAlternative())
			{
				patternBasicFlow = f;
			}
		}
		
		for(int i=0, j=0 ;i<is.getSentenceList().size();)
		{
			String ISVerb = is.getSentenceList().get(i);
			String PVerb = patternBasicFlow.getSentenceList().get(j).getSentenceContents();
			int startNum = is.getFrom()+j+1;
			if(ISVerb.equals(PVerb))
			{
				for(Flow af : flowList)
				{
					if(af.getIsAlternative() && af.getStartOrder().startsWith(patternBasicFlow.getSentenceList().get(j).getSentenceOrder()))
					{
						//Get the flow
						Flow alternativeFlow = new Flow(af);
						alternativeFlow.changeOrder(patternBasicFlow.getSentenceList().get(j).getSentenceOrder(),startNum+"");
						AFList.add(alternativeFlow);
					}

				}
				i++;
				j++;
			}
			else if(patternBasicFlow.getSentenceList().get(j).isOptional())
			{
				j++;
				continue;
			}
			else if(patternBasicFlow.getSentenceList().get(j).isRepeatable())
			{
				j=patternBasicFlow.getFirstRepeat();
				continue;
			}
		}
		return AFList;
	}

	private void printList(ArrayList<InteractionString> is)
	{
		for(int i=0;i<is.size();i++)
			System.out.println(is.get(i).getInteractionString());
	}

	private void getMatchedPattern(HashMap<String, ArrayList<InteractionString>> candidatePattern) 
	{
		Iterator<String> mapIt = candidatePattern.keySet().iterator();
		
		while(mapIt.hasNext())
		{
			String key = mapIt.next();
			//PatternAccessor PA = new PatternAccessor();
			//Pattern p = PA.getPattern(key);
			ArrayList<InteractionString> tmpList = candidatePattern.get(key);
			for(InteractionString is : tmpList)
			{
				for(int i=0;i<is.getSentenceList().size();i++)
				{
					
				}
			}
		}
		
	}
	
	public Tree parseSentence(String text)
	{
		if(!text.endsWith("."))
			text = text + ".";
	    // read some text in the text variable
	    //String text = "Users press the key"	+ "";
	    
	    // create an empty Annotation just with the given text
	    Annotation document = new Annotation(text);
	    Tree tree =null;
	    
	    // run all Annotators on this text
	    pipeline.annotate(document);
	    
	    // these are all the sentences in this document
	    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	    
	    for(CoreMap sentence: sentences) {
	      // traversing the words in the current sentence
	      // a CoreLabel is a CoreMap with additional token-specific methods
	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	        // this is the text of the token
	        String word = token.get(TextAnnotation.class);
	        // this is the POS tag of the token
	        String pos = token.get(PartOfSpeechAnnotation.class);
	        // this is the NER label of the token
	        String ne = token.get(NamedEntityTagAnnotation.class);       
	      }

	      // this is the parse tree of the current sentence
	      tree = sentence.get(TreeAnnotation.class);
	      
	      //System.out.println(tree);

	      // this is the Stanford dependency graph of the current sentence
	      //SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
	    }
	    return tree;
	}


	public String getVerb(String sentence)
	{
		String[] sent = sentence.split(" ");
	    ArrayList<String> sentList = new ArrayList<String>();
	    Collections.addAll(sentList, sent);
	    /*List<CoreLabel> rawWords = new ArrayList<CoreLabel>();
	    for (String word : sent) {
	      CoreLabel l = new CoreLabel();
	      l.setWord(word);
	      rawWords.add(l);
	    }
	    Tree parse = lp.apply(rawWords);
	    
	    System.out.println(parse);*/
	    
	    Tree tmpTree = parseSentence(sentence).getChild(0);
	    
	    boolean hasVerb = false;
    
	    while(true)
	    {
	    	for(Tree t:tmpTree.getChildrenAsList())
	    	{
	    		if(t.value().startsWith("V"))
	    		{
	    			hasVerb = true;
	    			tmpTree=t;
	    			break;
	    		}
	    		if(sentList.contains(t.value().trim()))
	    		{
	    			tmpTree=t;
	    			break;
	    		}
	    		tmpTree=t;
	    	}
	    	
    		if(sentList.contains(tmpTree.value().trim()))
    		{
    			break;
    		}
	    }
	    
		if(hasVerb)
		{
			//System.out.println(tmpTree.value());
			return tmpTree.value();
		}
		else
		{
		    System.out.println("'"+sentence+"' has no verb");
		    return null;

		}
	}
}
