package lexico;

import automato.AutomatoFinito;
import automato.Estado;
import automato.OperarAutomato;
import conjunto.ConjuntoEstado;
import expressao.ExpressaoRegular;

public class Token {
	private static final String VALOR_DEFAULT = "-";
	private String nome, valor;
	private AutomatoFinito padrao;
	
	public Token(String nome, String stringPadrao) {
		this(nome, stringPadrao, Token.VALOR_DEFAULT);
	}
	public Token(String nome, String stringPadrao, String valor) {
		this.nome = nome;
		this.valor = valor;
		
		this.loadPadrao(stringPadrao);
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getValor() {
		return this.valor;
	}
	public AutomatoFinito getPadrao() {
		return this.padrao;
	}
	
	private void loadPadrao(String stringPadrao) {
		this.padrao = new AutomatoFinito( new ExpressaoRegular(stringPadrao) );
		this.padrao = OperarAutomato.minimizar(this.padrao);
		
		ConjuntoEstado conjuntoEstadoFinal;
		conjuntoEstadoFinal = this.padrao.getConjuntoEstadoFinal();
		
		for (int c = 0; c < conjuntoEstadoFinal.size(); c++) {
			Estado estadoFinal;
			estadoFinal = conjuntoEstadoFinal.get(c);
			estadoFinal.setToken(this);
		}
	}
}
