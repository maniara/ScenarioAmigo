package Validator;

public class Result {
	private String projectCode;
	private int tryNum;
	private int extracted;
	private int correct;
	
	public Result(int tryNum, int extracted, int correct) {
		super();
		this.tryNum = tryNum;
		this.extracted = extracted;
		this.correct = correct;
	}
	
	

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}



	public int getTryNum() {
		return tryNum;
	}

	public int getExtracted() {
		return extracted;
	}

	public int getCorrect() {
		return correct;
	}
	
	public String toString()
	{
		return tryNum+":"+extracted+":"+correct;
	}
	
	public String getProjectCode()
	{
		return this.projectCode;
	}
	
}
