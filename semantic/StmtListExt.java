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
//stmt-list-ext  -> LAMBDA | stmt ; stmt-list-ext
private void stmtListExt() throws IOException {
    switch (token.tag) {
        case Tag.ID:
        case Tag.DO:
        case Tag.IF:
        case Tag.READ:
        case Tag.WRITE:
            stmt();
            eat(';');
            stmtListExt();
            break;
    }
}
*/
public class StmtListExt extends Parser {

    Stmt stmt;
    StmtListExt stmtListExt;

	public StmtListExt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
        case Tag.ID:
        case Tag.DO:
        case Tag.IF:
        case Tag.READ:
        case Tag.WRITE:
            stmt = new Stmt(this);
            stmt.analysis();
            
            try {
                eat(';');
            } catch (IOException ex) {
                Logger.getLogger(StmtListExt.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            stmtListExt = new StmtListExt(this);
            stmtListExt.analysis();
            break;
    }
	}
}