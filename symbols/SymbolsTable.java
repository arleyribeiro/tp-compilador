package symbols;

import java.util.*;
import lexer.*;

public class SymbolsTable{

	private static SymbolsTable table;
	private HashMap<String, Word> hashMap;

	public SymbolsTable() {
		hashMap = new HashMap<String, Word>();
	}

	public static SymbolsTable getSymbolsTable() {
		if (table == null) {
			table = new SymbolsTable();
			return table;
		}
		return table;
	}
	//(String s, int tag)
	public void put(String lexeme, Word token) {
		hashMap.put(token.lexeme, token);
	}

	public Word get(String lexeme) {
		Word token = hashMap.get(lexeme);
		return token;
	}
}
