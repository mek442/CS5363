package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class Term1 {

	private TokenWord mToken;
	private Factor mParseFactor;
	public Term1(TokenWord pToken, Factor pParseFactor) {
		mToken = pToken;
		mParseFactor = pParseFactor;
		
	}

	public Factor getParseFactor() {
		return mParseFactor;
	}
	
	public TokenWord getToken() {
		return mToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Term1);
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
		
		if(mParseFactor!=null){
			buffer.append(mParseFactor.getOutPut(pCounter, parent));	
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
