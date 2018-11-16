package analisador;

public class Token {
	private static final String VALOR_DEFAULT = "-";
	private String nome, valor;
	private Padrao padrao;
	
	public Token(String nome, String stringPadrao) {
		this(nome, stringPadrao, Token.VALOR_DEFAULT);
	}
	public Token(String nome, String stringPadrao, String valor) {
		this.nome = nome;
		this.valor = valor;
		this.padrao = new Padrao(stringPadrao);
	}
	
}
