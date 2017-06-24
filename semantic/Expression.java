package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Expression extends Parser {

    SimpleExpr simpleExpr;
    ExpressionExt expressionExt;

	public Expression (Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        simpleExpr = new SimpleExpr(this);
        simpleExpr.analysis();
        
        expressionExt = new ExpressionExt(this);
        expressionExt.analysis();
        
        if (!expressionExt.type.equals("void")) {
            
            if (!simpleExpr.type.equals(expressionExt.type)) {
                System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Tipo do operador e´ incompatível com Operando. ");
                error(token.toString());
            }
            
            this.type = "bool";
        
        } else {
            this.type = simpleExpr.type;
        }
	}
}