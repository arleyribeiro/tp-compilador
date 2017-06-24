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
    //writable       -> simple-expr
    private void writable() throws IOException {
        simpleExpr();
    }
*/
public class Writable extends Parser {
   
    SimpleExpr simpleExpr;

	public Writable(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		simpleExpr = new SimpleExpr(this);
        simpleExpr.analysis();
	}
}