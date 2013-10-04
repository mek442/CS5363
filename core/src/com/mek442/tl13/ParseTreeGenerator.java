package com.mek442.tl13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.mek442.node.Counter;
import com.mek442.node.Program;
import com.mek442.parser.Parser;

public class ParseTreeGenerator {
  
	Parser mParser;
	
	public ParseTreeGenerator(Parser pParser) {
		mParser = pParser;
				
	}

	public String produceParseTree() {
		Program program = mParser.startParse();
		String output = "digraph parseTree { \n"  +" ordering=out; \n";
		output = output + program.getOutPut(Counter.getInstance(), 0) + "\n }";
		return output;
	}

	public void writToFile(String pContent, String fileName) {
		FileOutputStream fop = null;
		File file;

		try {

			file = new File(fileName);
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(file.getAbsolutePath());

			byte[] contentInBytes = pContent.getBytes();

			fop.write(contentInBytes);

			fop.flush();
			fop.close();
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
	}

}
