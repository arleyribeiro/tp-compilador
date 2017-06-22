package lexer;


public class Token {

	public final int tag;
	public String lexeme;
	public String type;
	public boolean decl;
	
	public Token(int t) {
		tag = t;
	}

	public Token(String lexeme, int tag) {
		this.tag = tag;
		this.lexeme = lexeme;
		this.type = "";
	}

	public String getLexeme() {
		return lexeme;
	}

	
	public String toString() {
		return "" + (char)tag;
	}
}
