package lexer;
import java.io.*;
import java.util.*;
import symbols.*;


public class Lexer {
   public static int line = 1;
   private char ch = ' ';
   private FileReader file;
   private int EOF = 65535;
   
   private Hashtable words = new Hashtable();

   void reserve(Word w) {
      words.put(w.lexeme, w);
   }

   public Lexer(String filename) {
      try {
         file = new FileReader (filename);
      }
      catch (FileNotFoundException e){
         System.out.println("File not found.");
      }

      reserve(new Word("init",    Tag.INIT));
      reserve(new Word("stop",    Tag.STOP));
      reserve(new Word("is",      Tag.IS));
      reserve(new Word("integer", Tag.INTEGER));
      reserve(new Word("string",  Tag.STRING));
      reserve(new Word("if",      Tag.IF));
      reserve(new Word("else",    Tag.ELSE));
      reserve(new Word("begin",   Tag.BEGIN));
      reserve(new Word("end",     Tag.END));
      reserve(new Word("do",      Tag.DO));
      reserve(new Word("while",   Tag.WHILE));
      reserve(new Word("read",    Tag.READ));
      reserve(new Word("write",   Tag.WRITE));
      reserve(new Word("not",     Tag.NOT));
      reserve(new Word("or",      Tag.OR));
      reserve(new Word("and",     Tag.AND));
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
            return readch('=') ? Word.assign : new Token(':');
         case '=':
            ch = ' ';
            return  Word.eq;
         case '>':
            return readch('=') ? Word.ge : new Token('>');
         case '<':
            readch();
            if (ch == '=') {
               ch = ' ';
               return Word.le;
            }
            if (ch == '>') {
               ch = ' ';
               return Word.ne;
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
         Word w = (Word)words.get(s.toLowerCase());

         if (w != null)
            return w;

         w = new Word(s, Tag.ID);
         words.put(s, w);
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
         Word w = (Word)words.get(s.toLowerCase());

         if (w != null)
            return w;

         w = new Word(s, Tag.LITERAL);
         words.put(s, w);
         return w;
      }

      Token tok = new Token(ch);
      ch = ' ';
      return tok;
   }
}