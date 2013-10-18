package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Term implements Node {

	private Factor mParseFactor;
	private Term1 mParseTerm1;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private List<Node> mNodes = null;

	public Term(Factor pParseFactor, Term1 pParseTerm1) {
		mParseFactor = pParseFactor;
		mParseTerm1 = pParseTerm1;

	}

	public Factor getParseFactor() {
		return mParseFactor;
	}

	public Term1 getParseTerm1() {
		return mParseTerm1;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Term);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mParseFactor != null) {
			buffer.append(mParseFactor.getOutPut(pCounter, parent));
		}

		if (mParseTerm1 != null) {
			buffer.append(mParseTerm1.getOutPut(pCounter, parent));
		}

		return buffer.toString();

	}

	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node buildAST() {
		return this;
	}


	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			List<Node> nodeOP = new ArrayList<Node>();
			if (mParseFactor != null) {

				nodes.add(mParseFactor.buildAST());
			}

			if (mParseTerm1 != null) {
				Operator buildAST = (Operator) mParseTerm1.buildAST();
				List<Node> childNodes = mParseTerm1.getChildNodes();
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

}
