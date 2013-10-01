package com.mek442.scanner;

import java.io.FileNotFoundException;
import java.util.Hashtable;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class Scanner {

	private int mLine;
	private boolean isError;
	private char unScannedChar;
	private Hashtable<String, Token> mReserved;

	private InputReader mInput;

	private String mFileName;

	public Scanner(String fileName) throws FileNotFoundException {

		this.mFileName = fileName;
		this.mInput = new InputReader(fileName);
		isError = false;

	}

	/**
	 * Scan the next token from input.
	 * 
	 * @return the the next scanned token.
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
