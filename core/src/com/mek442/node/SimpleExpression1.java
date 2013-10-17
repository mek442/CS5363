package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class SimpleExpression1 implements Node{

	private TokenWord mToken;
	private Term mParseTerm;
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();

	public SimpleExpression1(TokenWord pWord, Term pParseTerm) {
		mToken = pWord;
		mParseTerm = pParseTerm;
		
		
	}
	
	public Term getParseTerm() {
		return mParseTerm;
	}
	
	public TokenWord getToken() {
		return mToken;
	}
	
	public String getOutPut(Counter pCounter, int father){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("\n");
		int parent =pCounter.getCount();
		String label  = NodeUtil.Label.replace("#", ""+ parent);
		label = label.replace("@", NodeUtil.SimpleExpression1);
		buffer.append(label);
		
		buffer.append("\n");
		
		buffer.append("n"+father +" -> "+ "n"+parent);
		
		
		if(mToken!=null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", mToken.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);
		}
		
		if(mParseTerm!=null){
			buffer.append(mParseTerm.getOutPut(pCounter, parent));	
		}
		
		if(mToken==null && mParseTerm==null){
			int child = pCounter.getCount();
			buffer.append("\n");
			label  = NodeUtil.Label.replace("#", ""+child);
			label = label.replace("@", "empty");
			buffer.append(label);
			buffer.append("\n");
			
			buffer.append("n"+parent +" -> "+ "n"+child);	
		}
		return buffer.toString();
		
	}
	
	@Override
	public Node buildAST() {
		Operator node =null;
		if(mToken!=null){
			
			node = new Operator(mToken);
			node.setChilds(getChildNodes());
		}
		
		
		return node;
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
		if (mParseTerm != null) {
			nodes.add(mParseTerm.buildAST());
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
