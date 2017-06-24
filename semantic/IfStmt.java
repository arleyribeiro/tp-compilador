package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IfStmt extends Parser {

    Expression expression;
    StmtList stmtList;
    IfStmtExt ifStmtExt;

	public IfStmt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
        
            case Tag.IF:
                {
                    try {
                        eat(Tag.IF);
                    } catch (IOException ex) {
                        Logger.getLogger(IfStmtExt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   
                
                {
                    try {
                        eat('(');
                    } catch (IOException ex) {
                        Logger.getLogger(IfStmtExt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  

                expression = new Expression(this);
                expression.analysis();
                
                {
                    try {
                        eat(')');
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

                ifStmtExt = new IfStmtExt(this);
                ifStmtExt.analysis();
                
                break;
                
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Comando 'IF' não encontrado.");
                error(token.toString());
        }
	}
}