package parser;

import java.io.*;
import lexer.*;
import symbols.*;

public class Parser {

	private Lexer lex;
	private Token token;


	public Parser(Lexer lexer) throws IOException {
		lex = lexer;
		move();
	}

	void move() throws IOException {
		token = lex.scan();
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

	//program ::= init [decl-list] stmt-list stop 
	public void program()  throws IOException {
        this.eat(Tag.INIT);
        this.declList();
        this.stmtList();
        this.eat(Tag.STOP);
	}

	//decl-list ::= decl “;” decl-list1
	private void declList() throws IOException {
		switch(token.tag) {
			case Tag.ID:
				this.decl();
				this.eat(';');
				this.declList1();
				break;

			default:
				error("syntax error");
		}
	}

	//decl-list1 ::= decl ;  decl-list1 | lambda
	private void declList1() throws IOException {
		if (token.tag == Tag.ID) {
			decl();
			eat(';');
			declList1();
		}
	}

	//decl ::= ident-list is type
	private void decl() throws IOException {
		switch (token.tag) {
			case Tag.ID:
			identList();
			eat(Tag.IS);
			type();
			break;

			default:
				error("syntax error");
		}
	}
	
	//ident-list ::= identifier ident-list1
	private void identList() throws IOException {
		switch(token.tag) {
			
			case Tag.ID:
				identifier();
				identList1();
				break;

			default:
				error("syntax error");
		}
	}

	//ident-list1 ::= “,” identifier ident-list1 | lambda
	private void identList1() throws IOException {
		if(token.tag == ',') {
			eat(',');
			identifier();
			identList1();
		}
	}

	//type ::= integer | string
	private void type()  throws IOException {
		switch (token.tag) {
			case Tag.INTEGER:
				eat(Tag.INTEGER);
				break;

			case Tag.STRING:
				eat(Tag.STRING);
				break;

			default:
				error("syntax error");
		}		
	}

	//stmt-list ::= stmt “;” stmt-list1
	private void stmtList() throws IOException {
		switch (token.tag) {
			case Tag.ID:
			case Tag.IF:
			case Tag.DO:
			case Tag.READ:
			case Tag.WRITE:
				stmt();
				eat(';');
				stmtList1();
				break;

			default:
				error("syntax error");
		}
	}

	//stmt-list1 :: stmt “;” stmt-list1 | lambda
	private void stmtList1() throws IOException {
		if (Tag.ID == token.tag || Tag.IF == token.tag || Tag.DO == token.tag || Tag.READ == token.tag || Tag.WRITE == token.tag){
			stmt();
			eat(';');
			stmtList1();
		}
	}

	//stmt ::= assign-stmt | if-stmt | do-stmt | read-stmt | write-stmt
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
                error("syntax error");
		}
	}

	//assign-stmt ::= identifier “:=” simple_expr
	private void assignStmt() throws IOException {

        switch (token.tag) {

            case Tag.ID:
                identifier();
                eat(Tag.ASSIGN);
                simpleExpr();
                break;

            default:
                error("syntax error");
        }
    }

    //if-stmt ::= if “(” condition “)” begin stmt-list end else1
    private void ifStmt() throws IOException {

        switch (token.tag) {

            case Tag.IF:
                eat(Tag.IF);
                eat('(');
                expression();
                eat(')');
                eat(Tag.BEGIN);
                stmtList();
                eat(Tag.END);
                elseStmt();
                break;

            default:
                error("syntax error");
        }
    }

	//else1 ::= END | ELSE stmt-list END
    private void elseStmt() throws IOException {

        switch (token.tag) {

            case Tag.END:
                eat(Tag.END);
                break;

            case Tag.ELSE:
                eat(Tag.ELSE);                
                eat('(');
                expression();
                eat(')');
                eat(Tag.BEGIN);
                stmtList();
                eat(Tag.END);
                break;

            default:
                error("syntax error");
        }
    }

    //condition ::= expression
    private void condition() throws IOException {
    	switch(token.tag) {
    		case Tag.ID:
    		case Tag.NUM:
    		case Tag.LITERAL:
    			expression();
    			break;
    	}
    }

    //do-stmt ::= do stmt-list do-suffix
    private void doStmt() throws IOException {

        switch (token.tag) {

            case Tag.DO:
                eat(Tag.DO);
                stmtList();
                doSuffix();
                break;

            default:
                error("syntax error");
        }
    }

    //do-suffix ::= while “(“ condition “)”
    private void doSuffix() throws IOException {

        if(token.tag == Tag.WHILE) {
        	eat('(');
        	condition();
        	eat(')');
        }
    }

    //read-stmt :: read “(” identifier “)”
    private void readStmt() throws IOException {

        switch (token.tag) {
            case Tag.READ:
                eat(Tag.READ);
                eat('(');
                identifier();
                eat(')');
                break;

            default:
                error("syntax error");
        }
    }

    //write-stmt ::= write “( “ writable “)”
	private void writeStmt() throws IOException {

        switch (token.tag) {
            case Tag.WRITE:
                eat(Tag.WRITE);
                eat('(');
                writable();
                eat(')');
                break;

            default:
                error("syntax error");
        }
	}

	//writable ::= simple-expr
	private void writable() throws IOException {

        switch (token.tag) {
            case Tag.ID:
            case Tag.NUM:
            case Tag.LITERAL:
            case Tag.NOT:
            case '(':
            case '0':
            case '-':
            case '"':
                simpleExpr();
                break;

            default:
                error("syntax error");
        }
    }

    //expression ::= simple-expr expression1
	private void expression() throws IOException {

        switch (token.tag) {

            case Tag.ID:
            case Tag.NUM:
            case Tag.LITERAL:       
            case '"':
                simpleExpr();
                expression1();
                break;

            default:
                error("syntax error");
        }
    }

    //expression1 :: relop expression1 | lambda
     private void expression1() throws IOException {

        if (token.tag == Tag.EQ || 
        	token.tag == Tag.LE || 
        	token.tag == Tag.GE || 
        	token.tag == Tag.LE || 
        	token.tag == '>' || 
        	token.tag == '<') {
            relop();
            expression();
        }
    }

    //simple-expr :== term simple-expr1
    private void simpleExpr() throws IOException {

        switch (token.tag) {
            case Tag.ID:
            case Tag.NUM:
            case Tag.LITERAL:
            case Tag.NOT:
            case '(':
            case '0':
            case '-':
            case '"':
                term();
                simpleExpr1();
                break;

            default:
                error("syntax error");
        }
    }

    //simple-expr1 ::= addop term simple-expr1 | LAMBDA
    private void simpleExpr1() throws IOException {

        if (token.tag == Tag.OR ||
         	token.tag == '+' || 
         	token.tag == '-') {

            addop();
            term();
            simpleExpr1();
        }
    }

    //term ::= factor-a term1
    private void term() throws IOException {

        switch (token.tag) {

            case Tag.ID:
            case Tag.NUM:
            case Tag.LITERAL:
            case Tag.NOT:
            case '(':
            case '0':
            case '-':
            case '"':
                factorA();
                term1();
                break;

            default:
                error("syntax error");
        }
    }

    //term1 ::= mulop factor-a term1 | lambda
    private void term1() throws IOException {
        if (token.tag == Tag.AND || 
        	token.tag == '*' || 
        	token.tag == '/') {

            mulop();
            factorA();
            term1();
        }
    }

	//factor-a ::= factor | not factor | - factor
    private void factorA() throws IOException {

        switch (token.tag) {

            case Tag.ID:
            case Tag.NUM:
            case Tag.LITERAL:
            case '(':
                factor();
                break;

            case Tag.NOT:
                eat(Tag.NOT);
                factor();
                break;

            case '-':
                eat('-');
                factor();
                break;

            default:
                error("syntax error");
        }
    }

	//factor ::= identifier | constant | “(” expression “)”
    private void factor() throws IOException {

        switch (token.tag) {

            case Tag.ID:
                identifier();
                break;

            case Tag.NUM:
            case Tag.LITERAL:
            case '"':
                constant();
                break;

            case '(':
                eat('(');
                expression();
                eat(')');
                break;

            default:
                error("syntax error");
        }
    }

	//relop ::= “=” | ”>” | ”>=” | ”<” | ”<=” | ”<>”
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
                error("syntax error");
        }
    }

    //addop ::= “+” | ”-” | or
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
                error("syntax error");
        }
    }

    //mulop ::= “*” | “/” | and
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
                error("syntax error");
        }
    }

    //constant ::= integer_const | literal
    private void constant() throws IOException {

        switch (token.tag) {

            case Tag.NUM:
            case '0':
                integerConst();
                break;

            case Tag.LITERAL:

                eat(Tag.LITERAL);
                break;

            default:
                error("syntax error");
        }
    }

    //integer_const ::= nozero{digit} | “0”
    private void integerConst() throws IOException {

        switch (token.tag) {

            case Tag.NUM:
                eat(Tag.NUM);
                break;

            case '0':
                eat('0');
                break;

            default:
                error("syntax error");
        }
    }

    //literal ::= “ ” ” {caractere} “ ” ”
    private void literal() throws IOException {

        switch (token.tag) {

            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;

            default:
                error("syntax error");
        }
    }

    //identifier ::=(letter){letter | digit | “ _ “ }
    private void identifier() throws IOException {
	    switch (token.tag) {

            case Tag.ID:
                eat(Tag.ID);
                break;

            default:
            	error("syntax error");
        }
    }

    //letter ::= [A-Za-z]
    private void letter() throws IOException {
	    switch (token.tag) {

            case Tag.LITERAL:
                eat(Tag.LITERAL);
                break;

            default:
            	error("syntax error");
        }
    }

}