package com.mek442.scanner;
/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class TokenWord {
	private Token word;
	private int lineNumber;
	private String identifier;
	
	public TokenWord(Token pToken, int lineNumber) {
		this.word = pToken;
		this.lineNumber = lineNumber;
	}
	
	public TokenWord(Token pToken, String identifier, int lineNumber) {
		this.word = pToken;
		this.identifier = identifier;
		this.lineNumber = lineNumber;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public Token getWord() {
		return this.word;
	}

}
