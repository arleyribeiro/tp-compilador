package main;

import java.io.*;
import lexer.*;


public class Main {

	public static void main(String[] args) {
		String filename;

		if (args.length > 0 )
			filename = args[0];
		else
			return;

		Lexer lex = new Lexer(filename);

		try {
			while(true) {
				Token token = lex.scan();
				System.out.println(token.tag);
			}
		} catch (IOException e) {
			System.out.println("Done.");
		}

	}
}
