package symbols;

import java.util.*;
import lexer.*;


public class SymbolsTable {

	private static SymbolsTable table;
	private HashMap<String, Token> hashMap;

	public SymbolsTable() {
		hashMap = new HashMap<String, Token>();
	}

	public static SymbolsTable getSymbolsTable() {
		if (table == null) {
			table = new SymbolsTable();
			return table;
		}
		return table;
	}

	public void put(Token token) {
		hashMap.put(token.lexeme, token);
	}

	public token get(String lexeme) {
		Token token = hashMap.get(lexeme);
		return token;
	}
}
