package com.mek442.scanner;


import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Vector;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class TokenManager {
	private Scanner mScanner;

	private Vector<TokenWord> mNextQueue;

	private Stack<Vector<TokenWord>> mQueueStack;
	private Vector<TokenWord> mBacktrackingQueue;

	public boolean isLookingAhead;

	private TokenWord previousToken;

	private TokenWord token;

	public TokenManager(String fileName) throws FileNotFoundException {
		mScanner = new Scanner(fileName);
		mNextQueue = new Vector<TokenWord>();
		mQueueStack = new Stack<Vector<TokenWord>>();
		mBacktrackingQueue = new Vector<TokenWord>();

		isLookingAhead = false;
	}

	public TokenWord previousToken() {
		return previousToken;
	}

	public boolean hasError() {
		return mScanner.hasError();
	}

	public TokenWord token() {
		return token;
	}

	public void recordPosition() {
		isLookingAhead = true;
		mQueueStack.push(mNextQueue);
		mNextQueue = new Vector<TokenWord>();
		mNextQueue.add(previousToken);
		mNextQueue.add(token);
	}

	public void next() {
		previousToken = token;
		if (mBacktrackingQueue.size() == 0) {
			token = mScanner.getNextToken();
		} else {
			token = mBacktrackingQueue.remove(0);
		}
		if (isLookingAhead) {
			mNextQueue.add(token);
		}
	}

	public void returnToPosition() {
		while (mBacktrackingQueue.size() > 0) {
			mNextQueue.add(mBacktrackingQueue.remove(0));
		}
		mBacktrackingQueue = mNextQueue;
		mNextQueue = mQueueStack.pop();
		isLookingAhead = !(mQueueStack.empty());

		// Restore previous and current tokens
		previousToken = mBacktrackingQueue.remove(0);
		token = mBacktrackingQueue.remove(0);
	}

	
	public static void main(String[] args) {
		TokenManager scanner = null;
	        try {
	            scanner = new TokenManager("C:\\Users\\Mostafa\\git\\mek442\\core\\tests\\simple1.tl13");
	        } catch (FileNotFoundException e) {
	        	
	             e.printStackTrace();
	        }

	        
	            // Just tokenize input and print the tokens to STDOUT
	            TokenWord token;
	            do {
	                scanner.next();
	                token = scanner.token();
	                System.out.printf("%d\t : %s = %s\n", token.getLineNumber(), token.getIdentifier(),token.getWord());
	            } while (token.getWord() != Token.EOF);
	           
	        
	}

}
