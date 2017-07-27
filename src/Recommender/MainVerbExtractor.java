package Recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class MainVerbExtractor {
	
	private StanfordCoreNLP pipeline;
	
	public MainVerbExtractor()
	{
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize,ssplit, parse");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public String getMainVerb(String sentence)
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
    		
    		else if(tmpTree.getChildrenAsList().size() == 0 && hasVerb == false)
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
	
	private Tree parseSentence(String text)
	{
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		
		if(!text.endsWith("."))
			text = text + ".";
	    // read some text in the text variable
	    //String text = "Users press the key"	+ "";
	    Tree tree =null;
	    
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

}
