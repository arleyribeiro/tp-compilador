package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import Util.Util;

import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Type extends Parser {

	public Type(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
        switch (token.tag) {
            
            case Tag.INTEGER:
                {
                    try {
                        eat(Tag.INTEGER);
                    } catch (IOException ex) {
                        Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.type = "integer";
                
                break;
                
            case Tag.STRING:
                {
                    try {
                        eat(Tag.STRING);
                    } catch (IOException ex) {
                        Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.type = "literal";
                break;
                
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Tipo nao encontrado");
                error(token.toString());
        }
	}
}