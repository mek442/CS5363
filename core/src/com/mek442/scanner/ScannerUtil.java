package com.mek442.scanner;

public class ScannerUtil {

	public static boolean isWhitespace(char pCharacter) {
		switch (pCharacter) {
		case ' ':
		case '\t':
		case '\n': // CharReader maps all new lines to '\n'
		case '\f':
			return true;
		}
		return false;
	}

	public static boolean isStartOfIdentifier(char c) {
		return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$');
	}

	public static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	public static boolean isPartOfIdentifier(char c) {
		return (isStartOfIdentifier(c) || isDigit(c));
	}
}
