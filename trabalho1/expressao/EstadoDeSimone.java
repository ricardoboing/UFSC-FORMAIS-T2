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

import automato.Estado;
import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoObject;

class EstadoDeSimone {
	private ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private Estado estado;
	
	public EstadoDeSimone() {
		this(false);
	}
	public EstadoDeSimone(boolean estadoInicial) {
		this.conjuntoComposicao = new ConjuntoObject<ComposicaoDeSimone>();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		
		this.estado = new Estado("q");
		this.estado.setInicial(estadoInicial);
		
		// Simbolo qualquer para nao conflitar com nome de outros estados.
		// O simbolo eh alterado apos o Automato ser gerado na classe NoDeSimone.
		this.estado.setSimbolo(this.estado.toString());
	}
	
	public void addNoDeSimone(NoDeSimone no) {
		char simboloNo;
		simboloNo = no.getSimbolo().charAt(0);
		
		if (no.nodoFimDaArvore()) {
			this.estado.setFinal(true);
			return;
		}
		
		// Se o NoDeSimone for eh do grupo de simbolos que ja
		// possui uma composicao existente
		if (this.conjuntoAlfabeto.contains(simboloNo)) {
			for (int c = 0; c < this.conjuntoAlfabeto.size(); c++) {
				ComposicaoDeSimone composicao;
				composicao = conjuntoComposicao.get(c);
				
				if (composicao.getSimbolo() == simboloNo) {
					composicao.addNoDeSimone(no);
					
					return;
				}
			}
			// Se nao cria uma nova composicao
		} else {
			ComposicaoDeSimone novaComposicao;
			novaComposicao = new ComposicaoDeSimone(simboloNo);
			novaComposicao.addNoDeSimone(no);
			
			this.conjuntoComposicao.add(novaComposicao);
			this.conjuntoAlfabeto.add(simboloNo);
		}
		
		return;
	}
	
	// Adiciona as composicoes do EstadoDeSimone no conjuntoGlobal de composicoes
	public void atualizarConjuntoComposicao(ConjuntoObject<ComposicaoDeSimone> conjuntoComposicao) {
		ConjuntoObject<ComposicaoDeSimone> conjuntoComposicaoFilho;
		conjuntoComposicaoFilho = new ConjuntoObject<ComposicaoDeSimone>();
		
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = this.conjuntoComposicao.get(c);
			composicao = conjuntoComposicao.add(composicao);
			
			conjuntoComposicaoFilho.add(composicao);
		}
		
		this.conjuntoComposicao = conjuntoComposicaoFilho;
	}
	
	public Estado gerarEstadoAf() {
		// Gera um estado de automato de acordo com as composicoes
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicao;
			composicao = this.conjuntoComposicao.get(c);
			
			EstadoDeSimone estadoDaComposicao;
			estadoDaComposicao = composicao.getEstadoDeSimone();
			
			Estado estadoAf;
			estadoAf = estadoDaComposicao.estado;
			
			char simboloEntrada;
			simboloEntrada = composicao.getSimbolo();
			
			this.estado.addTransicao(simboloEntrada, estadoAf);
		}
		
		return this.estado;
	}
	
	@Override
	public boolean equals(Object obj) {
		EstadoDeSimone estadoEquals;
		estadoEquals = (EstadoDeSimone) obj;
		
		// Se o conjunto de composicoes de um no for diferente do outro, entao os
		// nos nao sao equivalentes
		if (this.conjuntoComposicao.size() != estadoEquals.conjuntoComposicao.size()) {
			return false;
		}
		
		// Verifica se algum uma composicao de um no esta contido no outro no
		for (int c = 0; c < this.conjuntoComposicao.size(); c++) {
			ComposicaoDeSimone composicaoThis;
			composicaoThis = this.conjuntoComposicao.get(c);
			
			boolean existeEquivalente;
			existeEquivalente = false;
			
			for (int i = 0; i < estadoEquals.conjuntoComposicao.size(); i++) {
				ComposicaoDeSimone composicaoEquals;
				composicaoEquals = estadoEquals.conjuntoComposicao.get(i);
				
				if (composicaoThis.equals(composicaoEquals)) {
					existeEquivalente = true;
					break;
				}
			}
			// Se alguma composicao esta contido em um mas nao no outro, entao
			// os nos nao sao equivalentes
			if (!existeEquivalente) {
				return false;
			}
		}
		
		return true;
	}
}
