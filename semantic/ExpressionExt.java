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
    //expression-ext -> LAMBDA | relop simple-expr
    //expression-ext -> LAMBDA | relop expression
     private void expressionExt() throws IOException {
        switch (token.tag) {
          //  case '=': 
            case '<': 
            case '>': 
            case Tag.EQ:
            case Tag.NE:
            case Tag.LE:
            case Tag.GE:
                relop();
                expression();
        }
    }
*/
public class ExpressionExt extends Parser {

    Relop relop;
    Expression expression;

	public ExpressionExt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            case '<': 
            case '>': 
            case Tag.EQ:
            case Tag.NE:
            case Tag.LE:
            case Tag.GE:
                relop = new Relop(this);
                relop.analysis();
                
                expression = new Expression(this);
                expression.analysis();
                
                if ((!expression.type.equals("integer")) && (!expression.type.equals("literal"))) {
                    
                    System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Operador incompatível com o type do operando.");
                    error(token.toString());
                }
                
                this.type = expression.type;
        }
	}
}