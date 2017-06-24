package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Term extends Parser {

	FactorA factorA;
	TermExt termExt;

	public Term(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		factorA = new FactorA(this);
		factorA.analysis();
		this.type = factorA.type;
		termExt = new TermExt(this);
		termExt.analysis();
		
		if (!termExt.type.equals("void")) {
		    
			if (!Util.isNumeric(factorA.type) || !Util.isNumeric(termExt.type)) {
		        
				if (!factorA.type.equals(termExt.type)) {
		        
		            System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "O operador e incompativel com o operando.");
		            error(token.toString());
		        }
		    }
		}
		
		if (Util.isNumeric(termExt.type) && Util.isNumeric(factorA.type)) {
		    this.type = Util.getNumericType(factorA.type, termExt.type);
		} else {
		    this.type = termExt.type;
		} 
	}
}