grammar TaYi;

program     : 'start TaYi<' ID '>' '(' parameters ')' ':' block 'end TaYi(' type ')' EOF;
parameters  : parameter (',' parameter)*;
parameter   : type ID;
block       : (statement)*;
statement   : variableDeclaration ';' | loopStatement | ifStatement | systemFunctionCall ';'| returnStatement ';';
variableDeclaration: type VARIABLE '=' expression;
loopStatement: 'loop' '(' INT ':' INT ')' 'do' '{' block '}';
ifStatement : 'if' '(' expression ')' 'do' '{' block '}';
returnStatement: 'return' expression;
systemFunctionCall: 'that' '->' ID '(' arguments? ')';

expression  : expression ('+' | '-' | '*' | '/') expression
            | INT
            | VARIABLE
            | STRING;

arguments   : expression (',' expression)*;
type        : 'int' | 'string' | 'bool';

// 词法规则
INT         : [0-9]+ ;
STRING      : '"' .*? '"' ;
VARIABLE    : [a-zA-Z_][a-zA-Z_0-9]* ;
ID          : [a-zA-Z_][a-zA-Z_0-9]* ;

// 跳过空白字符
WS          : [ \t\r\n]+ -> skip ;

// 注释
LINE_COMMENT: '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;
