package com.mek442.tl13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mek442.node.Attribute;
import com.mek442.node.Counter;
import com.mek442.node.Declaration;
import com.mek442.node.Node;
import com.mek442.node.NodeUtil;
import com.mek442.node.Program;
import com.mek442.node.StatementSequence;
import com.mek442.parser.Parser;
import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class ParseTreeGenerator {

	Parser mParser;
	Map<String, Token> typeMap = new HashMap<String, Token>();

	public ParseTreeGenerator(Parser pParser) {
		mParser = pParser;

	}

	public String produceParseTree() {
		Program program = mParser.startParse();
		Node buildAST = program.buildAST();
		StringBuffer buffer = new StringBuffer();
		String output = "digraph parseTree { \n" + " ordering=out; ";
		buffer.append(output);
		Counter instance = Counter.getInstance();
		instance.getCount();

		treeTraversal(buildAST, null, 0, instance, buffer);
		buffer = new StringBuffer();
		buffer.append(output);
		printTree(buildAST, null, buffer);
		buffer.append("\n" + "}");
		return buffer.toString();
	}

	private void printTree(Node pNode, Node father, StringBuffer pBuffer) {
		if (pNode == null)
			return;
		
		if (pNode.getTokenValue() != null) {
			String label = NodeUtil.Label.replace("#", "" + pNode.getCount());

			if (pNode.hasError()) {
				label = label.replace("$", "/pastel13/1");

			} else {

				label = label.replace("$", pNode.getColor());

			}
			label = label.replace("@", getTokenValue(pNode));

			pBuffer.append(label);
			pBuffer.append("\n");

			pBuffer.append("n" + father.getCount() + " -> " + "n" + pNode.getCount());

			pBuffer.append("\n");

		}
		
		List<Node> childrenNodes = pNode.getChildNodes();

		if (pNode instanceof Program) {
			String program = NodeUtil.Program;
			String color = "/x11/lightgrey";
			if (pNode.hasError()) {
				color = "/pastel13/1";
			}
			buildASTview(0, pNode.getCount(), pBuffer, program, color);

		}

		if (pNode instanceof Declaration) {
			String program = "decl";
			if (childrenNodes.size() > 0)
				buildASTview(father.getCount(), pNode.getCount(), pBuffer, program, "white");

		}

		if (pNode instanceof StatementSequence) {
			String program = "stmt";
			if (childrenNodes.size() > 0)
				buildASTview(father.getCount(), pNode.getCount(), pBuffer, program, "white");

		}

		for (Node node : childrenNodes) {
			if(node!=null && pNode.getTokenValue()!=null && pNode.getTokenValue().getWord()==Token.ASGN && node.getTokenValue()!=null && node.getTokenValue().getWord()==Token.READINT){
				
			} else 	printTree(node, pNode, pBuffer);
		}

	}

	public void treeTraversal(Node pNode, Node father, int fCounter, Counter pCounter, StringBuffer pBuffer) {
		if (pNode == null)
			return;

		boolean isError = false;
		String color = "/x11/lightgrey";
		if (pNode.getTokenValue() != null) {
			TokenWord tokenValue = father.getTokenValue();
			Token currentToekn = pNode.getTokenValue().getWord();

			if (tokenValue != null && tokenValue.getWord() == Token.IDENT
					&& (currentToekn == Token.INT || currentToekn == Token.BOOL)) {
				Attribute attribute = new Attribute();
				attribute.setName(tokenValue.getIdentifier());
				attribute.setValue(currentToekn);
				pNode.setAttribute(attribute.getName(), attribute);
				// typeMap.put(tokenValue.getIdentifier(), currentToekn);
				father.setAttribute(attribute.getName(), attribute);

			} else if (Token.getINTOP().contains(currentToekn)) {
				Attribute attribute = new Attribute();
				attribute.setName("type");
				attribute.setValue(Token.INT);
				pNode.setAttribute(attribute.getName(), attribute);
				father.setAttribute(attribute.getName(), attribute);
				if (Token.getArithOP().contains(currentToekn)) {
					color = "/pastel13/3";
				} else {
					color = "/pastel13/2";
				}
			} else if (currentToekn == Token.IF || currentToekn == Token.WHILE) {
				Attribute attribute = new Attribute();
				attribute.setName("type");
				attribute.setValue(Token.BOOL);
				pNode.setAttribute(attribute.getName(), attribute);
				// color = "#e0e0e0";
			} else if (currentToekn == Token.THEN) {

			} else if (currentToekn == Token.NUM) {
				TokenWord word = pNode.getTokenValue();
				long value = Long.parseLong(word.getIdentifier().trim());
				Attribute temp = father.getAttributes().get("type");
				if (temp != null && temp.getValue() != Token.INT) {
					System.err.println("TYPE_ERROR " + Token.NUM);
					isError = true;
				} else if (value > 2147483647 || value < 0) {
					System.err.println("TYPE_ERROR " + Token.NUM);
					isError = true;
				} else {
					Attribute attribute = new Attribute();
					attribute.setName("type");
					attribute.setValue(Token.INT);
					pNode.setAttribute(attribute.getName(), attribute);
					father.setAttribute(attribute.getName(), attribute);
				}
			} else if (currentToekn == Token.IDENT) {
				Attribute temp = father.getAttributes().get(pNode.getTokenValue().getIdentifier());
				Attribute temp1 = father.getAttributes().get("type");
				if (temp != null && temp1 != null && temp.getValue() != temp1.getValue()) {
					System.err.println("TYPE_ERROR " + pNode.getTokenValue().getIdentifier());
					isError = true;
				} else if (temp == null) {
					for (Node tempNode : pNode.getChildNodes()) {
						if (tempNode.getTokenValue() != null) {
							Token typeToken = tempNode.getTokenValue().getWord();
							if (typeToken == Token.BOOL) {
								color = "/pastel13/2";
							} else
								color = "/pastel13/3";
						}
					}
				} else {
					Attribute attribute = new Attribute();
					attribute.setName("type");
					if (father.getTokenValue() != null && Token.getINTOP().contains(father.getTokenValue().getWord())) {
						attribute.setValue(Token.INT);
					} else
						attribute.setValue(temp.getValue());
					pNode.setAttribute(attribute.getName(), attribute);
					if (temp.getValue() == Token.BOOL) {
						color = "/pastel13/2";
					} else
						color = "/pastel13/3";
					// attribute.setName("type");
					father.setAttribute(attribute.getName(), attribute);
				}
			} else if (currentToekn == Token.BOOLLIT) {
				Attribute temp = father.getAttributes().get("type");
				if (temp != null && temp.getValue() != Token.BOOL) {
					System.err.println("TYPE_ERROR " + Token.BOOLLIT + " " + pNode.getCount() + " " + temp.getValue());
					isError = true;
				} else {
					Attribute attribute = new Attribute();
					attribute.setName("type");
					attribute.setValue(Token.BOOLLIT);
					pNode.setAttribute(attribute.getName(), attribute);
					father.setAttribute(attribute.getName(), attribute);
					color = "/pastel13/2";
				}
			} else if (currentToekn == Token.READINT) {
				Attribute temp = father.getAttributes().get("type");
				if (temp != null && temp.getValue() != Token.INT) {
					System.err.println("TYPE_ERROR " + Token.READINT);
					isError = true;
				}
			} else if (currentToekn == Token.WRITEINT) {

				Attribute attribute = new Attribute();
				attribute.setName("type");
				attribute.setValue(Token.INT);
				pNode.setAttribute(attribute.getName(), attribute);
				father.setAttribute(attribute.getName(), attribute);
			}

			Token printToken = pNode.getTokenValue().getWord();
			String label = NodeUtil.Label.replace("#", "" + pNode.getCount());
			if (isError) {
				label = label.replace("$", "/pastel13/1");
				father.setError(isError);
				father.setColor("/pastel13/1");
				pNode.setColor(color);
			} else {

				if (printToken == Token.NUM) {
					color = "/pastel13/3";
				} else if (printToken == Token.EQUAL) {
					color = "/pastel13/2";
				}
				label = label.replace("$", color);
				pNode.setColor(color);
			}
			label = label.replace("@", getTokenValue(pNode));
			if (printToken != Token.READINT) {
				pBuffer.append(label);
				pBuffer.append("\n");

				pBuffer.append("n" + father.getCount() + " -> " + "n" + pNode.getCount());

				pBuffer.append("\n");
			}
		}

		List<Node> childNodes = pNode.getChildNodes();
		int fatherCount = fCounter;// pCounter.getPrevCount();
		if (pNode instanceof Program) {
			String program = NodeUtil.Program;
			buildASTview(0, pNode.getCount(), pBuffer, program, color);

		}

		if (pNode instanceof Declaration) {
			String program = "decl";
			if (pNode.getChildNodes().size() > 0)
				buildASTview(father.getCount(), pNode.getCount(), pBuffer, program, "white");

		}

		if (pNode instanceof StatementSequence) {
			String program = "stmt";
			if (pNode.getChildNodes().size() > 0)
				buildASTview(father.getCount(), pNode.getCount(), pBuffer, program, "white");

		}

		// fatherCount =pCounter.getPrevCount();
		Map<String, Attribute> attributes = new HashMap<String, Attribute>();

		for (Node node : childNodes) {
			if (pNode != null) {
				attributes.putAll(pNode.getAttributes());
				if (pNode.getTokenValue() != null && pNode.getTokenValue().getWord() == Token.GT_EQUAL) {
				}

				if (node instanceof Declaration || node instanceof StatementSequence) {
					attributes.remove("type");
					setAttributes(node, attributes);
					node.setCount(pCounter.getCount());
				} else if (node != null && node.getTokenValue() == null) {
					setAttributes(node, attributes);
					node.setCount(pNode.getCount());
				} else if (node != null && node.getTokenValue() != null) {
					setAttributes(node, attributes);
					node.setCount(pCounter.getCount());
				}
			} else
				node.setCount(1);
			try {
				treeTraversal(node, pNode, fatherCount, pCounter, pBuffer);
				if (node != null)
					setAttributes(pNode, node);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	private void setAttributes(Node node, Node child) {
		Map<String, Attribute> pAttributes = child.getAttributes();
		if (child.hasError()) {
			node.setError(true);
		}

		setAttributes(node, pAttributes);

	}

	private void setAttributes(Node node, Map<String, Attribute> pAttributes) {
		if (node.getTokenValue() != null && Token.getINTOP().contains(node.getTokenValue().getWord())) {
			Attribute attribute = new Attribute();
			attribute.setValue(Token.INT);

			node.setAttribute("type", attribute);
		}
		// System.err.println("Nahid Mostafa "+node.getTokenValue().getWord());
		Set<String> keySet = pAttributes.keySet();
		for (String key : keySet) {
			Attribute attribute = pAttributes.get(key);
			if (Token.getINTOP().contains(attribute.getValue())) {
				if (key.equalsIgnoreCase("type")) {
					attribute.setValue(Token.INT);

					node.setAttribute(key, attribute);

				} else
					node.setAttribute(key, attribute);
			} else
				node.setAttribute(key, attribute);
		}

	}

	private String getTokenValue(Node pNode) {
		Token word = pNode.getTokenValue().getWord();
		if (Token.IDENT == word || Token.NUM == word || Token.BOOLLIT == word) {
			return pNode.getTokenValue().getIdentifier();
		}

		if (Token.ASGN == word) {
			List<Node> childNodes = pNode.getChildNodes();
			for (Node node : childNodes) {
				if (node.getTokenValue() != null && node.getTokenValue().getWord() == Token.READINT) {
					return pNode.getTokenValue().getWord().getValue() + " " + node.getTokenValue().getWord().getValue();
				}
			}
		}
		return pNode.getTokenValue().getWord().getValue();
	}

	private void buildASTview(int fCounter, int cCounter, StringBuffer pBuffer, String program, String color) {

		pBuffer.append("\n");
		String label = NodeUtil.Label.replace("#", "" + cCounter);
		label = label.replace("@", program);
		label = label.replace("$", color);
		if (!program.equalsIgnoreCase("program")) {
			label = label.replace("box", "none");
		}
		pBuffer.append(label);
		pBuffer.append("\n");
		if (!program.equalsIgnoreCase("program")) {
			pBuffer.append("n" + fCounter + " -> " + "n" + cCounter);
			pBuffer.append("\n");
		}
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
