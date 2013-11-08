package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class SimpleExpression implements Node {

	private Term mParseTerm;
	private SimpleExpression1 mParseSimpleExpression1;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;
	private Node mFather;
	public SimpleExpression(Term pParseTerm, SimpleExpression1 pParseSimpleExpression1) {
		mParseTerm = pParseTerm;
		mParseSimpleExpression1 = pParseSimpleExpression1;

	}

	public SimpleExpression1 getParseSimpleExpression1() {
		return mParseSimpleExpression1;
	}

	public Term getParseTerm() {
		return mParseTerm;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.SimpleExpression);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mParseTerm != null) {
			buffer.append(mParseTerm.getOutPut(pCounter, parent));

		}

		if (mParseSimpleExpression1 != null) {
			buffer.append(mParseSimpleExpression1.getOutPut(pCounter, parent));
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
		mFather = father;
		return this;
	}

	

	@Override
	public List<Node> getChildNodes() {
		
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			List<Node> nodeOP = new ArrayList<Node>();
			if (mParseTerm != null) {
				nodes.add(mParseTerm.buildAST(this));
			}
			if (mParseSimpleExpression1 != null) {
				Operator buildAST = (Operator) mParseSimpleExpression1.buildAST(this);
				List<Node> childNodes = mParseSimpleExpression1.getChildNodes();
				nodes.addAll(childNodes);
				if (buildAST != null) {
					buildAST.setChilds(nodes);
					nodeOP.add(buildAST);
				} else {
					mNodes = nodes;
					return mNodes;
				}
			}
			mNodes = nodeOP;
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
