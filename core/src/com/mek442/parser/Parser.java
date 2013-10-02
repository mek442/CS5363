package com.mek442.parser;


import java.io.FileNotFoundException;


import com.mek442.node.Program;
import com.mek442.scanner.Token;
import com.mek442.scanner.TokenManager;
import com.mek442.scanner.TokenWord;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class Parser {

	TokenManager mTokenManager;
	private boolean isError = false;
	private boolean isRecovered = true;

	public Parser(String pFileName) throws FileNotFoundException {
		mTokenManager = new TokenManager(pFileName);

	}

	private void reportError(String message, Object... args) {
		isError = true;
		System.err.printf(message, args);
		System.err.println();
	}

	/**
	 * 
	 * <pre>
	 *  <program> ::= program <declarations> begin <statementSequence> end
	 * </pre>
	 * 
	 */
	public Program startParse() {
		mTokenManager.next();	
		mustBe(mTokenManager.token());
        
		return null;
	}
	
	
	

	private void mustBe(TokenWord pTokenWord) {
		if (mTokenManager.token() == pTokenWord) {
			isRecovered = true;
		} else if (isRecovered) {
			isRecovered = false;
			reportError("%s found where %s sought", mTokenManager.token(), mTokenManager.token().getWord());
		} else {
			// Do not report the (possibly spurious) error,
			// but rather attempt to recover by forcing a match.
			while (!isMatch(pTokenWord) && !isMatch(new TokenWord(Token.EOF, 0))) {
				mTokenManager.next();
			}
			if (isMatch(pTokenWord)) {
				isRecovered = true;
			}
		}
	}

	private boolean haveExpectedToekn(TokenWord pWord) {
		if (isMatch(pWord)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isMatch(TokenWord pToken) {
		return (pToken == mTokenManager.token());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
