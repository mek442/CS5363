package com.mek442.node;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class StatementSequence {

	private Statement mStatement;
	private TokenWord mSc;
	private StatementSequence mParseStatementSequences;

	public StatementSequence(Statement pStatement, TokenWord pSc, StatementSequence pParseStatementSequences) {
		mStatement = pStatement;
		mSc = pSc;
		mParseStatementSequences = pParseStatementSequences;

	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public TokenWord getSc() {
		return mSc;
	}

	public Statement getStatement() {
		return mStatement;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.StatementSequences);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		
		
		if(mStatement!=null){
			buffer.append(mStatement.getOutPut(pCounter,parent));
		}
		
		if(mSc!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mSc.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseStatementSequences!=null){
			buffer.append(mParseStatementSequences.getOutPut(pCounter,parent));
		}
		
		if(mStatement==null && mSc==null && mParseStatementSequences==null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@","empty");
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		return buffer.toString();
		
	}
	

}