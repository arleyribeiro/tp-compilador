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
//if-stmt-ext    -> LAMBDA | else begin stmt-list end
private void ifStmtExt() throws IOException {
    switch (token.tag) {
        case Tag.ELSE:
            eat(Tag.ELSE);
            eat(Tag.BEGIN);
            stmtList();
            eat(Tag.END);
            break;
    }
}
*/
public class IfStmtExt extends Parser {

    StmtList stmtList;
    
	public IfStmtExt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            case Tag.ELSE:
                {
                    try {
                        eat(Tag.ELSE);
                    } catch (IOException ex) {
                        Logger.getLogger(IfStmtExt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                {
                    try {
                        eat(Tag.BEGIN);
                    } catch (IOException ex) {
                        Logger.getLogger(IfStmtExt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
                
                stmtList = new StmtList(this);
                stmtList.analysis();
                
                {
                    try {
                        eat(Tag.END);
                    } catch (IOException ex) {
                        Logger.getLogger(IfStmtExt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

             default:
                System.out.println("Erro sintático na linha " + Lexer.line+ ":\n" + "Comando 'ELSE' esperado, porém não encontrado.");
                error(token.toString());
        }
	}
}