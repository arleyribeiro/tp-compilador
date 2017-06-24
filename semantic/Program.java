package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("Analise semântica concluída com sucesso!");
	}
}