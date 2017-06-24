package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Addop extends Parser {

	public Addop(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
	    
        	case '+': {
        		try {
        			eat('+');
        		} catch (IOException ex) {
        			Logger.getLogger(Addop.class.getName()).log(Level.SEVERE, null, ex);
        		}
        	}
            
            this.type = head.type;
            break;
	    
        	case '-': {
	            try {
	                eat('-');
	            } catch (IOException ex) {
	                Logger.getLogger(Addop.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
            this.type = head.type;
            break;
            
		    case Tag.OR: {
	            try {
	                eat(Tag.OR);
	            } catch (IOException ex) {
	                Logger.getLogger(Addop.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
            this.type = "bool";
            break;
		    
		    default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Operador aritmético encontrado.");
                error(token.toString());
		}
	}
}