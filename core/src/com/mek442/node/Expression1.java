package com.mek442.node;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class Expression1 {

	private TokenWord mToken;
	private SimpleExpression mParseSimpleExpression;

	public Expression1(TokenWord pWord, SimpleExpression pParseSimpleExpression) {
		mToken = pWord;
		mParseSimpleExpression = pParseSimpleExpression;

	}

	public TokenWord getToken() {
		return mToken;
	}

	public SimpleExpression getParseSimpleExpression() {
		return mParseSimpleExpression;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Expression1);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		
		if(mToken!=null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseSimpleExpression!=null){
			buffer.append(mParseSimpleExpression.getOutPut(pCounter, parent));	
		}
	
		if(mToken==null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", "empty");
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		return buffer.toString();
		
	}

}
