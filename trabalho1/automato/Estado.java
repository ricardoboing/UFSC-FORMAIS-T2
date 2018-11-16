/*
 *  Trabalho I: Algoritmos para Manipulacao de Linguagens Regulares
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  brunohonnef@gmail.com pedroabcorte@gmail.com ricardoboing.ufsc@gmail.com
 *  
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  11 de Outubro de 2018
 */
package automato;

import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;

public class Estado {
	private String simbolo;
	private boolean isInicial, isFinal;
	
	private ConjuntoEstado conjuntoEpsilonFecho;
	private ConjuntoObject<Transicao> transicoes;
	
	public Estado() {
		this.construir(this.toString());
	}
	public Estado(String simbolo) {
		this.construir(simbolo);
	}
	private void construir(String simbolo) {
		this.simbolo = simbolo;
		this.isInicial = false;
		this.isFinal = false;
		
		this.transicoes = new ConjuntoObject<Transicao>();
		
		this.conjuntoEpsilonFecho = new ConjuntoEstado();
		this.conjuntoEpsilonFecho.add(this);
	}
	
	// Metodos Add
	public Transicao addTransicao(Transicao transicao) {
		return this.transicoes.add(transicao);
	}
	public Transicao addTransicao(char entrada, Estado destino) {
		Transicao transicao;
		transicao = new Transicao();
		transicao.setSimboloEntrada(entrada);
		transicao.setEstadoOrigem(this);
		transicao.setEstadoDestino(destino);
		
		return this.transicoes.add(transicao);
	}
	public void addConjuntoTransicao(ConjuntoObject<Transicao> transicoes) {
		this.transicoes.add(transicoes);
	}
	
	// Metodos Setters
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public void setInicial(boolean isInicial) {
		this.isInicial = isInicial;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	// Metodos Getters
	public String getSimbolo() {
		return simbolo;
	}
	public boolean isInicial() {
		return isInicial;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public ConjuntoObject<Transicao> getConjuntoTransicao() {
		return transicoes;
	}
	public ConjuntoObject<Transicao> getConjuntoTransicao(char entrada) {
		ConjuntoObject<Transicao> reconhecedores;
		reconhecedores = new ConjuntoObject<Transicao>();
		
		/* Percorre todas as producoes e add no conjunto aquelas
		 * que transitao com a entrada
		 */
		for (int c = 0; c < this.transicoes.size(); c++) {
			Transicao transicao;
			transicao = this.transicoes.get(c);
			
			if (transicao.reconhecerEntrada(entrada)) {
				reconhecedores.add(transicao);
			}
		}
		
		return reconhecedores;
	}
	public ConjuntoEstado getConjuntoEpsilonFecho() {
		return this.conjuntoEpsilonFecho;
	}
	
	public boolean reconhece(char entrada) {
		/* Percorre todas as producoes e, caso alguma transite
		 * com o terminal buscado entao reconhece
		 */
		for (int c = 0; c < this.transicoes.size(); c++) {
			Transicao transicao;
			transicao = this.transicoes.get(c);
			
			if (transicao.reconhecerEntrada(entrada)) {
				return true;
			}
		}
		
		return false;
	}
	// Verifica se uma dada entrada eh reconhecida pelo estado
	// Nao faz parte do trabalho e implementacao de ultima hora e duvidosa,
	// portanto nao foi add na interface
	public boolean reconhece(String entrada) {
		if (entrada == null) {
			if (this.isFinal) {
				return true;
			}
			return false;
		}
		if (entrada.equals("")) {
			if (this.isFinal) {
				return true;
			}
			return false;
		}
		
		for (int c = 0; c < this.getConjuntoTransicao().size(); c++) {
			Transicao transicao;
			transicao = this.getConjuntoTransicao().get(c);
			
			char simboloTransicao;
			simboloTransicao = transicao.getSimboloEntrada();
			
			Estado estadoDestino;
			estadoDestino = transicao.getEstadoDestino();
			
			if (entrada.charAt(0) != simboloTransicao) {
				continue;
			}
			
			if (estadoDestino.reconhece(entrada.substring(1))) {
				return true;
			}
		}
		
		return false;
	}
	// Verifica a equivalencia entre dois estados
	@Override
	public boolean equals(Object object) {
		Estado estado;
		estado = (Estado)object;
		
		// Estados sao equivalentes se possuirem mesmo simbolo
		if (!estado.simbolo.equals(this.simbolo)) {
			return false;
		}
		
		return true;
	}
}
