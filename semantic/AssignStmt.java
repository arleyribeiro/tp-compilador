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

//assign-stmt    -> identifier := simple_expr
private void assignStmt() throws IOException {
    eat(Tag.ID);
    eat(Tag.ASSIGN);
    simpleExpr();
}
*/
public class AssignStmt extends Parser {

	Identifier identifier;
    SimpleExpr simpleExpr;

	public AssignStmt(Parser head) {
		super(head);
	}

	@Override
	public void analysis () {
		switch (token.tag) {
            
    		case Tag.ID:
                
            	identifier = new Identifier(this);
                identifier.analysis();
		        
                {
		            try {
		                eat(Tag.ASSIGN);
		            } catch (IOException ex) {
		                Logger.getLogger(AssignStmt.class.getName()).log(Level.SEVERE, null, ex);
		            }
		        }
                
                simpleExpr = new SimpleExpr(this);
                simpleExpr.analysis();
                if (!Util.canAssign(identifier.type, simpleExpr.type)) {
                    
                	System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Tipos incompatíveis.");
                    error(token.toString());
                }
                
                break;
            
    		default:
                System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Atribuição esperada, porém nao encontrada.");
                error(token.toString());
                
        }
	}
}