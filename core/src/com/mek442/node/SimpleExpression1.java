package com.mek442.node;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class SimpleExpression1 {

	private TokenWord mToken;
	private Term mParseTerm;

	public SimpleExpression1(TokenWord pWord, Term pParseTerm) {
		mToken = pWord;
		mParseTerm = pParseTerm;
		
		
	}
	
	public Term getParseTerm() {
		return mParseTerm;
	}
	
	public TokenWord getToken() {
		return mToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.SimpleExpression1);
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
		
		if(mParseTerm!=null){
			buffer.append(mParseTerm.getOutPut(pCounter, parent));	
		}
		
		if(mToken==null && mParseTerm==null){
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
