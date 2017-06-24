package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

import parser.*;

import Util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mulop extends Parser {

    public Mulop(Parser head) {
        super(head);
    }

    @Override
    public void analysis () {
        switch(token.tag) {
        
            case '*': {
                
                try {
                    eat('*');
                } catch (IOException ex) {
                    Logger.getLogger(Mulop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.type = head.type;
            break;
            
            case '/': {
                
                try {
                    eat('/');
                } catch (IOException ex) {
                    Logger.getLogger(Mulop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.type = head.type;
            break;
            
            case Tag.AND: {
                
                try {
                    eat(Tag.AND);
                } catch (IOException ex) {
                    Logger.getLogger(Mulop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.type = "bool";
            break;
            
            default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n"+ "Operador aritmético esperado, porém nao encontrado.");
                error(token.toString());
        }
    }
}

/*
//mulop          -> * | / | and
private void mulop() throws IOException {
    switch (token.tag) {
        case '*':
            eat('*');
            break;

        case '/':
            eat('/');
            break;

        case Tag.AND:
            eat(Tag.AND);
            break;

        default:
            error(token.toString());
    }
}

*/