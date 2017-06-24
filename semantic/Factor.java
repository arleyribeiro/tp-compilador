package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factor extends Parser {

    Identifier identifier;
    Constant constant;
    Expression expression;

	public Factor(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        
        switch (token.tag) {
            case Tag.ID:
                identifier = new Identifier(this);
                identifier.analysis();
                this.type = identifier.type;
                break;
            
            case Tag.NUM:
            case Tag.LITERAL:
                constant = new Constant(this);
                constant.analysis();
                this.type = constant.type;
                break;
           
            case '(': {
                
                try {
                    eat('(');
                } catch (IOException ex) {
                    Logger.getLogger(Factor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            expression = new Expression(this);
            expression.analysis();
            this.type = expression.type;
            
            {
                try {
                    eat(')');
                } catch (IOException ex) {
                    Logger.getLogger(Factor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;                                                                                                                                
            
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Expressão não encontrada.");
                error(token.toString());
        }
    }
}