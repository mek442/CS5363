package com.mek442.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public class InputReader {
	private LineNumberReader mLineNumberReader;

	public InputReader(String pFileName) throws FileNotFoundException {
		FileReader fileReader = new FileReader(new File(pFileName));
		mLineNumberReader = new LineNumberReader(fileReader);
	}

	public char nextChar() {
		char ch = (char) -1;
		try {
			ch = (char) mLineNumberReader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ch;
	}

	public int getLineNumber() {

		return mLineNumberReader.getLineNumber();
	}

	public void close() {
		try {
			mLineNumberReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
