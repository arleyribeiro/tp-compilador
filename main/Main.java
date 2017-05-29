package main;

import java.io.*;
import java.util.*;
import lexer.*;


public class Main {
	public static void main(String[] args) {
		String filename;

		if (args.length > 0 )
			filename = args[0];
		else
			return;

		Lexer lex = new Lexer(filename);
		LexerHelper lexHelper = new LexerHelper();

		try {
			while(true) {
				Token token = lex.scan();
				Parser parser = new Parser(token);
				parser.analysis();
				lexHelper.print(token);
			}
		} catch (IOException e) {
			System.out.println("\nDone.");
		}
	}
}
