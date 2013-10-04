package com.mek442.node;

public class Statement {

	private Assignment mParseAssignment;
	private IfStatement mParseIfStatement;
	private WhileStatement mParseWhileStatement;
	private WriteInt mParseWriteExpression;

	public Statement(Assignment pParseAssignment, IfStatement pParseIfStatement, WhileStatement pParseWhileStatement,
			WriteInt pParseWriteExpression) {
				mParseAssignment = pParseAssignment;
				mParseIfStatement = pParseIfStatement;
				mParseWhileStatement = pParseWhileStatement;
				mParseWriteExpression = pParseWriteExpression;
		
	}

	public Assignment getParseAssignment() {
		return mParseAssignment;
	}

	public IfStatement getParseIfStatement() {
		return mParseIfStatement;
	}

	public WhileStatement getParseWhileStatement() {
		return mParseWhileStatement;
	}

	public WriteInt getParseWriteExpression() {
		return mParseWriteExpression;
	}

	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Statement);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseAssignment!=null){
			buffer.append(mParseAssignment.getOutPut( pCounter,parent));
		}
		
		if(mParseIfStatement!=null){
			buffer.append(mParseIfStatement.getOutPut(pCounter, parent));	
		}
		
		if(mParseWhileStatement!=null){
			buffer.append(mParseWhileStatement.getOutPut(pCounter, parent));	
		}
		
		if(mParseWriteExpression!=null){
			buffer.append(mParseWriteExpression.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}
}
