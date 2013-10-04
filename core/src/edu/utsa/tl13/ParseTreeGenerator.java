package edu.utsa.tl13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.mek442.node.Counter;
import com.mek442.node.Program;
import com.mek442.parser.Parser;

public class ParseTreeGenerator {
	Program mProgram;

	public ParseTreeGenerator(Program pProgram) {
		mProgram = pProgram;
	}

	public String produceParseTree() {
		String output = "digraph parseTree { \n"  +" ordering=out; \n";
		output = output + mProgram.getOutPut(Counter.getInstance(), 0) + "\n }";
		return output;
	}

	public void writToFile(String pContent) {
		FileOutputStream fop = null;
		File file;

		try {

			file = new File("C:\\Users\\Mostafa\\git\\mek442\\core\\tests\\result.pt.dot");
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
		try {
			Parser parser = new Parser("C:\\Users\\Mostafa\\git\\mek442\\core\\tests\\test2.txt");
			Program startParse = parser.startParse();
			ParseTreeGenerator parseTreeGenerator = new ParseTreeGenerator(startParse);
			parseTreeGenerator.writToFile(parseTreeGenerator.produceParseTree());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
