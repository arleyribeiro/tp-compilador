package main;

import java.io.*;
import java.util.*;
import lexer.*;
import parser.*;


public class Main {
	public static void main(String[] args) {
		String filename;

		if (args.length > 0 )
			filename = args[0];
		else
			return;

		Lexer lex = new Lexer(filename);
		LexerHelper lexHelper = new LexerHelper();
	
		// while(true) {
		// 	Token token = lex.scan();
		// 	lexHelper.print(token);

		// 	if(token.tag == Tag.EOF)
		// 		break;
		// }
		
		try {
			Parser parser = new Parser(lex);
			parser.program();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
