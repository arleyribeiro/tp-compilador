package parser;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;
import semantic.*;
import java.util.*;

public abstract class Parser {
    
    public Parser head;
    public String type;
    public boolean decl;
    public static Lexer lex;
    public static Token token;
    public static SymbolsTable symbolsTable = SymbolsTable.getSymbolsTable();
    public static List<Token> idList = new ArrayList<Token>();
    public static boolean isDecl = false;
    public static LexerHelper lexer = new LexerHelper();

    protected Parser(Parser head){
        this.head = head;
        this.type = "void";
        this.decl = false;
    }
    
    
    protected void error(String token) {
        System.exit(0);
    }
    
    protected void eat(int tag) throws IOException{
        
    	if (token.tag == tag) {
            
    		//System.out.println("eat " + token);
            
            lexer.print(token);
            token = lex.scan();
            
        } else {
            
        	System.out.println("Erro Sintático na linha " + Lexer.line + ":\n" + "Token esperado: \"" + token.getLexeme() + "\"\n" + "Token encontrado: \"" + token.lexeme + "\"");
            error(token.toString());
            
        }
    	
    }
    
    public abstract void analysis();
   
}
    
   
