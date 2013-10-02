package com.mek442.scanner;

import java.io.FileNotFoundException;
import java.util.Hashtable;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class Scanner {

	private boolean isError;
	private char unScannedChar;
	private Hashtable<String, Token> mReservedKeyWord;

	private InputReader mInput;

	private String mFileName;

	private char character;

	private int lineNumber;

	public Scanner(String fileName) throws FileNotFoundException {

		this.mFileName = fileName;
		this.mInput = new InputReader(fileName);
		isError = false;

		loadReservedKeyWord();
	}

	public boolean hasError(){
		return isError;
	}
	
	private void reportError(String message, Object... args) {
		isError = true;
		System.err.printf("%s:%d: ", mFileName, lineNumber);
		System.err.printf(message, args);
		System.err.println();
	}

	private void readNextCharacter() {
		character = mInput.nextChar();
		lineNumber = mInput.getLineNumber();
	}

	public TokenWord getNextToken() {
		readNextCharacter();
		StringBuffer buffer;
		while (ScannerUtil.isWhitespace(character)) {
			readNextCharacter();
		}

		switch (character) {

		case '+':
			return new TokenWord(Token.PLUS, lineNumber);
		case '-':
			return new TokenWord(Token.MINUS, lineNumber);
		case '*':
			return new TokenWord(Token.MUL, lineNumber);
		case '(':
			return new TokenWord(Token.LP, lineNumber);
		case ')':
			return new TokenWord(Token.RP, lineNumber);
		case '=':
			return new TokenWord(Token.EQUAL, lineNumber);
		case ';':
			return new TokenWord(Token.SC, lineNumber);
		case ':':
			readNextCharacter();
			if (character == '=') {
				return new TokenWord(Token.ASGN, lineNumber);
			} else {
				reportError("%s does not does not support", "" + character);

				return getNextToken();
			}
		case '>':
			readNextCharacter();
			if (character == ' ') {
				return new TokenWord(Token.GT, lineNumber);
			} else if (character == '=') {
				return new TokenWord(Token.GT_EQUAL, lineNumber);
			} else {
				reportError("%s does not does not support", "" + character);

				return getNextToken();
			}
		case '<':
			readNextCharacter();
			if (character == ' ') {
				return new TokenWord(Token.LT, lineNumber);
			} else if (character == '=') {
				return new TokenWord(Token.LT_EQUAL, lineNumber);
			} else {
				reportError("%s does not does not support", "" + character);

				return getNextToken();
			}

		case '!':
			readNextCharacter();
			if (character == '=')
				return new TokenWord(Token.NOT_EQUAL, lineNumber);
			else {
				reportError("%s does not support", "" + character);

				return getNextToken();
			}
			
		case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            buffer = new StringBuffer();
            while (ScannerUtil.isDigit(character)) {
                buffer.append(character);
                readNextCharacter();
            }
            return new TokenWord(Token.NUM, buffer.toString(), lineNumber);

		default:
			if (ScannerUtil.isStartOfIdentifier(character)) {
				buffer = new StringBuffer();
				while (ScannerUtil.isPartOfIdentifier(character)) {
					buffer.append(character);
					readNextCharacter();
				}
				String identifier = buffer.toString();
				if(identifier.equalsIgnoreCase("true") || identifier.equalsIgnoreCase("false")){
				 return new TokenWord(Token.BOOLLIT, identifier, lineNumber);	
				}
				else if (mReservedKeyWord.containsKey(identifier)) {
					return new TokenWord(mReservedKeyWord.get(identifier), lineNumber);
				} else {
					return new TokenWord(Token.IDENT, identifier, lineNumber);
				}
			} else {
				reportError("Unidentified input token: '%c'", character);
				readNextCharacter();
				return getNextToken();
			}
		}
	}

	private void loadReservedKeyWord() {

		mReservedKeyWord.put(Token.IF.getValue(), Token.IF);
		mReservedKeyWord.put(Token.ELSE.getValue(), Token.ELSE);
		mReservedKeyWord.put(Token.THEN.getValue(), Token.THEN);
		mReservedKeyWord.put(Token.INT.getValue(), Token.INT);
		mReservedKeyWord.put(Token.BOOL.getValue(), Token.BOOL);
		mReservedKeyWord.put(Token.BEGIN.getValue(), Token.BEGIN);
		mReservedKeyWord.put(Token.END.getValue(), Token.END);
		mReservedKeyWord.put(Token.WHILE.getValue(), Token.WHILE);
		mReservedKeyWord.put(Token.DO.getValue(), Token.DO);
		mReservedKeyWord.put(Token.PROGRAM.getValue(), Token.PROGRAM);
		mReservedKeyWord.put(Token.VAR.getValue(), Token.VAR);
		mReservedKeyWord.put(Token.AS.getValue(), Token.AS);
		mReservedKeyWord.put(Token.DIV.getValue(), Token.DIV);
		mReservedKeyWord.put(Token.MOD.getValue(), Token.MOD);
		mReservedKeyWord.put(Token.WRITEINT.getValue(), Token.WRITEINT);
		mReservedKeyWord.put(Token.READINT.getValue(), Token.READINT);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
