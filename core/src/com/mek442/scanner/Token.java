package com.mek442.scanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
enum Token {
	BOOL("bool"), INT("int"), IF("if"), THEN("then"), ELSE("else"), BEGIN("begin"), END("end"), WHILE("while"), DO("do"), PROGRAM(
			"program"), VAR("var"), AS("as"), LP("("), RP(")"), ASGN(":="), SC(";"), MUL("*"), DIV("div"), MOD("mod"), PLUS(
			"+"), MINUS("-"), EQUAL("="), NOT_EQUAL("!="), LT("<"), GT(">"), LT_EQUAL("<="), GT_EQUAL(">="),
			WRITEINT("writeInt"),READINT("readInt");

	private String name;

	Token(String pName) {
		this.name = name;
	}

	public String getValue() {
		return this.name;
	}

	public List<Token> getOP2() {
		return Collections.unmodifiableList(Arrays.asList(MUL, MOD, DIV));
	}

	public List<Token> getOP3() {
		return Collections.unmodifiableList(Arrays.asList(PLUS, MINUS));
	}

	public List<Token> getOP4() {
		return Collections.unmodifiableList(Arrays.asList(EQUAL, NOT_EQUAL, LT, LT_EQUAL, GT, GT_EQUAL));
	}

}
