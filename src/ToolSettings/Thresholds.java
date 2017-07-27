package ToolSettings;

public class Thresholds {
	
	//Verb Clustering
	public static double Clusteing_High_Freq_Criteria = 0.01;
	public static double Word_Min_Frequency_On_Similarity = 0.2;
	public static double Word_Max_Distance = 0.5;
	
	//Drawing Graph
	//Edge weight Share
	public static double RI_Share = 0.7;
	public static double FrequencyFactor_Share = 0.3;
	//Minimum Weight
	public static double Edge_Weight_Threshold = 0.2;
	//Filtering Vertex
	public static double Graph_Verb_Occr_Criteria = 0.15;
	public static double Graph_Min_RI = 0.05;
	
	//Making Patterns
	public static double[] Weight_Of_PatternWeight_COUNT_AVGRI_LENGHT={0.1,0.1,0.8};
	
	//Action Finding
	public static double EP_supporter = 1;
	public static double Matched_Pattern_Min_Equal_Rate = 0.3;
	public static double[] Weight_Of_Scenario_Similarity_EQUALITY_PATTERNSCORE={0.2,0.8};
	
}