package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleExpr extends Parser {

	Term term;
    SimpleExprExt simpleExprExt;

	public SimpleExpr(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		term = new Term(this);
        term.analysis();
        this.type = term.type;
        simpleExprExt = new SimpleExprExt(this);
        simpleExprExt.analysis();
        
        if (!simpleExprExt.type.equals("void")) {
            
        	if (!Util.isNumeric(term.type) || !Util.isNumeric(simpleExprExt.type)) {
                
        		if (!term.type.equals(simpleExprExt.type)) {
                    System.out.println("Erro semantico na linha " + Lexer.line + ":\n" + "Os operandos e operadores sao incompatíveis.");
                    error(token.toString());
                }
            }
        }
        
        if (Util.isNumeric(term.type) && Util.isNumeric(simpleExprExt.type)) {
            this.type = Util.getNumericType(term.type, simpleExprExt.type);
        } else {
            this.type = simpleExprExt.type;
        }
        
	}
}