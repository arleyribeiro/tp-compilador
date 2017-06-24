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
//term-ext       -> mulop factor-a term-ext | lambda
private void termExt() throws IOException {
    switch (token.tag) {
        case '*': 
        case '/': 
        case Tag.AND:
            mulop();
            factorA();
            termExt();
            break;
    }
}
*/
public class TermExt extends Parser {

    Mulop mulop;
    FactorA factorA;
    TermExt termExt;

	public TermExt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            case '*': 
            case '/': 
            case Tag.AND:
               mulop = new Mulop(this);
                mulop.analysis();
                this.type = mulop.type;
                
                factorA = new FactorA(this);
                factorA.analysis();
                
               if (!Util.isNumeric(mulop.type) || !Util.isNumeric(factorA.type)) {
                    
                    if (!mulop.type.equals(factorA.type)) {
                    
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Operador incompatível com o type do operando.");
                        error(token.toString());
                    }
                }
                
                termExt = new TermExt(this);
                termExt.analysis();
                
                if (!termExt.type.equals("void")) {
                    
                    if (!mulop.type.equals(termExt.type)) {
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Operador incompaível com o type do operando.");
                        error(token.toString());
                    }
                    
                }
                
                if (Util.isNumeric(mulop.type)) {
                    this.type = Util.getNumericType(factorA.type, termExt.type);
                } else {
                    this.type = mulop.type;
                } 
                break;
        }
	}
}