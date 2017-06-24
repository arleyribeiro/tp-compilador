package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoSuffix extends Parser {

    Expression expression;
    StmtList stmtList;

	public DoSuffix(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        {
            try {
                eat(Tag.WHILE);
            } catch (IOException ex) {
                Logger.getLogger(DoStmt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        {
            try {
                eat('(');
            } catch (IOException ex) {
                Logger.getLogger(DoStmt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        expression = new Expression(this);
        expression.analysis();

        {
            try {
                eat(')');
            } catch (IOException ex) {
                Logger.getLogger(DoStmt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}