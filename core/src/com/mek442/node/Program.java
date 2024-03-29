package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Program implements Node{
	Declaration declarations = null;
	StatementSequence statementSequence = null;
	TokenWord program;
	TokenWord begin;
	TokenWord end;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;

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
	
	
	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST(Node father) {
		
		return this;
	}


	@Override
	public List<Node> getChildNodes() {

		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (declarations != null) {
				nodes.add(declarations.buildAST(this));
			}
			if (statementSequence != null) {
				nodes.add(statementSequence.buildAST(this));
			}
			mNodes = nodes;
		}
		return mNodes;// TODO Auto-generated method stub
	}
	
	@Override
	public int getCount() {
		
		return mCount;
	}

	@Override
	public void setCount(int pCount) {
		mCount = pCount;
		
	}
	
	

	@Override
	public Map<String, Attribute> getAttributes() {
		// TODO Auto-generated method stub
		return mAttributes;
	}

	@Override
	public void setAttribute(String key, Attribute pAttribute) {
		mAttributes.put(key,pAttribute);
		
	}
	
	@Override
	public String getColor() {
		return mColor;
	}

	@Override
	public void setColor(String pColor) {
		mColor = pColor;
		
	}

	@Override
	public void setError(boolean pError) {
		mError = pError;
		
		
	}
	
	@Override
	public boolean hasError() {
		return mError;
	}

	@Override
	public boolean isDeclaration() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getFather() {
		// TODO Auto-generated method stub
		return null;
	}

}
