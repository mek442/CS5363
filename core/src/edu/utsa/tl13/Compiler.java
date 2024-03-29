package edu.utsa.tl13;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.mek442.parser.Parser;
import com.mek442.tl13.OutputHolder;
import com.mek442.tl13.ParseTreeGenerator;

public class Compiler {
	
	ParseTreeGenerator treeGenerator;
	 public Compiler(String inputFile, String outPutFile) throws FileNotFoundException  {
		Parser parser = new Parser(inputFile);
		treeGenerator = new ParseTreeGenerator(parser);
		OutputHolder Outputtree = treeGenerator.produceParseTree();
		treeGenerator.writToFile(Outputtree.getAST(), outPutFile+".ast.dot");
		System.out.println("Output file: " + outPutFile+".ast.dot");
		treeGenerator.writToFile(Outputtree.getILOC(), outPutFile+".iloc.cfg.dot");
		System.out.println("Output file: " + outPutFile+".iloc.cfg.dot");
		treeGenerator.writToFile(Outputtree.getCfgAfterPhi(), outPutFile+".phi.cfg.dot");
		System.out.println("Output file: " + outPutFile+".phi.cfg.dot");
		treeGenerator.writToFile(Outputtree.getCfgAfterRenaming(), outPutFile+".rename_phi.cfg.dot");
		System.out.println("Output file: " + outPutFile+".rename_phi.cfg.dot");
		treeGenerator.writToFile(Outputtree.getCfgOptimized(), outPutFile+".optimised.cfg.dot");
		System.out.println("Output file: " + outPutFile+".optimised.cfg.dot");
		treeGenerator.writToFile(Outputtree.getCFGRemovePhi(), outPutFile+".removed_phi.cfg.dot");
		System.out.println("Output file: " + outPutFile+".removed_phi.cfg.dot");
		
		treeGenerator.writToFile(Outputtree.getMIPS(), outPutFile+".s");
		System.out.println("Output file: " + outPutFile+".s");
		treeGenerator.writToFile(Outputtree.getOptimiseMIPS(), outPutFile+"_optimised.s");
		System.out.println("Output file: " + outPutFile+"_optimised.s");
	}
	
	public static void main(String[] args) throws IOException {
		String inputFileName = args[0];
		int baseNameOffset = inputFileName.length() - 5;

		String baseName;
		if (inputFileName.substring(baseNameOffset).equals(".tl13"))
			baseName = inputFileName.substring(0, baseNameOffset);
		else
			throw new RuntimeException("inputFileName does not end in .tl13");

		String parseOutName = baseName ;

		System.out.println("Input file: " + inputFileName);

		Compiler compiler = new Compiler(inputFileName, parseOutName);
	}
}
