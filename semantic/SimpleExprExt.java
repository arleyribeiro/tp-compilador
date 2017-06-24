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
    //simple-expr-ext-> LAMBDA | addop term simple-expr-ext
    private void simpleExprExt() throws IOException {
        switch (token.tag) {
            case '+': 
            case '-': 
            case Tag.OR:
                addop();
                term();
                simpleExprExt();
        }
    }
*/
public class SimpleExprExt extends Parser {
   
    Addop addop;
    Term term;
    SimpleExprExt simpleExprExt;

	public SimpleExprExt(Parser head) {
		super(head);
        this.type = head.type;
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            case '+': 
            case '-': 
            case Tag.OR:
                addop = new Addop(this);
                addop.analysis();
                this.type = addop.type;
                term = new Term(this);
                term.analysis();
                
                if (!Util.isNumeric(addop.type) || !Util.isNumeric(term.type)) {
                   
                    if (!addop.type.equals(term.type)) {
                    
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n"  + "Operador incompatível com o type do operando.");
                        error(token.toString());
                        
                    }
                }
                
                this.type = Util.getNumericType(addop.type, term.type);
                simpleExprExt = new SimpleExprExt(this);
                simpleExprExt.analysis();
                
                if (!simpleExprExt.type.equals("void")) {
                    
                    if (!Util.isNumeric(addop.type) || !Util.isNumeric(simpleExprExt.type)) {
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Operador incompatível com o type do operando.");
                    
                        error(token.toString());
                    }
                    
                    
                }
                
                if (Util.isNumeric(addop.type)) {
                    this.type = Util.getNumericType(term.type, simpleExprExt.type);
                } else {
                    this.type = addop.type;
                } 
        }
	}
}