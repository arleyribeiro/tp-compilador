package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoStmt extends Parser {

    Expression expression;
    StmtList stmtList;
    DoSuffix doSuffix;

	public DoStmt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        {
            try {
                eat(Tag.DO);
            } catch (IOException ex) {
                Logger.getLogger(DoStmt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        stmtList = new StmtList(this);
        stmtList.analysis();

        doSuffix = new DoSuffix(this);
        doSuffix.analysis();
	}
}