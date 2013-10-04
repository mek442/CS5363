package com.mek442.node;

public class SimpleExpression {

	private Term mParseTerm;
	private SimpleExpression1 mParseSimpleExpression1;

	public SimpleExpression(Term pParseTerm, SimpleExpression1 pParseSimpleExpression1) {
		mParseTerm = pParseTerm;
		mParseSimpleExpression1 = pParseSimpleExpression1;
		
	}
	
	public SimpleExpression1 getParseSimpleExpression1() {
		return mParseSimpleExpression1;
	}
	
	public Term getParseTerm() {
		return mParseTerm;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.SimpleExpression);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseTerm!=null){
			buffer.append(mParseTerm.getOutPut(pCounter, parent));	
		 
		}
		
		if(mParseSimpleExpression1!=null){
			buffer.append(mParseSimpleExpression1.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}

}