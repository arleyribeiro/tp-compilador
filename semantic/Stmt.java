package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stmt extends Parser {

    AssignStmt assignStmt;
    IfStmt ifStmt;
    DoStmt doStmt;
    ReadStmt readStmt;
    WriteStmt writeStmt;

	public Stmt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            
            case Tag.ID:
                assignStmt = new AssignStmt(this);
                assignStmt.analysis();
                break;
                
            case Tag.IF:
                ifStmt = new IfStmt(this);
                ifStmt.analysis();
                break;
            
            case Tag.DO:
                doStmt = new DoStmt(this);
                doStmt.analysis();
                break;
     
            case Tag.READ:
                readStmt = new ReadStmt(this);
                readStmt.analysis();
                break;
            
            case Tag.WRITE:
                writeStmt = new WriteStmt(this);
                writeStmt.analysis();
                break;
            
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Comando encontrado.");
                error(token.toString());
                
        }
	}
}