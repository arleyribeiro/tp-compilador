package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactorA extends Parser {

	Factor factor;

	public FactorA(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        
    	switch (token.tag) {
                		
            case '-': {
		            try {
		                eat('-');
		            } catch (IOException ex) {
		                Logger.getLogger(FactorA.class.getName()).log(Level.SEVERE, null, ex);
		            }
    		}
	            factor = new Factor(this);
	            factor.analysis();
	            if ((!factor.type.equals("integer"))&&(!factor.type.equals("literal"))) {
	                System.out.println("Erro Semântico na linha " + Lexer.line + ":\n" + "Operador númerico nao foi encontrado.");
	                error(token.toString());
	            }
	            this.type = factor.type;
	            break;

	        case Tag.NOT: {
		        try {
		            eat( Tag.NOT);
		        } catch (IOException ex) {
		            Logger.getLogger(FactorA.class.getName()).log(Level.SEVERE, null, ex);
		        }
		    }
	            factor = new Factor(this);
	            factor.analysis();
	            if (!factor.type.equals("bool")) {
	                System.out.println("Erro Semântico na linha " + Lexer.line + ":\n" + "Operador booleano nao foi encontrado.");
	                error(token.toString());
	            }
	            this.type = factor.type;
	            break;
        

	        case '(':
	        case '"':
	        case Tag.ID: 
	        case Tag.NUM:
	        case Tag.LITERAL:
                factor = new Factor(this);
                factor.analysis();
                this.type = factor.type;
                break;                

            default:
                System.out.println("Erro sintatico na linha " + Lexer.line + ":\n" + "A expressao nao foi encontrada.");
                error(token.toString());
        }
    }
}