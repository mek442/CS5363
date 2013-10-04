package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class ElseClause {

	private TokenWord mToken;
	private StatementSequence mParseStatementSequences;

	public ElseClause(TokenWord pToken, StatementSequence pParseStatementSequences) {
		mToken = pToken;
		mParseStatementSequences = pParseStatementSequences;

	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public TokenWord getToken() {
		return mToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.ElseClause);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mToken!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseStatementSequences!=null){
			buffer.append(mParseStatementSequences.getOutPut(pCounter,parent));
		}
		
		if(mToken==null && mParseStatementSequences==null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", " empty");
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		
		return buffer.toString();
		
	}


}
