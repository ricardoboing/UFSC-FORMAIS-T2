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
package expressao;

import conjunto.ConjuntoObject;

public class ComposicaoDeSimone {
	private ConjuntoObject<NoDeSimone> conjuntoNoDeSimone;
	private EstadoDeSimone estadoDeSimone;
	
	private char simbolo;
	
	public ComposicaoDeSimone(char simbolo) {
		this.simbolo = simbolo;
		this.estadoDeSimone = new EstadoDeSimone();
		this.conjuntoNoDeSimone = new ConjuntoObject<NoDeSimone>();
	}
	public void addNoDeSimone(NoDeSimone no) {
		this.conjuntoNoDeSimone.add(no);
	}
	public char getSimbolo() {
		return this.simbolo;
	}
	public EstadoDeSimone getEstadoDeSimone() {
		return this.estadoDeSimone;
	}
	public ConjuntoObject<NoDeSimone> getConjuntoNoDeSimone() {
		return this.conjuntoNoDeSimone;
	}
	@Override
	public boolean equals(Object obj) {
		ComposicaoDeSimone estadoEquals;
		estadoEquals = (ComposicaoDeSimone) obj;
		
		// Se o conjunto de nos de uma composicao for diferente da outra, entao as
		// composicoes nao sao equivalentes
		if (this.conjuntoNoDeSimone.size() != estadoEquals.conjuntoNoDeSimone.size()) {
			return false;
		}
		
		// Verifica se algum no de uma composicao esta contido na outra composicao
		for (int c = 0; c < this.conjuntoNoDeSimone.size(); c++) {
			NoDeSimone noThis;
			noThis = this.conjuntoNoDeSimone.get(c);
			
			boolean possuiEquivalente;
			possuiEquivalente = false;
			
			for (int i = 0; i < estadoEquals.conjuntoNoDeSimone.size(); i++) {
				NoDeSimone noEquals;
				noEquals = estadoEquals.conjuntoNoDeSimone.get(i);
				
				if (noThis.equals(noEquals)) {
					possuiEquivalente = true;
					break;
				}
			}
			// Se algum no esta contido em uma mas nao na outra, entao as composicoes
			// nao sao equivalentes
			if (!possuiEquivalente) {
				return false;
			}
		}
		
		return true;
	}
	public void setEstadoDeSimone(EstadoDeSimone estadoEquivalente) {
		this.estadoDeSimone = estadoEquivalente;
	}
}
