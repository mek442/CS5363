package edu.utsa.tl13;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.mek442.parser.Parser;
import com.mek442.tl13.ParseTreeGenerator;

public class Compiler {
	
	ParseTreeGenerator treeGenerator;
	 public Compiler(String inputFile, String outPutFile) throws FileNotFoundException  {
		Parser parser = new Parser(inputFile);
		treeGenerator = new ParseTreeGenerator(parser);
		treeGenerator.writToFile(treeGenerator.produceParseTree(), outPutFile);
	}
	
	public static void main(String[] args) throws IOException {
		String inputFileName = args[0];
		int baseNameOffset = inputFileName.length() - 5;

		String baseName;
		if (inputFileName.substring(baseNameOffset).equals(".tl13"))
			baseName = inputFileName.substring(0, baseNameOffset);
		else
			throw new RuntimeException("inputFileName does not end in .tl13");

		String parseOutName = baseName + ".pt.dot";

		System.out.println("Input file: " + inputFileName);
		System.out.println("Output file: " + parseOutName);

		Compiler compiler = new Compiler(inputFileName, parseOutName);
	}
}
