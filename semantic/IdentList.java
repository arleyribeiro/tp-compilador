package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdentList extends Parser {

	Identifier identifier;
    IdentListExt identListExt;

	public IdentList(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		switch (token.tag) {
            
    		case Tag.ID:
                this.type = head.type;
                
                identifier = new Identifier(this);
                identifier.decl = true;
                identifier.analysis();
                
                identListExt = new IdentListExt(this);
                identListExt.analysis();
                
                break;
            
    		default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "A Lista de identificadores não foi encontrada.");
                error(token.toString());
        }
	}
}