package analisador;

public class ListaDeTokenReservado {
	public static final Token BASIC = new Token("BASIC", "basic");
	public static final Token WHILE = new Token("WHILE", "while");
	public static final Token DO    = new Token("DO", "do");
	public static final Token BREAK = new Token("BREAK", "break");
	public static final Token IF    = new Token("IF", "if");
	public static final Token THEN  = new Token("THEN", "then");
	public static final Token ELSE  = new Token("ELSE", "else");
	public static final Token TRUE  = new Token("BOOL", "true", "TRUE");
	public static final Token FALSE = new Token("BOOL", "false", "FALSE");
	public static final Token REAL  = new Token("TYPE", "real", "REAL");
	
	public static final Token MENOR 			= new Token("COMPARADOR", "<", "MENOR");
	public static final Token MENOR_IGUAL 		= new Token("COMPARADOR", "<=", "MENOR_IGUAL");
	public static final Token MAIOR 			= new Token("COMPARADOR", ">", "MAIOR");
	public static final Token IGUAL 			= new Token("COMPARADOR", "==", "IGUAL");
	public static final Token DIFERENTE 		= new Token("COMPARADOR", "!=", "DIFERENTE");
	public static final Token OU 				= new Token("AGRUPADOR_LOGICO", "||", "OU");
	public static final Token E 				= new Token("AGRUPADOR_LOGICO", "&&", "E");
	public static final Token MULTIPLICACAO 	= new Token("MATEMATICO", "*", "MULTIPLICACAO");
	public static final Token SOMA				= new Token("MATEMATICO", "+", "SOMA");
	public static final Token SUBTRACAO 		= new Token("MATEMATICO", "-", "SUBTRACAO");
	public static final Token DIVISAO 			= new Token("MATEMATICO", "/", "DIVISAO");
	public static final Token PONTO_VIRGULA 	= new Token("PONTO_VIRGULA", ";", "PONTO_VIRGULA");
	public static final Token ATRIBUICAO 		= new Token("ATRIBUICAO", "=", "ATRIBUICAO");
	public static final Token CHAVE_ABRE 		= new Token("CHAVE", "{", "ABRE");
	public static final Token CHAVE_FECHA 		= new Token("CHAVE", "}", "FECHA");
	public static final Token PARENTESES_ABRE 	= new Token("PARENTESES", "(", "ABRE");
	public static final Token PARENTESES_FECHA 	= new Token("PARENTESES", ")", "FECHA");
	public static final Token COLCHETES_ABRE 	= new Token("COLCHETES", "[", "ABRE");
	public static final Token COLCHETES_FECHA 	= new Token("COLCHETES", "]", "FECHA");
}
