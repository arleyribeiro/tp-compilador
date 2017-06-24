package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
//constant       -> num | literal
private void constant() throws IOException {
    switch (token.tag) {
        case Tag.NUM:
            eat(Tag.NUM);
            break;
        case Tag.LITERAL:
            eat(Tag.LITERAL);
            break;

        default:
            error(token.toString());
    }
}
*/

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
		   		System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Constante númerica esperada porém não encontrada.");
		        error(token.toString());
		}
	}
}