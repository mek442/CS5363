package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Assignment implements Node {

	private TokenWord mIdent;
	private TokenWord mAsgn;
	private Assignment1 mParseAssignment1;
	int mCount =0;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	
	public Assignment(TokenWord pIdent, TokenWord pAsgn, Assignment1 pParseAssignment1) {
		mIdent = pIdent;
		mAsgn = pAsgn;
		mParseAssignment1 = pParseAssignment1;

	}

	public TokenWord getAsgn() {
		return mAsgn;
	}

	public TokenWord getIdent() {
		return mIdent;
	}

	public Assignment1 getParseAssignment1() {
		return mParseAssignment1;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		int parent = pCounter.getCount();
		buffer.append("\n");
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Assignment);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mIdent != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mIdent.getWord().getValue() + " : " + mIdent.getIdentifier());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mAsgn != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mAsgn.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseAssignment1 != null) {
			buffer.append(mParseAssignment1.getOutPut(pCounter, parent));
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

		Operator node = new Operator(mAsgn);
		node.setChilds(getChildNodes());

		return node;
	}

	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Node> getChildNodes() {
		List<Node> nodes = new ArrayList<Node>();
		Operator iden = new Operator(mIdent);
		nodes.add(iden);
		if (mParseAssignment1 != null) {
			nodes.add(mParseAssignment1.buildAST());
		}

		return nodes;
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
}
