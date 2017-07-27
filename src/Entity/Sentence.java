package Entity;

public class Sentence implements Comparable<Sentence> {
	private String sentenceNum;
	private String projectID;
	private String usecaseID;
	private String flowID;
	private String sentenceOrder;
	private String sentenceContents;
	private char sentenceType;
	private Integer sentenceSeq;
	private boolean isRepeatable;
	private boolean isOptional;
	private String verb;
	private String representVerb;
	private String prevType;
	private String nextType;
	private boolean hasRepresentive; // using in just sequence pattern
										// generation
	private String isSample;

	public Sentence(String sentenceType, String representVerb) {
		this.sentenceContents = sentenceType;
		this.representVerb = representVerb;
	}

	public Sentence(String projectID, String usecaseID, String flowID, String sentenceOrder, String sentenceContents,
			char sentenceType, int sentenceSeq, boolean isRepeatable, boolean isOptional, String isSample) {
		this.flowID = flowID;
		this.projectID = projectID;
		this.usecaseID = usecaseID;
		this.sentenceContents = sentenceContents;
		this.sentenceOrder = sentenceOrder;
		this.sentenceType = sentenceType;
		this.sentenceSeq = new Integer(sentenceSeq);
		this.isOptional = isOptional;
		this.isRepeatable = isRepeatable;
		this.hasRepresentive = false;
		this.isSample = isSample;
	}

	public Sentence(String sentenceNum, String projectID, String usecaseID, String flowID, String sentenceOrder,
			String sentenceContents, char sentenceType, int sentenceSeq, boolean isRepeatable, boolean isOptional, String isSample) {
		this.sentenceNum = sentenceNum;
		this.flowID = flowID;
		this.projectID = projectID;
		this.usecaseID = usecaseID;
		this.sentenceContents = sentenceContents;
		this.sentenceOrder = sentenceOrder;
		this.sentenceType = sentenceType;
		this.sentenceSeq = new Integer(sentenceSeq);
		this.isOptional = isOptional;
		this.isRepeatable = isRepeatable;
		this.hasRepresentive = false;
		this.isSample = isSample;
	}
	
	

	public String getIsSample() {
		return isSample;
	}

	public void setIsSample(String isSample) {
		this.isSample = isSample;
	}

	public boolean hasRepresentive() {
		return hasRepresentive;
	}

	public void setRepresentive(boolean hasRepresentive) {
		this.hasRepresentive = hasRepresentive;
	}

	public String getVerb() {
		return this.verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getProjectID() {
		return this.projectID;
	}

	public String getUseCaseID() {
		return this.usecaseID;
	}

	public String getFlowID() {
		return this.flowID;
	}

	public String getSentenceOrder() {
		return this.sentenceOrder;
	}

	public String getSentenceContents() {
		return this.sentenceContents;
	}

	public char getSentenceType() {
		return this.sentenceType;
	}

	public int getSentenceSeq() {
		return this.sentenceSeq;
	}

	public boolean isOptional() {
		return this.isOptional;
	}

	public boolean isRepeatable() {
		return this.isRepeatable;
	}

	public void setSentenceOrder(String ord) {
		this.sentenceOrder = ord;
	}

	public String getSentenceNum() {
		return sentenceNum;
	}

	public void setSentenceNum(String sentenceNum) {
		this.sentenceNum = sentenceNum;
	}

	@Override
	public int compareTo(Sentence s) {
		return sentenceSeq.compareTo(s.getSentenceSeq());

	}

	public void changeOrder(String from, String to) {
		String original = this.sentenceOrder;
		String changed = original.replaceFirst(from, to);
		this.sentenceOrder = changed;
	}

	public String getRepresentVerb() {
		return representVerb;
	}

	public void setRepresentVerb(String representVerb) {
		this.representVerb = representVerb;
		this.setRepresentive(true);
	}

	public String getPrevType() {
		return prevType;
	}

	public void setPrevType(String prevType) {
		this.prevType = prevType;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getNextType() {
		return nextType;
	}

	public void setNextType(String nextType) {
		this.nextType = nextType;
	}

	public String getVerbString() {
		return this.sentenceType + ":" + this.getRepresentVerb();
	}

	@Override
	public String toString() {
		return getVerbString();
	}

}
