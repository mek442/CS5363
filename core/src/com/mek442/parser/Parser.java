package com.mek442.parser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mek442.node.Assignment;
import com.mek442.node.Assignment1;
import com.mek442.node.Declaration;
import com.mek442.node.ElseClause;
import com.mek442.node.Expression;
import com.mek442.node.Expression1;
import com.mek442.node.Factor;
import com.mek442.node.IfStatement;
import com.mek442.node.Program;
import com.mek442.node.SimpleExpression;
import com.mek442.node.SimpleExpression1;
import com.mek442.node.Statement;
import com.mek442.node.StatementSequence;
import com.mek442.node.Term;
import com.mek442.node.Term1;
import com.mek442.node.Type;
import com.mek442.node.WhileStatement;
import com.mek442.node.WriteInt;
import com.mek442.scanner.Token;
import com.mek442.scanner.TokenManager;
import com.mek442.scanner.TokenWord;

// TODO: Auto-generated Javadoc
/**
 * The Class Parser.
 *
 * @author Shaikh Mostafa<mek442@my.utsa.edu>
 */
public class Parser {

	/** The Token manager. */
	TokenManager mTokenManager;
	
	/** The is error. */
	private boolean isError = false;
	
	/** The is recovered. */
	private boolean isRecovered = true;

	/**
	 * Instantiates a new parser.
	 *
	 * @param pFileName the file name
	 * @throws FileNotFoundException the file not found exception
	 */
	public Parser(String pFileName) throws FileNotFoundException {
		mTokenManager = new TokenManager(pFileName);
		mTokenManager.next();

	}

	/**
	 * Report error.
	 *
	 * @param message the message
	 * @param args the args
	 */
	private void reportError(String message, Object... args) {
		isError = true;
		System.err.printf(message, args);
		System.err.println();
	}

	/**
	 * <pre>
	 * <program> ::= program <declarations> begin <statementSequence> end
	 * </pre>.
	 *
	 * @return the program
	 */
	public Program startParse() {
		TokenWord tokenP = mTokenManager.token();
		mustBe(Token.PROGRAM);
		
		Declaration declaration = null;
		StatementSequence parseStatementSequences =null;

		if (isMatch(Token.VAR)) {

			declaration = parseDeclaration();

		}
		TokenWord tokenB = mTokenManager.token();
		
		mustBe(Token.BEGIN);

		if (Token.getStatement().contains(mTokenManager.token().getWord())) {

			parseStatementSequences = parseStatementSequences();

		}
		TokenWord tokenE = mTokenManager.token();
		mustBe(Token.END);

		return new Program(tokenP, declaration, tokenB, parseStatementSequences, tokenE);
	}

	
    /**
     * <pre>
     * <statementSequence> ::= <statement> SC <statementSequence> | ε
     * </pre>
     * 
     *
     * @return the statement sequence
     */
	private StatementSequence parseStatementSequences() {
		
		Statement statement = parseStatement();
		TokenWord token = mTokenManager.token();
		mustBe(Token.SC);

		StatementSequence parseStatementSequences = new StatementSequence(null, null, null);
		if (Token.getStatement().contains(mTokenManager.token().getWord())) {
			parseStatementSequences = parseStatementSequences();

		}

		return new StatementSequence(statement, token, parseStatementSequences);

	}
	
	/**
	 * <pre>
	 * <statement> ::= <assignment> | <ifStatement>  | <whileStatement>  | <writeInt>
	 * </pre>
	 * 
	 *
	 * @return the statement
	 */
	private Statement parseStatement() {
		Assignment parseAssignment = null;
		IfStatement parseIfStatement = null;
		WhileStatement parseWhileStatement = null;
		WriteInt parseWriteExpression = null;
		if(isMatch(Token.IDENT)){
		   	 parseAssignment = parseAssignment();
		}else if(isMatch(Token.IF)){
			parseIfStatement = parseIfStatement();
		}else if(isMatch(Token.WHILE)){
			parseWhileStatement = parseWhileStatement();
		}else if(isMatch(Token.WRITEINT)){
			parseWriteExpression = parseWriteExpression();
		}
		
		
		return new Statement(parseAssignment, parseIfStatement,parseWhileStatement,parseWriteExpression);
		
	}

	
	/**
	 * <pre>
	 * <assignment> ::= ident ASGN <assignment1>
	 * </pre>.
	 *
	 * @return the assignment
	 */
	private Assignment parseAssignment() {
		TokenWord ident = mTokenManager.token();
		mustBe(Token.IDENT);
		TokenWord asgn = mTokenManager.token();
		mustBe(Token.ASGN);
		Assignment1 parseAssignment1 = parseAssignment1();
		return new Assignment(ident,asgn,parseAssignment1);
	}
	
	
	/**
	 * <pre>
	 * <assignment1> ::= <expression> | READINT
	 * </pre>.
	 *
	 * @return the assignment
	 */
	private Assignment1 parseAssignment1() {
		
		 TokenWord word = mTokenManager.token();
		 Expression parseExpression = null;
		if(haveExpectedToekn(Token.READINT)){
			
		}else{
		  parseExpression = parseExpression();
		}
		
		
		return new Assignment1(word,parseExpression);
	}
	
	/**
	 * <writeInt> ::= WRITEINT <expression>
	 */
	private WriteInt parseWriteExpression() {
		TokenWord token = mTokenManager.token();
		mustBe(Token.WRITEINT);
		Expression parseExpression = parseExpression();
		
		return new WriteInt(token, parseExpression);
		
		
	}
/*
 * <elseClause> ::= ELSE <statementSequence>| ε
 * 
 */
	private ElseClause parseElseExpression() {
		TokenWord token = mTokenManager.token();
		if (haveExpectedToekn(Token.ELSE)) {
			StatementSequence parseStatementSequences = parseStatementSequences();

			return new ElseClause(token, parseStatementSequences);
		} else
			return new ElseClause(null, null);

	}
	
	/*
	 * <ifStatement> ::= IF <expression> THEN <statementSequence> <elseClause> END
	 */
	private IfStatement parseIfStatement() {
		TokenWord ifT = mTokenManager.token();
		mustBe(Token.IF);
		Expression parseExpression = parseExpression();
		TokenWord thenT = mTokenManager.token();
		mustBe(Token.THEN);
		StatementSequence parseStatementSequences = parseStatementSequences();
		ElseClause parseElseExpression = parseElseExpression();
		TokenWord endT = mTokenManager.token();
		mustBe(Token.END);
		return new IfStatement(ifT,parseExpression,thenT,parseStatementSequences, parseElseExpression ,endT);
	}
	/**
	 * Parses the while statement.
	 *<whileStatement> ::= WHILE <expression> DO <statementSequence> END

	 * @return the while statement
	 */
	private WhileStatement parseWhileStatement() {
		TokenWord whileT = mTokenManager.token();
		mustBe(Token.WHILE);
		Expression parseExpression = parseExpression();
		TokenWord dOT = mTokenManager.token();
		mustBe(Token.DO);
		StatementSequence parseStatementSequences = parseStatementSequences();
		TokenWord endT = mTokenManager.token();
		mustBe(Token.END);
		return new WhileStatement(whileT,parseExpression,dOT,parseStatementSequences,endT);
	}

	
	/*<expression> ::= <simpleExpression> <expression1>
	
	   
	*/
	private Expression parseExpression(){
		
	    SimpleExpression parseSimpleExpression = parseSimpleExpression();
	    Expression1 expression1 = parseExpression1();
	    
	    return new Expression(parseSimpleExpression, expression1);
	
	}
	
	/*
	 *   <expression1> ::= OP4 <simpleExpression> |e
	 */
	private Expression1 parseExpression1() {
		TokenWord word = mTokenManager.token();

		if (haveExpectedToekn(Token.EQUAL) || haveExpectedToekn(Token.NOT_EQUAL) || haveExpectedToekn(Token.LT)
				|| haveExpectedToekn(Token.LT_EQUAL) || haveExpectedToekn(Token.GT)
				|| haveExpectedToekn(Token.GT_EQUAL)) {
			SimpleExpression parseSimpleExpression = parseSimpleExpression();
			return new Expression1(word, parseSimpleExpression);
		}

		else
			return new Expression1(null, null);
	}
	
	/*<simpleExpression> ::= <term> <simpleExpression1>
	
   
*/
	
	private SimpleExpression parseSimpleExpression(){
			Term parseTerm = parseTerm();
		    SimpleExpression1 parseSimpleExpression1 = parseSimpleExpression1();
		    
		    return new SimpleExpression(parseTerm, parseSimpleExpression1);
		
	}
	
	/*
	 *   <simpleExpression1> ::= OP3 <term> |e
	 */
 private SimpleExpression1 parseSimpleExpression1(){
	TokenWord word = mTokenManager.token();
	if(haveExpectedToekn(Token.PLUS) || haveExpectedToekn(Token.MINUS)){
		Term parseTerm = parseTerm();
		return new SimpleExpression1(word, parseTerm);
	}
	else return new SimpleExpression1(null, null);
	}
	
	/**
	 * <pre>
	 * <term> ::= <factor><term1>
	 * 
	 * </pre>.
	 *
	 * @return the term
	 */
	private Term parseTerm() {
		Factor parseFactor = parseFactor();
		Term1 parseTerm1 = parseTerm1();

		return new Term(parseFactor, parseTerm1);
	}
	
	
	
	/**
	 * <pre>
	 * <term1> ::= OP2 <factor>|e
	 * </pre>
	 *
	 * @return the term1
	 */
	private Term1 parseTerm1() {
		TokenWord token = mTokenManager.token();
		if (haveExpectedToekn(Token.MUL) || haveExpectedToekn(Token.MOD) || haveExpectedToekn(Token.DIV)) {

			Factor parseFactor = parseFactor();

			return new Term1(token, parseFactor);
		}

		else
			return new Term1(null, null);
		
	}

	/**
	 * Parses the factor.
	 * <pre>
	 * <factor> ::= ident | num | boollit | LP <expression> RP
	 * </pre>
	 * @return the factor
	 */
	private Factor parseFactor(){
		
		 TokenWord token = mTokenManager.token();
		if (haveExpectedToekn(Token.IDENT)|| haveExpectedToekn(Token.NUM) || haveExpectedToekn(Token.BOOLLIT)) {
		 return new Factor(token,null,null);
		}else{
			mustBe(Token.LP);
			
			Expression parseExpression = parseExpression();
			TokenWord token2 = mTokenManager.token();
			mustBe(Token.RP);
			
			return new Factor(token, parseExpression,token2);
			
		}
		
	}

	/**
	 * <pre>
	 * <declarations> ::= var ident as <type> SC <declarations> | ε
	 * </pre>.
	 *
	 * @return the declaration
	 */

	public Declaration parseDeclaration() {
		TokenWord tokenVar = mTokenManager.token();
		
		mustBe(Token.VAR);
		TokenWord tokenID = mTokenManager.token();
		mustBe(Token.IDENT);
		TokenWord tokenAs = mTokenManager.token();
		mustBe(Token.AS);
		Type parseType = parseType();
		TokenWord tokenSc = mTokenManager.token();
		mustBe(Token.SC);

		Declaration declaration = null;
		if (isMatch(Token.VAR)) {
			declaration = parseDeclaration();
		}

		return new Declaration(tokenVar, tokenID, tokenAs, parseType, tokenSc, declaration);
	}

	/**
	 * <pre>
	 * <type>  ::=  BOOL | INT
	 * </pre>.
	 *
	 * @return the type
	 */
	public Type parseType() {
		TokenWord token = mTokenManager.token();
		if (haveExpectedToekn(Token.BOOL)|| haveExpectedToekn(Token.INT)) {
			return new Type(token);
		} else {
			isError = true;
			return new Type(null);
		}

	}

	/**
	 * Must be.
	 *
	 * @param pToken the token
	 */
	private void mustBe(Token pToken) {
		if (mTokenManager.token().getWord() == pToken) {
			isRecovered = true;
			mTokenManager.next();
		} else if (isRecovered) {
			isRecovered = false;
			
			reportError("PARSER ERROR %s found where %s sought in line no %d ", mTokenManager.token().getWord(), pToken, mTokenManager.token().getLineNumber());
		} else {
			// Do not report the (possibly spurious) error,
			// but rather attempt to recover by forcing a match.
			while (!isMatch(pToken) && !isMatch(Token.EOF)) {
				mTokenManager.next();
			}
			if (isMatch(pToken)) {
				isRecovered = true;
				mTokenManager.next();
			}
		}
	}

	/**
	 * Have expected toekn.
	 *
	 * @param pToken the token
	 * @return true, if successful
	 */
	private boolean haveExpectedToekn(Token pToken) {
		if (isMatch(pToken)) {
			mTokenManager.next();
			return true;
			
		} else {
			return false;
		}
	}

	/**
	 * Checks if is match.
	 *
	 * @param pToken the token
	 * @return true, if is match
	 */
	private boolean isMatch(Token pToken) {
		return pToken.getValue().equalsIgnoreCase(mTokenManager.token().getWord().getValue());
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
