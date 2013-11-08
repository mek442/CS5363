package com.mek442.tl13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.mek442.node.Attribute;
import com.mek442.node.Block;
import com.mek442.node.BlockCounter;
import com.mek442.node.Counter;
import com.mek442.node.Declaration;
import com.mek442.node.Node;
import com.mek442.node.NodeUtil;
import com.mek442.node.Program;
import com.mek442.node.RegisterCounter;
import com.mek442.node.StatementSequence;
import com.mek442.parser.Parser;
import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class ParseTreeGenerator {

	Parser mParser;
	Map<String, Token> typeMap = new HashMap<String, Token>();
	Stack<String> mStack = new Stack<String>();
	List<String> mCodes = new ArrayList<String>();
	RegisterCounter registerCounter = RegisterCounter.getInstance();
	BlockCounter blockCounter = BlockCounter.getInstance();
	static StringBuffer mBuffer = new StringBuffer();
	Map<String, Block> mBlockMap = new HashMap<String, Block>();
	String currentBlock = null;
	String lastUntrackedBlock = null;
	int counter = 0;

	public ParseTreeGenerator(Parser pParser) {
		mParser = pParser;

	}

	public String produceParseTree() {
		Program program = mParser.startParse();
		Node buildAST = program.buildAST(null);
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
		
		if(buildAST.hasError()){
			System.err.println("Failure to produce ILOC, Please Fix the error in the source File");
			return "";
		}

		Block block = new Block(blockCounter.getCount());
		mBlockMap.put(block.getLabel(), block);
		currentBlock = block.getLabel();
		mStack.push(block.getLabel());
		generateILOc(buildAST, null);

		if (!mStack.isEmpty()) {
			Block block1 = mBlockMap.get(mStack.pop());
			block1.setBlock(block1.getBlock().concat(mBuffer.toString()));
		}

		if (!mStack.isEmpty()) {
			String t = mStack.pop();
		}

		mCodes.add(mBuffer.toString());
		StringBuffer blockBuffer = new StringBuffer();
		blockBuffer.append("digraph graphviz { " + "\n" + " node [shape = none]; " + "\n" + "  edge [tailport = s]; "
				+ "\n" + " entry  " + "\n" + "subgraph cluster { " + "\n" + " color=\"/x11/white\" " + "\n");

		traverseBlock(mBlockMap.get("B0"), blockBuffer);
		blockBuffer.append("}");
		blockBuffer.append("entry -> n0" + "\n");
		blockBuffer.append("n" + counter + " -> " + "exit" + "\n");
		blockBuffer.append("}");

		return blockBuffer.toString();// buffer.toString();
	}

	private void traverseBlock(Block pBlock, StringBuffer buffer) {
		if (pBlock == null) {
			return;
		}
		String label = NodeUtil.Label2.replace("#", "" + pBlock.getNumber());
		String name = decorateTable(pBlock);// ;pBlock.getLabel()+" \n"+
											// pBlock.getBlock() ;
		label = label.replace("@", name);
		buffer.append(label);
		buffer.append("\n");
		pBlock.setVisited(true);
		List<String> children = pBlock.getChildren();
		for (String string : children) {
			Block block = mBlockMap.get(string);

			if (block != null && !block.isVisited())
				traverseBlock(block, buffer);
			if (block != null) {
				if (block.getChildren().size() == 0) {
					counter = block.getNumber();
				}
				buffer.append("n" + pBlock.getNumber() + " -> " + "n" + block.getNumber() + "\n");
			}
		}
	}

	private String decorateTable(Block pBlock) {
		StringBuffer table = new StringBuffer();
		table.append("<<table border='0'> <tr><td border='1'>" + pBlock.getLabel() + "</td> </tr>");
		String block = pBlock.getBlock();
		String[] token = block.split("\n");
		for (int i = 0; i < token.length; i++) {
			token[i] = token[i].replace(">", " &gt; ");
			token[i] = token[i].replace("<", " &lt; ");
			table.append("<tr><td>" + token[i] + "</td> </tr>");

		}
		table.append("</table>>");

		return table.toString();
	}

	private void generateILOc(Node pNode, Node father) {
		if (pNode == null)
			return;
		List<Node> childrenNodes = pNode.getChildNodes();
		String localBlock = "";

		if (pNode.getTokenValue() != null && pNode.getTokenValue().getWord() == Token.WHILE) {

			String target = mStack.pop();
			Block block = mBlockMap.get(target);

			Block block2 = new Block(blockCounter.getCount());
			block.setChildren(block2.getLabel());
			mBuffer.append("jumpI ->" + block2.getLabel());
			mCodes.add(mBuffer.toString());
			if (!block.isProcess()) {
				block.setBlock(block.getBlock().concat(mBuffer.toString()));
				block.setProcess(true);
			}
			mBuffer = new StringBuffer();
			mStack.push(block2.getLabel());
			mBlockMap.put(block.getLabel(), block);
			mBlockMap.put(block2.getLabel(), block2);

		} else if (pNode.getTokenValue() != null && pNode.getTokenValue().getWord() == Token.IF) {

			String target = mStack.pop();
			Block block = mBlockMap.get(target);

			mBlockMap.put(block.getLabel(), block);

			updateBlockinfo(block, pNode);
			currentBlock = block.getLabel();
			localBlock = currentBlock;
			if (block.isWithElse()) {
				mStack.push(currentBlock);
			}

		}

		String sElse = "";
		for (Node node : childrenNodes) {

			if (node != null && node.getTokenValue() != null && pNode.getTokenValue() != null
					&& pNode.getTokenValue().getWord() == Token.IF) {

				Block block = mBlockMap.get(localBlock);
				if (node.getTokenValue().getWord() == Token.THEN) {

					String target = mStack.pop();
					Block b1 = new Block(blockCounter.getCount());
					Block b2 = new Block(blockCounter.getCount());
					mBuffer.append("cbr " + target + " -> " + b1.getLabel() + " , " + b2.getLabel() + "\n");
					block.setBlock(mBuffer.toString());
					block.setProcess(true);
					mBuffer = new StringBuffer();

					mBlockMap.put(b1.getLabel(), b1);
					mBlockMap.put(b2.getLabel(), b2);
					block.setChildren(b1.getLabel());
					block.setChildren(b2.getLabel());
					if (!block.isWithElse()) {
						mStack.push(b2.getLabel());
						mStack.push(currentBlock);
					}
					mStack.push(b1.getLabel());
					sElse = b2.getLabel();

				} else if (node.getTokenValue().getWord() == Token.ELSE) {
					mStack.push(sElse);
					block.setWithElse(true);
				}
			}

			if (node != null && pNode.getTokenValue() != null && pNode.getTokenValue().getWord() == Token.WHILE) {
				// TokenWord whileChild = node.getTokenValue();
				Node father1 = node.getFather();
				if (father1 != null && father1.getTokenValue() != null && father1.getTokenValue().getWord() == Token.DO) {
					String target = mStack.pop();
					Block b1 = new Block(blockCounter.getCount());
					Block b2 = new Block(blockCounter.getCount());
					mBuffer.append("cbr " + target + " -> " + b1.getLabel() + " , " + b2.getLabel() + "\n");
					mCodes.add(mBuffer.toString());
					String pop = mStack.pop();
					Block b = mBlockMap.get(pop);
					b.setBlock(b.getBlock().concat(mBuffer.toString()));
					b.setChildren(b1.getLabel());
					b.setChildren(b2.getLabel());
					mBlockMap.put(b1.getLabel(), b1);
					mBlockMap.put(b2.getLabel(), b2);
					mBuffer = new StringBuffer();

					if (father1.getTokenValue().getWord() == Token.DO) {

						// target = mStack.pop();
						mStack.push(b2.getLabel());
						mStack.push(b.getLabel());
						mStack.push(b1.getLabel());
					}

				}

			}

			generateILOc(node, pNode);
		}

		if (pNode != null) {
			generateCode(pNode);
		}

	}

	private void generateCode(Node pNode) {
		if (pNode.getTokenValue() != null) {
			Token word = pNode.getTokenValue().getWord();
			TokenWord tword = pNode.getTokenValue();
			if (word == Token.NUM) {
				String target = "r_" + registerCounter.getCount();
				mBuffer.append("loadI " + tword.getIdentifier() + " => " + target + " \n");
				mStack.push(target);

			} else if (word == Token.INT) {

				mStack.push("int");

			} else if (word == Token.BOOL) {

				mStack.push("bool");

			} else if (word == Token.READINT) {
				mStack.push("readInt");

			} else if (word == Token.WRITEINT) {
				String target = mStack.pop();
				mBuffer.append(" writeInt   " + target + "\n");

			} else if (word == Token.THEN) {
				String target = mStack.pop();
				Block block = mBlockMap.get(target);
				block.setBlock(block.getBlock().concat(mBuffer.toString()));
				block.setToken(word);
				mBlockMap.put(block.getLabel(), block);

				mBuffer = new StringBuffer();

			} else if (word == Token.ELSE) {

				String target = mStack.pop();
				Block block = mBlockMap.get(target);
				block.setBlock(block.getBlock().concat(mBuffer.toString()));
				block.setToken(word);
				mBlockMap.put(block.getLabel(), block);
				mBuffer = new StringBuffer();

			} else if (word == Token.BOOLLIT) {
				String target = "r_" + registerCounter.getCount();
				mBuffer.append("loadI " + tword.getIdentifier() + " => " + target + " \n");
				mStack.push(target);

			} else if (word == Token.IDENT && pNode.isDeclaration()) {
				String target = "r_" + tword.getIdentifier();
				String type = mStack.pop();
				String source = "0";
				if (type.equalsIgnoreCase("bool")) {
					source = "false";
				}
				mBuffer.append("loadI " + source + " => " + target + " \n");

			} else if (word == Token.IDENT) {
				String target = "r_" + tword.getIdentifier();

				mStack.push(target);

			} else if (word == Token.IF) {
				String target = "";
				String temp1 = mStack.pop();
				Block block1 = mBlockMap.get(temp1);

				if (block1.isWithElse()) {
					Block block = new Block(blockCounter.getCount());
					mBlockMap.put(block.getLabel(), block);
					target = block.getLabel();
					mStack.push(target);

				} else {
					target = mStack.pop();
					mStack.push(target);

				}

				// System.err.println(block1.getLabel()+"IF"+block1.getBlock());
				List<String> children = block1.getChildren();

				for (String string : children) {
					Block temp = mBlockMap.get(string);
					if (!temp.isProcess() && !temp.getLabel().equalsIgnoreCase(target)) {
						temp.setBlock(temp.getBlock().concat("jumpI ->" + target));
						temp.setChildren(target);
						temp.setProcess(true);
					}

				}

				if (lastUntrackedBlock != null) {
					Block temp = mBlockMap.get(lastUntrackedBlock);
					if (!temp.isProcess()) {
						temp.setBlock(temp.getBlock().concat("jumpI ->" + target));
						temp.setChildren(target);
						temp.setProcess(true);
						mBlockMap.put(temp.getLabel(), temp);
					}
				}

				lastUntrackedBlock = target;

			} else if (word == Token.WHILE) {

				String temp = mStack.pop();
				mStack.push(temp);
				lastUntrackedBlock = temp;

			} else if (Token.getINTOP().contains(word)) {
				String target = "r_" + registerCounter.getCount();
				String popRight = mStack.pop();
				String popLeft = mStack.pop();
				if (word == Token.MOD) {
					mBuffer.append(getOPcode(Token.DIV) + popLeft + " , " + popRight + " => " + target + " \n");
					String divResult = target;
					target = "r_" + registerCounter.getCount();
					mBuffer.append(getOPcode(Token.MUL) + divResult + " , " + popRight + " => " + target + " \n");
					String mulResult = target;
					target = "r_" + registerCounter.getCount();
					mBuffer.append(getOPcode(Token.MINUS) + popLeft + " , " + mulResult + " => " + target + " \n");
				} else {
					mBuffer.append(getOPcode(word) + popLeft + " , " + popRight + " => " + target + " \n");
				}
				mStack.push(target);

			} else if (word == Token.ASGN) {
				String popRight = mStack.pop();
				String popLeft = mStack.pop();
				if (popRight.equalsIgnoreCase("readInt")) {
					mBuffer.append(popRight + " => " + popLeft + " \n");
				} else
					mBuffer.append("i2i " + popRight + " => " + popLeft + " \n");

			}

		} else {
			Node father = pNode.getFather();
			if (father != null && father.getTokenValue() != null) {

				Token token = father.getTokenValue().getWord();
				if (token == Token.DO) {
					String target = mStack.pop();
					Block block = mBlockMap.get(target);

					target = mStack.pop();
					mBuffer.append(" jumpI -> " + target);
					if (!block.isProcess()) {
						block.setBlock(block.getBlock().concat(mBuffer.toString()));
						block.setChildren(target);
						block.setProcess(true);
					}
					mCodes.add(mBuffer.toString());
					mBuffer = new StringBuffer();

				}

			}
		}

	}

	private void updateBlockinfo(Block pBlock, Node pNode) {
		List<Node> childrenNodes = pNode.getChildNodes();

		for (Node node : childrenNodes) {
			if (node != null && node.getTokenValue() != null && node.getTokenValue() != null
					&& node.getTokenValue().getWord() == Token.ELSE) {
				pBlock.setWithElse(true);
			}
		}

	}

	private String getOPcode(Token pWord) {
		if (pWord == Token.PLUS) {
			return "add ";
		} else if (pWord == Token.MINUS) {
			return "sub ";
		} else if (pWord == Token.MUL) {
			return "mult ";
		} else if (pWord == Token.DIV) {
			return "div ";
		} else if (pWord == Token.GT) {
			return "cmp_GT ";
		} else if (pWord == Token.GT_EQUAL) {
			return "cmp_GE ";
		} else if (pWord == Token.LT) {
			return "cmp_LT ";
		} else if (pWord == Token.LT_EQUAL) {
			return "cmp_LE ";
		} else if (pWord == Token.EQUAL) {
			return "cmp_EQ ";
		} else if (pWord == Token.EQUAL) {
			return "cmp_NE ";
		}

		return null;
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
			if (node != null && pNode.getTokenValue() != null && pNode.getTokenValue().getWord() == Token.ASGN
					&& node.getTokenValue() != null && node.getTokenValue().getWord() == Token.READINT) {

			} else
				printTree(node, pNode, pBuffer);
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
					pNode.setError(true);
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
							} else if (typeToken == Token.INT) {
								color = "/pastel13/3";
							} else {
								color = "/pastel13/1";
								pNode.setError(true);
								isError = true;
								System.err.println("ERROR " + "declaration of ID "
										+ pNode.getTokenValue().getIdentifier() + " expected");
							}
						}
					}
					if (pNode.getChildNodes().size() < 1) {
						color = "/pastel13/1";
						pNode.setError(true);
						isError = true;
						System.err.println("ERROR " + "declaration of ID " + pNode.getTokenValue().getIdentifier()
								+ " expected");
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
