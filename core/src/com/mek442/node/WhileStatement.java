package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class WhileStatement implements Node {

	private TokenWord mWhile;
	private Expression mParseExpression;
	private TokenWord mDOT;
	private StatementSequence mParseStatementSequences;
	private TokenWord mEnd;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private Node mNode =null;
	private List<Node> mNodes = null;

	public WhileStatement(TokenWord pWhileT, Expression pParseExpression, TokenWord pDOT,
			StatementSequence pParseStatementSequences, TokenWord pEndT) {
		mWhile = pWhileT;
		mParseExpression = pParseExpression;
		mDOT = pDOT;
		mParseStatementSequences = pParseStatementSequences;
		mEnd = pEndT;

	}

	public TokenWord getWhile() {
		return mWhile;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getDOT() {
		return mDOT;
	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public TokenWord getEnd() {
		return mEnd;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.WhileStatement);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mWhile != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mWhile.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseExpression != null) {
			buffer.append(mParseExpression.getOutPut(pCounter, parent));
		}

		if (mDOT != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mDOT.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseStatementSequences != null) {
			buffer.append(mParseStatementSequences.getOutPut(pCounter, parent));
		}
		if (mEnd != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mEnd.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
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
		if (mNode == null) {
			Operator node = new Operator(mWhile);
			node.setChilds(getChildNodes());
			mNode = node;
		}
		return mNode;
	}

	

	@Override
	public List<Node> getChildNodes() {
		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			if (mParseExpression != null) {
				nodes.add(mParseExpression.buildAST());
			}
			if (mParseStatementSequences != null) {
				nodes.add(mParseStatementSequences.buildAST());
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

}
