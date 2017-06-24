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
//program        -> init decl-list stmt-list stop
public void program()  throws IOException {
    eat(Tag.INIT);
    declList();
    stmtList();
    eat(Tag.STOP);
    System.out.println("Programa correto!\n");
}
*/
public class Program extends Parser {

	Identifier identifier;
    DeclList declList;
    StmtList stmtList;

	public Program(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {

        {
            try {
                eat(Tag.INIT);
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		if (token.tag == Tag.ID) {         
 
                this.type = "var";
                declList = new DeclList(this);
                declList.analysis();
             
               
                stmtList = new StmtList(this);
                stmtList.analysis();
            
	                        
           // default:
          //      System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Declaração de inicialização do programa esperada, porém não encontrada.");
           //     error(token.toString());            
        }else {     	

	            stmtList = new StmtList(this);
	            stmtList.analysis();

        }

        {
            try {
                eat(Tag.STOP);
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}