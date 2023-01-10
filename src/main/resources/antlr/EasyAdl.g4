grammar EasyAdl;

// This grammar uses DenterHelper by Yuval Shavit
// to handle indents https://github.com/yshavit/antlr-denter

tokens { INDENT, DEDENT }
@lexer::header {
import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
  private final DenterHelper denter = DenterHelper.builder()
    .nl(NL)
    .indent(EasyAdlParser.INDENT)
    .dedent(EasyAdlParser.DEDENT)
    .pullToken(EasyAdlLexer.super::nextToken);

  @Override
  public Token nextToken() {
    return denter.nextToken();
  }
}

architectureDescription: (systemDefinition | componentDefinition)+ EOF;
systemDefinition: system ':' INDENT (componentDefinition NL?)+ DEDENT;
componentDefinition : component ':' INDENT (operation NL)+ DEDENT;
operation: operator (argument)*;

operator: ID+;
system : SYSTEM ID;
component : COMPONENT ID;
argument : ARGUMENT | component;

COMMENT: '#' ~('\r'|'\n')* -> skip;
SYSTEM : S Y S T E M;
COMPONENT : C O M P O N E N T;
ID : [a-zA-Z0-9_]+ ;
ARGUMENT : '"'~('\r' | '\n' | '"')+?'"';
NL: ('\r'? '\n' ' '*);
WS: [ \t]+ -> channel(HIDDEN) ;

fragment A : [aA]; // match either an 'a' or 'A'
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];
