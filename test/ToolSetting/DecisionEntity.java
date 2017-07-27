package ToolSetting;

public class DecisionEntity {
	double CP;
	double RI;
	double LEN;
	
	double EP;
	double PS;
	
	int TryNum;
	int ExtNum;
	int CorNum;

		
	public DecisionEntity(double cP, double rI, double lEN, double eP, double pS, int tryNum, int extNum,
			int corNum) {
		super();
		CP = cP;
		RI = rI;
		LEN = lEN;
		EP = eP;
		PS = pS;
		TryNum = tryNum;
		ExtNum = extNum;
		CorNum = corNum;
	}
	
	@Override
	public String toString()
	{
		return CP+":"+RI+":"+LEN+":"+EP+":"+PS+":"+TryNum+":"+ExtNum+":"+CorNum;
	}

	

}
