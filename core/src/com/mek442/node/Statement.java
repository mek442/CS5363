package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Statement implements Node {

	private Assignment mParseAssignment;
	private IfStatement mParseIfStatement;
	private WhileStatement mParseWhileStatement;
	private WriteInt mParseWriteExpression;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Node mFather;
	public Statement(Assignment pParseAssignment, IfStatement pParseIfStatement, WhileStatement pParseWhileStatement,
			WriteInt pParseWriteExpression) {
				mParseAssignment = pParseAssignment;
				mParseIfStatement = pParseIfStatement;
				mParseWhileStatement = pParseWhileStatement;
				mParseWriteExpression = pParseWriteExpression;
		
	}

	public Assignment getParseAssignment() {
		return mParseAssignment;
	}

	public IfStatement getParseIfStatement() {
		return mParseIfStatement;
	}

	public WhileStatement getParseWhileStatement() {
		return mParseWhileStatement;
	}

	public WriteInt getParseWriteExpression() {
		return mParseWriteExpression;
	}

	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.Statement);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		if(mParseAssignment!=null){
			buffer.append(mParseAssignment.getOutPut( pCounter,parent));
		}
		
		if(mParseIfStatement!=null){
			buffer.append(mParseIfStatement.getOutPut(pCounter, parent));	
		}
		
		if(mParseWhileStatement!=null){
			buffer.append(mParseWhileStatement.getOutPut(pCounter, parent));	
		}
		
		if(mParseWriteExpression!=null){
			buffer.append(mParseWriteExpression.getOutPut(pCounter, parent));	
		}
		
		return buffer.toString();
		
	}
	
	@Override
	public Node buildAST(Node father) {
		mFather = father;
		return this;
	}
	
	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (mParseAssignment != null) {
				nodes.add(mParseAssignment.buildAST(this));
			} else if (mParseIfStatement != null) {
				nodes.add(mParseIfStatement.buildAST(this));
			} else if (mParseWhileStatement != null) {
				nodes.add(mParseWhileStatement.buildAST(this));
			} else if (mParseWriteExpression != null) {
				nodes.add(mParseWriteExpression.buildAST(this));
			}
			mNodes = nodes;
		}
		return mNodes;
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
		return mFather;
	}
}
