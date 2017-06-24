package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdentListExt extends Parser {

	Identifier identifier;
    IdentListExt identListExt;

	public IdentListExt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		if (token.tag == ',') {
            
    		this.type = head.type;
            
    		try {
                eat(',');
            } catch (IOException ex) {
                Logger.getLogger(IdentListExt.class.getName()).log(Level.SEVERE, null, ex);
            }
    		
            identifier = new Identifier(this);
            identifier.decl = true;
            identifier.analysis();
            
            identListExt = new IdentListExt(this);
            identListExt.analysis();
            
        }
	}
}