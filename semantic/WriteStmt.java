package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteStmt extends Parser {

    Writable writable;

	public WriteStmt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            case Tag.WRITE:
                {
                    try {
                        eat(Tag.WRITE);
                    } catch (IOException ex) {
                        Logger.getLogger(WriteStmt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                {
                    try {
                        eat('(');
                    } catch (IOException ex) {
                        Logger.getLogger(WriteStmt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                writable = new Writable(this);
                writable.analysis();
                
                {
                    try {
                        eat(')');
                    } catch (IOException ex) {
                        Logger.getLogger(WriteStmt.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Comando  WRITE encontrado.");
                error(token.toString());
        }
	}
}