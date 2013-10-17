package com.mek442.node;

import java.util.List;
import java.util.Map;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;


public interface Node {

	public TokenWord getTokenValue();
	public List<Node> getChildNodes();
	public Node buildAST();
	public boolean hasError();
	public int getCount();
	public void setCount(int count);
	public Map<String, Attribute> getAttributes();
	public void setAttribute(String key, Attribute pAttribute);
}
