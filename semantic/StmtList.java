package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StmtList extends Parser {

    Stmt stmt;
    StmtListExt stmtListExt;

	public StmtList(Parser head) {
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
                    Logger.getLogger(StmtList.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                stmtListExt = new StmtListExt(this);
                stmtListExt.analysis();
                break;

            default:
                System.out.println("Erro sintatico na linha " + Lexer.line + ":\n" + "Comando (IF/ASSIGN/DO/READ/WRITE) encontrado.");
                error(token.toString());
        }
	}
}