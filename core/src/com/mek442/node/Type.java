package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Type implements Node {
	private TokenWord type;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;

	public Type(TokenWord pType) {
		this.type = pType;
	}

	public TokenWord getType() {
		return type;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Type);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (type != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", type.getWord().toString());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		return buffer.toString();

	}

	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public Node buildAST() {
		return this;
	}

	
	@Override
	public List<Node> getChildNodes() {
		List<Node> nodes = new ArrayList<Node>();
		return nodes;// TODO Auto-generated method stub
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
