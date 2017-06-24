package main;

import java.io.*;
import lexer.*;
import parser.*;
import semantic.*;
import symbols.*;
import main.LexerHelper;

import Util.Util;

public class Main {
	public  static void main(String[] args) {
		String filename;

		if (args.length > 0 )
			filename = args[0];
		else
			return;
		Lexer lex = new Lexer(filename);
		
		LexerHelper lexHelper = new LexerHelper();
	
		/*// while(true) {
		// 	Token token = lex.scan();
		// 	lexHelper.print(token);

		// 	if(token.tag == Tag.EOF)
		// 		break;
		// }
		
		try {
			Parser parser = new Parser(lex);
			parser.analysis();
		} catch(IOException e){
			e.printStackTrace();
		}
		*/

			Parser.lex =  lex;
			Parser.token = Parser.lex.scan();
			Parser.symbolsTable = SymbolsTable.getSymbolsTable();
	
			Program program = new Program(null);
			program.analysis();

	}
}
