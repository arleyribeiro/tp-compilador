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
//decl-list      -> LAMBDA | decl ; decl-list
private void declList() throws IOException {
    if (token.tag == Tag.ID) {
        decl();
        eat(';');
        declList();
    }
}
*/
public class DeclList extends Parser {

	Decl decl;
    DeclList declList;

	public DeclList(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		if (token.tag == Tag.ID) {
	        decl = new Decl(this);
            decl.analysis();
            
            try {
                eat(';');
            } catch (IOException ex) {
                Logger.getLogger(DeclList.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            declList = new DeclList(this);
            declList.analysis();
	    }
	}
}