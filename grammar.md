```
program         -> init [decl-list] stmt-list stop

decl-list       -> decl ; decl-list-ext
decl-list-ext   -> LAMBDA
                 | decl ";"

decl            -> ident-list is type

ident-list      -> identifier ident-list-ext
ident-list-ext  -> LAMBDA
                 | , identifier

type            -> integer
                 | string

stmt-list       -> stmt ; stmt-ext
stmt-list-ext   -> LAMBDA
                 | stmt ;

stmt            -> assign-stmt 
                 | if-stmt 
                 | do-stmt 
                 | read-stmt 
                 | write-stmt


assign-stmt     -> identifier := simple_expr

if-stmt         -> if ( condition ) begin stmt-list end if-stmt-ext
if-stmt-ext     -> LAMBDA
                 | else begin stmt-list end

condition       -> expression
do-stmt         -> do stmt-list do-suffix
do-suffix       -> while ( condition )
read-stmt       -> read ( identifier )
write-stmt      -> write ( writable )
writable        -> simple-expr

expression      -> simple-expr expression-ext
expression-ext  -> LAMBDA
                 | relop simple-expr

simple-expr     -> term simple-expr-ext
simple-expr-ext -> LAMBDA
                 | addop term simple-expr-ext

term            -> factor-a term-ext
term-ext        -> mulop factor-a term-ext
                 | lambda

factor-a        -> factor 
                 | not factor 
                 | - factor

factor          -> identifier 
                 | constant 
                 | ( expression )

relop           -> = 
                 | > 
                 | >= 
                 | < 
                 | <= 
                 | <>

addop           -> + 
                 | - 
                 | or

mulop           -> * 
                 | / 
                 | and

constant        -> integer_const 
                 | literal

integer_const   -> nozero
integer_const   -> nozero digit
                 | 0

literal         -> " “ " {caractere} " ” "
identifier      -> (letter) {letter   | digit   | " _ " }
letter          -> [A-Za-z]
digit           -> [0-9]
nozero          -> [1-9]
caractere       -> um dos 256 caracteres do conjunto ASCII, exceto “ " ”
e quebra de linha
```

simple-expr1 e term = recursão a esquerda