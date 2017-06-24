package lexer;
import java.io.*;
import java.util.*;
import symbols.*;
import parser.*;

public class Lexer {
   public static int line = 1;
   private char ch = ' ';
   private FileReader file;
   private int EOF = 65535;
   
   public SymbolsTable words = SymbolsTable.getSymbolsTable();

   void reserve(Token w) {
      words.put(w);
   }

   public Lexer(String filename) {
      try {
         file = new FileReader (filename);
      }
      catch (FileNotFoundException e){
         System.out.println("File not found.");
      }

      reserve(new Token("init",    Tag.INIT));
      reserve(new Token("stop",    Tag.STOP));
      reserve(new Token("is",      Tag.IS));
      reserve(new Token("integer", Tag.INTEGER));
      reserve(new Token("string",  Tag.STRING));
      reserve(new Token("if",      Tag.IF));
      reserve(new Token("else",    Tag.ELSE));
      reserve(new Token("begin",   Tag.BEGIN));
      reserve(new Token("end",     Tag.END));
      reserve(new Token("do",      Tag.DO));
      reserve(new Token("while",   Tag.WHILE));
      reserve(new Token("read",    Tag.READ));
      reserve(new Token("write",   Tag.WRITE));
      reserve(new Token("not",     Tag.NOT));
      reserve(new Token("or",      Tag.OR));
      reserve(new Token("and",     Tag.AND));
   }

   
   private void readch() {
      if (ch != EOF)
         try {
            ch = (char)file.read();
         } catch (IOException e) {
            // da nada não
         }
   }
   
   private boolean readch(char c) {
      readch();
      
      if (ch != c)
         return false;
      
      ch = ' ';
      return true;
   }

   public Token scan() {
      for ( ; ; readch() ) {
         if (ch == ' ' || ch == '\t')
            continue;
         else if (ch == '\n')
            line = line + 1;
         else
            break;
      }

      //line comment
      if (ch == '/'){
         if (readch('/')) {
            while (ch != '\n')
               readch();

            return scan();
         }
         else {
            return new Token('/');
         }
      }

      //block comment
      if (ch == '{'){
         while (ch != '}') {
            readch();
            if (ch == '\n')
               line = line + 1;
            if (ch ==EOF)
               break;
         }
         ch = ' ';
         return scan();
      }

      switch(ch) {
         case ':':
            return readch('=') ? Token.assign : new Token(':');
         case '=':
            ch = ' ';
            return  Token.eq;
         case '>':
            return readch('=') ? Token.ge : new Token('>');
         case '<':
            readch();
            if (ch == '=') {
               ch = ' ';
               return Token.le;
            }
            if (ch == '>') {
               ch = ' ';
               return Token.ne;
            }
            return new Token('<');
      }

      if (Character.isDigit(ch)) {
         int v = 0;

         do {
            v = 10 * v + Character.digit(ch, 10);
            readch();
         } while(Character.isDigit(ch));
         
         return new Num(v);
      }

      if (Character.isLetter(ch)) {
         StringBuffer b = new StringBuffer();

         do {
            b.append(ch);
            readch();
         } while(Character.isLetterOrDigit(ch) || ch == '_');

         String s = b.toString();
         Token w = (Token)words.get(s.toLowerCase());

         if (w != null)
            return w;

         w = new Token(s, Tag.ID);
         words.put(w);
         return w;
      }

      if (ch == '“') {
         StringBuffer b = new StringBuffer();

         do {
            b.append(ch);
            readch();
         } while(ch != '”');
         b.append(ch);
         readch();

         String s = b.toString();
         Token w = (Token)words.get(s.toLowerCase());

         if (w != null)
            return w;

         w = new Token(s, Tag.LITERAL);
         words.put(w);
         return w;
      }

      Token tok = new Token(ch);
      ch = ' ';
      return tok;
   }
}