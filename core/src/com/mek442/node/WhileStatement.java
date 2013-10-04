package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class WhileStatement {

	private TokenWord mWhile;
	private Expression mParseExpression;
	private TokenWord mDOT;
	private StatementSequence mParseStatementSequences;
	private TokenWord mEnd;

	public WhileStatement(TokenWord pWhileT, Expression pParseExpression, TokenWord pDOT,
			StatementSequence pParseStatementSequences, TokenWord pEndT) {
		mWhile = pWhileT;
		mParseExpression = pParseExpression;
		mDOT = pDOT;
		mParseStatementSequences = pParseStatementSequences;
		mEnd = pEndT;

	}

	public TokenWord getWhile() {
		return mWhile;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getDOT() {
		return mDOT;
	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public TokenWord getEnd() {
		return mEnd;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.WhileStatement);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mWhile!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mWhile.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseExpression!=null){
			buffer.append(mParseExpression.getOutPut(pCounter,parent));
		}
		
		if(mDOT!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mDOT.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseStatementSequences!=null){
			buffer.append(mParseStatementSequences.getOutPut(pCounter,parent));
		}
		if(mEnd!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mEnd.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		return buffer.toString();
		
	}
	

}
