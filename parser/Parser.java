package parser;

import java.io.*;
import lexer.*;
import symbols.*;
import main.*;

public class Parser {

	private Lexer lex;
	private Token token;
    private LexerHelper lexer = new LexerHelper();

	public Parser(Lexer lexer) throws IOException {
		lex = lexer;
		move();
	}

	void move() throws IOException {
		token = lex.scan();
        lexer.print(token);
	}

	void eat(int tag) throws IOException {
		if (token.tag == tag)
            move();
		else
            error(token.toString());
	}

    void error(String s){
        System.out.println("Erro!!!");
        System.out.println(" -- linha " + this.lex.line);
        System.out.println(" -- token inválido: " + s);
        throw new Error();
    }

	//program        -> init decl-list stmt-list stop
	public void program()  throws IOException {
        eat(Tag.INIT);
        declList();
        stmtList();
        eat(Tag.STOP);
        System.out.println("Programa correto!\n");
	}

	//decl-list      -> LAMBDA | decl ; decl-list
	private void declList() throws IOException {
        decl();
        eat(';');
        declList();
	}

	//decl           -> ident-list is type
	private void decl() throws IOException {
        identList();
        eat(Tag.IS);
		type();
	}

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
	
	//ident-list     -> identifier ident-list-ext
	private void identList() throws IOException {
        eat(Tag.ID);
        identListExt();
	}

	//ident-list-ext -> LAMBDA | , identifier ident-list-ext
	private void identListExt() throws IOException {
		if(token.tag == ',') {
			eat(',');
			eat(Tag.ID);
			identListExt();
		}
	}

	//stmt-list      -> stmt ; stmt-list-ext
	private void stmtList() throws IOException {
		switch (token.tag) {
			case Tag.ID:
			case Tag.DO:
            case Tag.IF:
            case Tag.READ:
            case Tag.WRITE:
				stmt();
				eat(';');
				stmtListExt();
				break;

			default:
				error(token.toString());
		}
	}

	//stmt-list-ext  -> LAMBDA | stmt ; stmt-list-ext
	private void stmtListExt() throws IOException {
        switch (token.tag) {
            case Tag.ID:
            case Tag.DO:
            case Tag.IF:
            case Tag.READ:
            case Tag.WRITE:
                stmt();
                eat(';');
                stmtListExt();
                break;
        }
	}

    //stmt           -> assign-stmt | if-stmt | do-stmt | read-stmt | write-stmt
	private void stmt() throws IOException {
		switch (token.tag) {
			case Tag.ID:
				assignStmt();
				break;
			case Tag.IF:
                ifStmt();
                break;
            case Tag.DO:
                doStmt();
                break;
            case Tag.READ:
                readStmt();
                break;
            case Tag.WRITE:
                writeStmt();
                break;
            default:
                error(token.toString());
		}
	}

	//assign-stmt    -> identifier := simple_expr
	private void assignStmt() throws IOException {
        eat(Tag.ID);
        eat(Tag.ASSIGN);
        simpleExpr();
    }

    //if-stmt        -> if ( expression ) begin stmt-list end if-stmt-ext
    private void ifStmt() throws IOException {
        eat(Tag.IF);
        eat('(');
        expression();
        eat(')');
        eat(Tag.BEGIN);
        stmtList();
        eat(Tag.END);
        ifStmtExt();
    }

    //if-stmt-ext    -> LAMBDA | else begin stmt-list end
    private void ifStmtExt() throws IOException {
        switch (token.tag) {
            case Tag.ELSE:
                eat(Tag.ELSE);
                eat(Tag.BEGIN);
                stmtList();
                eat(Tag.END);
                break;
        }
    }

    //do-stmt        -> do stmt-list do-suffix
    private void doStmt() throws IOException {
        eat(Tag.DO);
        stmtList();
        doSuffix();
    }

    //do-suffix      -> while ( expression )
    private void doSuffix() throws IOException {
        eat(Tag.WHILE);
    	eat('(');
    	expression();
    	eat(')');
    }

    //read-stmt      -> read ( identifier )
    private void readStmt() throws IOException {
        eat(Tag.READ);
        eat('(');
        eat(Tag.ID);
        eat(')');
    }

    //write-stmt     -> write ( writable )
	private void writeStmt() throws IOException {
        eat(Tag.WRITE);
        eat('(');
        writable();
        eat(')');
	}

	//writable       -> simple-expr
	private void writable() throws IOException {
        simpleExpr();
    }

    //expression     -> simple-expr expression-ext
	private void expression() throws IOException {
        simpleExpr();
        expressionExt();
    }

    //expression-ext -> LAMBDA | relop simple-expr
    //expression-ext -> LAMBDA | relop expression
     private void expressionExt() throws IOException {
        switch (token.tag) {
          //  case '=': 
            case '<': 
            case '>': 
            case Tag.EQ:
            case Tag.NE:
            case Tag.LE:
            case Tag.GE:
                relop();
                expression();
        }
    }

    //simple-expr    -> term simple-expr-ext
    private void simpleExpr() throws IOException {
        term();
        simpleExprExt();
    }

    //simple-expr-ext-> LAMBDA | addop term simple-expr-ext
    private void simpleExprExt() throws IOException {
        switch (token.tag) {
            case '+': 
            case '-': 
            case Tag.OR:
                addop();
                term();
                simpleExprExt();
        }
    }

    //term           -> factor-a term-ext
    private void term() throws IOException {
        factorA();
        termExt();
    }

    //term-ext       -> mulop factor-a term-ext | lambda
    private void termExt() throws IOException {
        switch (token.tag) {
            case '*': 
            case '/': 
            case Tag.AND:
                mulop();
                factorA();
                termExt();
                break;
        }
    }

    //factor-a       -> factor | not factor | - factor
    private void factorA() throws IOException {
        switch (token.tag) {
            case '-': 
                eat('-');
                factor();
                break;
            case Tag.NOT:
                eat(Tag.NOT);
                factor();
                break;
            case '(':
            case Tag.ID: 
            case Tag.NUM:
            case Tag.LITERAL:
                factor();
                break;
            default:
                error(token.toString());
        }
    }

	//factor         -> identifier | constant | ( expression )
    private void factor() throws IOException {
        switch (token.tag) {
            case Tag.ID: 
                eat(Tag.ID);
                break;
            case Tag.NUM:
            case Tag.LITERAL:
                constant();
                break;
            case '(':
                eat('(');
                expression();
                eat(')');
                break;
            default:
                error(token.toString());
        }
    }

	//relop          -> = | > | >= | < | <= | <>
    private void relop() throws IOException {
        switch (token.tag) {
            case Tag.EQ:
                eat(Tag.EQ);
                break;

            case '>':
                eat('>');
                break;

            case Tag.GE:
                eat(Tag.GE);
                break;

            case '<':
                eat('<');
                break;

            case Tag.LE:
                eat(Tag.LE);
                break;

            case Tag.NE:
                eat(Tag.NE);
                break;

            default:
                error(token.toString());
        }
    }

    //addop          -> + | - | or
     private void addop() throws IOException {
        switch (token.tag) {
            case '+':
                eat('+');
                break;

            case '-':
                eat('-');
                break;

            case Tag.OR:
                eat(Tag.OR);
                break;

            default:
                error(token.toString());
        }
    }

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

    //constant       -> num | literal
    private void constant() throws IOException {
        switch (token.tag) {
            case Tag.NUM:
                eat(Tag.NUM);
                break;
            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;

            default:
                error(token.toString());
        }
    }
}