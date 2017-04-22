package main;

import java.io.*;
import java.util.*;
import lexer.*;


public class LexerHelper {
	private Hashtable table = new Hashtable();

	public void print(Token token){
		if (token.tag == Tag.NUM)
			System.out.println("NUM: " + ((Num)token).value);
		else if (token.tag == Tag.ID)
			System.out.println("ID:  " + ((Word)token).lexeme);
		else if (token.tag < 256)
			System.out.println("     " + (char)token.tag);
		else
			System.out.println("     " + table.get(token.tag));
	}

	public LexerHelper() {
		table.put(Tag.INIT, "INIT");
		table.put(Tag.STOP, "STOP");
		table.put(Tag.IS, "IS");
		table.put(Tag.INTEGER, "INTEGER");
		table.put(Tag.STRING, "STRING");
		table.put(Tag.IF, "IF");
		table.put(Tag.ELSE, "ELSE");
		table.put(Tag.BEGIN, "BEGIN");
		table.put(Tag.END, "END");
		table.put(Tag.DO, "DO");
		table.put(Tag.WHILE, "WHILE");
		table.put(Tag.READ, "READ");
		table.put(Tag.WRITE, "WRITE");
		table.put(Tag.NOT, "NOT");
		table.put(Tag.OR, "OR");
		table.put(Tag.AND, "AND");
		table.put(Tag.ASSIGN, "ASSIGN");
		table.put(Tag.EQ, "EQ");
		table.put(Tag.NE, "NE");
		table.put(Tag.LE, "LE");
		table.put(Tag.GE, "GE");
		table.put(Tag.NUM, "NUM");
		table.put(Tag.ID, "ID");
	}
}
