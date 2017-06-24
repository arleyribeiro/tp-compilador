package semantic;

import java.io.*;
import lexer.*;
import symbols.*;
import main.LexerHelper;
import Util.Util;
import parser.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/*identifier -> id
private void identifier() throws IOException {

	switch (token.tag) {

		case Tag.ID:

			eat(Tag.ID);
            break;

        default:
            erro();

    }
}

 */
public class Identifier extends Parser {

	public Identifier(Parser head) {
		super(head);
	}
    public static LexerHelper lexer = new LexerHelper();

	@Override
	public void analysis () {
		switch(token.tag) {
			case Tag.ID:

				//lexer.print(token);
				/*trocar de token para word*/
				Word  tok = symbolsTable.get(token.lexeme);
				
				if (isDecl) {
					System.out.println(isDecl);

					this.type = head.type;
					
					if (tok != null) {
						
						tok = new Word(token.lexeme, Tag.ID);
						tok.type = this.type;
						symbolsTable.put(tok);
						idList.add(tok);
						
					}
					
				} else { 
					if (!tok.decl) {
				
						System.out.println("Erro semântico na linha " + Lexer.line + ":\n" + "Utilização de identificador não definido '" + token.lexeme + "'.");
            			error(tok.toString());
					}
					
					this.type = tok.type;
					
				} 
				
				{
					try {
						eat(Tag.ID);
					} catch (IOException ex) {
						Logger.getLogger(Identifier.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				break;	
				
			default:
				System.out.println("Erro sintático na linha " + Lexer.line + ":\n" + "Identificador esperado, porém não encontrada.");
				//error(tok.toString());

		}
	}	
}