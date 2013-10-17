package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class Factor implements Node {

	private TokenWord mTokenStart;
	private Expression mParseExpression;
	private TokenWord mTokenEnd;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	

	public Factor(TokenWord pToken, Expression pParseExpression, TokenWord pToken2) {
		mTokenStart = pToken;
		mParseExpression = pParseExpression;
		mTokenEnd = pToken2;

	}

	public TokenWord getTokenStart() {
		return mTokenStart;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getTokenEnd() {
		return mTokenEnd;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Factor);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mParseExpression == null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mTokenStart.getWord().getValue() + " : " + mTokenStart.getIdentifier());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		} else {
			int child = pCounter.getCount();
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mTokenStart.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);

			buffer.append(mParseExpression.getOutPut(pCounter, parent));

			buffer.append("\n");

			child = pCounter.getCount();
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mTokenEnd.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);

		}

		return buffer.toString();

	}

	public TokenWord getTokenValue() {
       if(mTokenStart!=null && mTokenStart.getWord()==Token.LP)   
		return null;
       else return mTokenStart;
	}

	
	
	public Node buildAST() {
		
		return this;
	}

	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Node> getChildNodes() {
		List<Node> nodes = new ArrayList<Node>();
		if (mParseExpression != null) {
			nodes.add(new Operator(mTokenStart));
			nodes.add(mParseExpression.buildAST());
			nodes.add(new Operator(mTokenEnd));
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
