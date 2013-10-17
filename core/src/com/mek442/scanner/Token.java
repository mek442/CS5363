package com.mek442.scanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 * 
 */
public enum Token {
	BOOL("bool"), INT("int"), IF("if"), THEN("then"), ELSE("else"), BEGIN("begin"), END("end"), WHILE("while"), DO("do"), PROGRAM(
			"program"), VAR("var"), AS("as"), LP("("), RP(")"), ASGN(":="), SC(";"), MUL("*"), DIV("div"), MOD("mod"), PLUS(
			"+"), MINUS("-"), EQUAL("="), NOT_EQUAL("!="), LT("<"), GT(">"), LT_EQUAL("<="), GT_EQUAL(">="), WRITEINT(
			"writeInt"), READINT("readInt"), NUM("num"), BOOLLIT("boollit"), IDENT("ident"), EOF("EOF");

	private String name;

	Token(String pName) {
		this.name = pName;
	}

	public String getValue() {
		return this.name;
	}

	public static Set<Token> getOP2() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(MUL, MOD, DIV));
		return set;
	}
	
	public static Set<Token> getFactor() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(IDENT, NUM, BOOLLIT,LP));
		return set;
	}

	public static Set<Token> getOP3() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(PLUS, MINUS));
		return set;
	}

	public static Set<Token> getOP4() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(EQUAL, NOT_EQUAL, LT, LT_EQUAL, GT, GT_EQUAL));
		return set;
	}
	
	public static Set<Token> getINTOP() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(MUL,MOD,DIV,PLUS,MINUS, LT, LT_EQUAL, GT, GT_EQUAL));
		return set;
	}
	
	public static Set<Token> getArithOP() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(MUL,MOD,DIV,PLUS,MINUS));
		return set;
	}

	public static Set<Token> getStatement() {
		Set<Token> set = new HashSet<Token>(Arrays.asList(IDENT, IF, WHILE, WRITEINT));
		return set;
	}

}
