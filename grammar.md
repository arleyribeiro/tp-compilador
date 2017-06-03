```
program        -> init decl-list stmt-list stop

decl-list      -> LAMBDA | decl ; decl-list

decl           -> ident-list is type

type           -> integer | string

ident-list     -> identifier ident-list-ext
ident-list-ext -> LAMBDA | , identifier ident-list-ext

stmt-list      -> stmt ; stmt-list-ext 
stmt-list-ext  -> LAMBDA | stmt ; stmt-list-ext

stmt           -> assign-stmt | if-stmt | do-stmt | read-stmt | write-stmt

assign-stmt    -> identifier := simple_expr

if-stmt        -> if ( expression ) begin stmt-list end if-stmt-ext
if-stmt-ext    -> LAMBDA | else begin stmt-list end

do-stmt        -> do stmt-list do-suffix
do-suffix      -> while ( expression )
read-stmt      -> read ( identifier )
write-stmt     -> write ( writable )
writable       -> simple-expr

expression     -> simple-expr expression-ext
expression-ext -> LAMBDA | relop expression

simple-expr    -> term simple-expr-ext
simple-expr-ext-> LAMBDA | addop term simple-expr-ext

term           -> factor-a term-ext
term-ext       -> LAMBDA | mulop factor-a term-ext

factor-a       -> factor | not factor | - factor
factor         -> identifier | constant | ( expression )

relop          -> = | > | >= | < | <= | <>
addop          -> + | - | or
mulop          -> * | / | and

constant       -> num | literal
```

simple-expr1 e term = recursão a esquerda