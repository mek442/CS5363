package com.mek442.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mek442.scanner.TokenWord;

public class Operator implements Node{

	TokenWord mTokenWord;
	List<Node> mChilds = new ArrayList<Node>();
	private int mCount;
	Map<String,Attribute> mAttributes = new HashMap<String,Attribute>();
	private String mColor;
	private boolean mError;
	private Node mFather = null;
	
	 public Operator(TokenWord pTokenWord) {
		// TODO Auto-generated constructor stub
		 mTokenWord = pTokenWord;
	}
	
	@Override
	public TokenWord getTokenValue() {
		// TODO Auto-generated method stub
		return mTokenWord;
	}

	@Override
	public Node buildAST(Node fahter) {
		// TODO Auto-generated method stub
		mFather = fahter;
		return this;
	}


	@Override
	public List<Node> getChildNodes() {
		// TODO Auto-generated method stub
		return mChilds;
	}
	
	public void setChilds(List<Node> pChilds) {
		mChilds = pChilds;
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
