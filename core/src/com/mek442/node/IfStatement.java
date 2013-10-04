package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class IfStatement {

	private TokenWord mIfToken;
	private Expression mParseExpression;
	private TokenWord mThenToken;
	private StatementSequence mParseStatementSequences;
	private ElseClause mParseElseExpression;
	private TokenWord mEndToken;

	public IfStatement(TokenWord pIfT, Expression pParseExpression, TokenWord pThenT,
			StatementSequence pParseStatementSequences, ElseClause pParseElseExpression, TokenWord pEndT) {
		mIfToken = pIfT;
		mParseExpression = pParseExpression;
		mThenToken = pThenT;
		mParseStatementSequences = pParseStatementSequences;
		mParseElseExpression = pParseElseExpression;
		mEndToken = pEndT;

	}

	public TokenWord getIfToken() {
		return mIfToken;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getThenToken() {
		return mThenToken;
	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public ElseClause getParseElseExpression() {
		return mParseElseExpression;
	}

	public TokenWord getEndToken() {
		return mEndToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.IfStatement);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mIfToken!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mIfToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseExpression!=null){
			buffer.append(mParseExpression.getOutPut(pCounter,parent));
		}
		
		if(mThenToken!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mThenToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseStatementSequences!=null){
			buffer.append(mParseStatementSequences.getOutPut(pCounter,parent));
		}
		
		if(mParseElseExpression!=null){
			buffer.append(mParseElseExpression.getOutPut(pCounter,parent));
		}
		if(mEndToken!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mEndToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		return buffer.toString();
		
	}


}
