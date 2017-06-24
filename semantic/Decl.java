package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;
import parser.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
//decl           -> ident-list is type
private void decl() throws IOException {
    identList();
    eat(Tag.IS);
    type();
}
*/
public class Decl extends Parser {

	Type type;
    IdentList identList;

	public Decl(Parser head) {
		super(head);
	}

	public void setType(List<Token> idList, Type type){
		
		for (Token token : idList){
			token.type = type.type;
			token.decl = true;
		}		
	}

	@Override
	public void analysis () {
		switch (token.tag) {
            
    		case Tag.ID:
    			isDecl = true;
    			identList = new IdentList(this);
                identList.analysis();
                
                {
		            try {
		                eat(Tag.IS);
		            } catch (IOException ex) {
		                Logger.getLogger(Decl.class.getName()).log(Level.SEVERE, null, ex);
		            }
		        }
                
                type = new Type(this);
                type.analysis();
                
                setType(idList, type);
                idList.clear();
                isDecl = false;
                break;
                
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Declaração de variaveis esperada, porém não encontrada.");
                error(token.toString());
        }
	}
}