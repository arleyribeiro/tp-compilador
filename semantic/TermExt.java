package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TermExt extends Parser {

    Mulop mulop;
    FactorA factorA;
    TermExt termExt;

	public TermExt(Parser head) {
		super(head);
        this.type = head.type;
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
                    
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Tipo do Operador incompatível com tipo do operando.");
                        error(token.toString());
                    }
                }
                
                termExt = new TermExt(this);
                termExt.analysis();
                
                if (!termExt.type.equals("void")) {
                    
                    if (!mulop.type.equals(termExt.type)) {
                        System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Tipo do Operador incompatível com tipo do operando.");
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