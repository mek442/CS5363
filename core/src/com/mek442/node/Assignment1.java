package com.mek442.node;

import com.mek442.scanner.Token;
import com.mek442.scanner.TokenWord;

public class Assignment1 {

	private TokenWord mTokenWord;
	private Expression mParseExpression;

	public Assignment1(TokenWord pWord, Expression pParseExpression) {
		mTokenWord = pWord;
		mParseExpression = pParseExpression;

	}

	public TokenWord getTokenWord() {
		return mTokenWord;
	}

	public Expression getParseExpression() {
		return mParseExpression;
	}

	public String getOutPut(Counter pCounter, int father) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		int parent = pCounter.getCount();
		String label = NodeUtil.Label.replace("#", "" + parent);
		label = label.replace("@", NodeUtil.Assignment1);
		buffer.append(label);

		buffer.append("\n");

		buffer.append("n" + father + " -> " + "n" + parent);

		if (mTokenWord.getWord() == Token.READINT) {
			int child = pCounter.getCount();
			buffer.append("\n");
			label = NodeUtil.Label.replace("#", "" + child);
			label = label.replace("@", mTokenWord.getWord().getValue());
			buffer.append(label);
			buffer.append("\n");

			buffer.append("n" + parent + " -> " + "n" + child);
		}

		if (mParseExpression != null) {
			buffer.append(mParseExpression.getOutPut(pCounter, parent));
		}

		return buffer.toString();

	}

}
