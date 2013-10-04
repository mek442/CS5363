package com.mek442.node;

public class Expression {

	private SimpleExpression mParseSimpleExpression;
	private Expression1 mExpression1;

	public Expression(SimpleExpression pParseSimpleExpression, Expression1 pExpression1) {
		mParseSimpleExpression = pParseSimpleExpression;
		mExpression1 = pExpression1;

	}

	public Expression1 getExpression1() {
		return mExpression1;
	}

	public SimpleExpression getParseSimpleExpression() {
		return mParseSimpleExpression;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Expression);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseSimpleExpression!=null){
			buffer.append(mParseSimpleExpression.getOutPut( pCounter,parent));
		}
		
		if(mExpression1!=null){
			buffer.append(mExpression1.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}
	


}
