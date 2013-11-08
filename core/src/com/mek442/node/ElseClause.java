package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class ElseClause implements Node{

	private TokenWord mToken;
	private StatementSequence mParseStatementSequences;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private Node mNode =null;
	private List<Node> mNodes = null;
	private Node mFather =null;

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
	
	
	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST(Node father) {
		if (mNode == null) {
			Operator node = new Operator(mToken);
			this.mFather = node;
			node.setChilds(getChildNodes());
			mNode = node.buildAST(father);
		}
		return mNode;
	}

	

	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();

			if (mParseStatementSequences != null) {
				nodes.add(mParseStatementSequences.buildAST(this.mFather));
			}
			mNodes = nodes;
		}
		return mNodes;
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
