package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class Factor {
    
	private TokenWord mTokenStart;
	private Expression mParseExpression;
	private TokenWord mTokenEnd;

	public Factor(TokenWord pToken, Expression pParseExpression, TokenWord pToken2) {
		mTokenStart = pToken;
		mParseExpression = pParseExpression;
		mTokenEnd = pToken2;

	}

	public TokenWord getTokenStart() {
		return mTokenStart;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getTokenEnd() {
		return mTokenEnd;
	}

	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Factor);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseExpression == null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenStart.getWord().getValue()+" : " + mTokenStart.getIdentifier());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}else{
			int child = pCounter.getCount();
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenStart.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
			
			buffer.append(mParseExpression.getOutPut(pCounter, parent));
			
			buffer.append("\n");
			
			child = pCounter.getCount();
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mTokenEnd.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
			
		}
		
		return buffer.toString();
		
	}
	
}
