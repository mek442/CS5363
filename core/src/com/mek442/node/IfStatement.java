package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class IfStatement implements Node {

	private TokenWord mIfToken;
	private Expression mParseExpression;
	private TokenWord mThenToken;
	private StatementSequence mParseStatementSequences;
	private ElseClause mParseElseExpression;
	private TokenWord mEndToken;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private boolean mError;
	private String mColor;
	private Node mNode =null;
	private List<Node> mNodes = null;
	private Node father =null;

	public IfStatement(TokenWord pIfT, Expression pParseExpression, TokenWord pThenT,
			StatementSequence pParseStatementSequences, ElseClause pParseElseExpression, TokenWord pEndT) {
		mIfToken = pIfT;
		mParseExpression = pParseExpression;
		mThenToken = pThenT;
		mParseStatementSequences = pParseStatementSequences;
		mParseElseExpression = pParseElseExpression;
		mEndToken = pEndT;

	}

	public TokenWord getIfToken() {
		return mIfToken;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public TokenWord getThenToken() {
		return mThenToken;
	}

	public StatementSequence getParseStatementSequences() {
		return mParseStatementSequences;
	}

	public ElseClause getParseElseExpression() {
		return mParseElseExpression;
	}

	public TokenWord getEndToken() {
		return mEndToken;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.IfStatement);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mIfToken != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mIfToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseExpression != null) {
			buffer.append(mParseExpression.getOutPut(pCounter, parent));
		}

		if (mThenToken != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mThenToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseStatementSequences != null) {
			buffer.append(mParseStatementSequences.getOutPut(pCounter, parent));
		}

		if (mParseElseExpression != null) {
			buffer.append(mParseElseExpression.getOutPut(pCounter, parent));
		}
		if (mEndToken != null) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mEndToken.getWord().getValue());
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
	public Node buildAST(Node father) {
		if (mNode == null) {
			Operator node = new Operator(mIfToken);
			this.father= node;
			node.setChilds(getChildNodes());
			mNode = node.buildAST(father);
			
		}

		return mNode;
	}

	
	@Override
	public List<Node> getChildNodes() {

		if (mNodes == null) {
			List<Node> nodes = new ArrayList<Node>();
			List<Node> nodesthen = new ArrayList<Node>();
			if (mParseExpression != null) {
				nodes.add(mParseExpression.buildAST(this.father));
			}
			Operator node = new Operator(mThenToken);
			node = (Operator)node.buildAST(this.father);
			
			if (mParseStatementSequences != null) {
				nodesthen.add(mParseStatementSequences.buildAST(node));
				node.setChilds(nodesthen);
				nodes.add(node);
			}

			if (mParseStatementSequences != null) {
				nodes.add(mParseElseExpression.buildAST(this.father));
			}
			//node.setChilds(nodesthen);

			//nodes.add(node);
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
		return null;
	}

}
