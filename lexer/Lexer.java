package lexer;
import java.io.*;
import java.util.*;
import symbols.*;


public class Lexer {
   public static int line = 1;
   private char ch = ' ';
   private FileReader file;
   
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

   private void readch() throws IOException {
      ch = (char)file.read();

      if (ch == 65535)
         throw new IOException();
   }
   
   private boolean readch(char c) throws IOException {
      readch();
      
      if (ch != c)
         return false;
      
      ch = ' ';
      return true;
   }

   public Token scan() throws IOException {
      for ( ; ; readch() ) {
         if (ch == ' ' || ch == '\t')
            continue;
         else if (ch == '\n')
            line = line + 1;
         else
            break;
      }

      switch(ch) {
         case '&':
            return readch('&') ? Word.and : new Token('&');
         case '|':
            return readch('|') ? Word.or : new Token('|');
         case '=':
            return readch('=') ? Word.eq : new Token('=');
         case '>':
            return readch('>') ? Word.ge : new Token('>');
         case '<':
            if (readch('='))
               return Word.le;
            if (readch('>'))
               return Word.ne;
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
         } while(Character.isLetterOrDigit(ch));

         String s = b.toString();
         Word w = (Word)words.get(s);

         if (w != null)
            return w;

         w = new Word(s, Tag.ID);
         words.put(s, w);
         return w;
      }

      Token tok = new Token(ch);
      ch = ' ';
      return tok;
   }
}