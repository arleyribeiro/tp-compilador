package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Constant extends Parser {

	public Constant(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		switch (token.tag) {
		    case Tag.NUM:
		    	try {
		    		eat(Tag.NUM);
		    	}catch (IOException ex) {
	                Logger.getLogger(Constant.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            this.type = "integer";
		        break;

		    case Tag.LITERAL:
		    	try {
		        	eat(Tag.LITERAL);
		    	}catch (IOException ex) {
	                Logger.getLogger(Constant.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            this.type = "literal";
		        break;

		    default:
		   		System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "O tipo constant nao foi encontrada.");
		        error(token.toString());
		}
	}
}