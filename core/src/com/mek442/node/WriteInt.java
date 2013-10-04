package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class WriteInt {

	private TokenWord mToken;
	private Expression mParseExpression;

	public WriteInt(TokenWord pToken, Expression pParseExpression) {
		mToken = pToken;
		mParseExpression = pParseExpression;
		
		
		
	}
	
	public Expression getParseExpression() {
		return mParseExpression;
	}
	
	public TokenWord getToken() {
		return mToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.WRITEINT);
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
		
		if(mParseExpression!=null){
			buffer.append(mParseExpression.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}

}
