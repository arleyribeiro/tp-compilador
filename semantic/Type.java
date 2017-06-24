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
//type           -> integer | string
private void type()  throws IOException {
    switch (token.tag) {
        case Tag.INTEGER:
            eat(Tag.INTEGER);
            break;

        case Tag.STRING:
            eat(Tag.STRING);
            break;

        default:
            error(token.toString());
    }
}
*/
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
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Tipo esperado não encontrado");
                error(token.toString());
        }
	}
}