package lexer;


public class Tag {

    public final static int
        INIT    = 256,
        STOP    = 257,
        IS      = 258,
        INTEGER = 259,
        STRING  = 260,
        IF      = 261,
        ELSE    = 262,
        BEGIN   = 263,
        END     = 264,
        DO      = 265,
        WHILE   = 266,
        READ    = 267,
        WRITE   = 268,

        NOT     = 269,
        OR      = 270,
        AND     = 271,

        ASSIGN  = 272,
        EQ      = 273,
        NE      = 274,
        LE      = 275,
        GE      = 276,

        NUM     = 277,
        ID      = 278,
        LITERAL = 279;
}
