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

	public static final Token

      eq      = new Token( "=", Tag.EQ),
      ne      = new Token( "<>", Tag.NE),
      le      = new Token( "<=", Tag.LE),
      ge      = new Token( ">=", Tag.GE),
      assign  = new Token( ":=", Tag.ASSIGN);
      
}
