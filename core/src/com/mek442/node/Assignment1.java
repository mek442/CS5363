package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class Assignment1 implements Node {

	private TokenWord mTokenWord;
	private Expression mParseExpression;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();

	public Assignment1(TokenWord pWord, Expression pParseExpression) {
		mTokenWord = pWord;
		mParseExpression = pParseExpression;

	}

	public TokenWord getTokenWord() {
		return mTokenWord;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Assignment1);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mTokenWord.getWord() == Token.READINT) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mTokenWord.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseExpression != null) {
			buffer.append(mParseExpression.getOutPut(pCounter, parent));
		}

		return buffer.toString();

	}
	
	@Override
	public Node buildAST() {
		
		return this;
	}
	
	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean hasError() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Node> getChildNodes() {
		List<Node> nodes = new ArrayList<Node>();
		if (mParseExpression != null) {
			nodes.add(mParseExpression.buildAST());
		}else if (mTokenWord != null) {
			nodes.add(new Operator(mTokenWord));
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
