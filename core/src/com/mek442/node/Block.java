package com.mek442.node;

import java.util.ArrayList;
import java.util.List;

import com.mek442.scanner.Token;

public class Block {
	String mBlock = "";
	String mLabel = "B";
	List<String> mChildren = new ArrayList<String>();
	int mNumber = 0;
	boolean visited = false;
	Token mToken = null;
	boolean isProcess = false;
	boolean isWithElse = false;
	public Block(int number){
	 mNumber = number;	
	}
	public String getBlock() {
		return mBlock;
	}

	public void setBlock(String pBlock) {
		mBlock = pBlock;
	}

	public String getLabel() {
		return mLabel+mNumber;
	}

	public void setLabel(String pLabel) {
		mLabel = pLabel;
	}
	
	public int getNumber() {
		return mNumber;
	}
	
	public void setNumber(int pNumber) {
		mNumber = pNumber;
	}
	public void setChildren(String pChildren) {
		mChildren.add(pChildren);
	}

	public List<String> getChildren() {
		return mChildren;
	}
	
	public void setVisited(boolean pVisited) {
		visited = pVisited;
	}
	
	public boolean isVisited() {
		return visited;
	}
	
	public void setToken(Token pToken) {
		mToken = pToken;
	}
	
	public Token getToken() {
		return mToken;
	}
	
	public void setProcess(boolean pIsProcess) {
		isProcess = pIsProcess;
	}
	
	public boolean isProcess() {
		return isProcess;
	}
	
	public void setWithElse(boolean pIsWithoutElse) {
		isWithElse = pIsWithoutElse;
	}
	
	public boolean isWithElse() {
		return isWithElse;
	}

}
