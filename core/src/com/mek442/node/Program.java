package com.mek442.node;

import com.mek442.scanner.TokenWord;

public class Program {
	Declaration declarations = null;
	StatementSequence statementSequence = null;
	TokenWord program;
	TokenWord begin;
	TokenWord end;

	public Program(TokenWord pTokenP, Declaration pDeclaration, TokenWord pTokenB,
			StatementSequence pParseStatementSequences, TokenWord pTokenE) {
		this.program = pTokenP;
		this.declarations = pDeclaration;
		this.begin = pTokenB;
		this.statementSequence = pParseStatementSequences;
		this.end = pTokenE;
	}

	public TokenWord getBegin() {
		return begin;
	}

	public TokenWord getProgram() {
		return program;
	}

	public Declaration getDeclarations() {
		return declarations;
	}

	public StatementSequence getStatementSequence() {
		return statementSequence;
	}

	public TokenWord getEnd() {
		return end;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Program);
		buffer.append(label);
		
		buffer.append("\n");
		
		
		if(program!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", program.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(declarations!=null){
			buffer.append(declarations.getOutPut(pCounter,parent));
		}
		
		if(begin!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", begin.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(statementSequence!=null){
			buffer.append(statementSequence.getOutPut(pCounter,parent));
		}
		if(end!= null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", end.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		return buffer.toString();
		
	}
	
}
