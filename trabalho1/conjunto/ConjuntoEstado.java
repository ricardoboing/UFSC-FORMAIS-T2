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
package conjunto;

import java.util.ArrayList;
import java.util.Collections;

import automato.Estado;
import automato.Transicao;

public class ConjuntoEstado extends ConjuntoObject<Estado> {
	public ConjuntoEstado() {
		super();
	}
	
	@Override
	public Estado add(Estado estado) {
		Estado adicionado;
		adicionado = super.add(estado);
		
		/* Caso seja um estado equivalente, ja existente no conjunto,
		 * mas nao igual, entao copia suas transicoes.
		 * Isso pode ocorrer em casos em que um estado eh criado por
		 * conta de uma transicao, e posteriormente eh criado novamente,
		 * ou vice-versa.
		 */
		if (adicionado != estado) {
			ConjuntoObject<Transicao> transicoes;
			transicoes = estado.getConjuntoTransicao();
			
			adicionado.addConjuntoTransicao(transicoes);
			if (estado.isInicial()) {
				adicionado.setInicial(true);
			}
			if (estado.isFinal()) {
				adicionado.setFinal(true);
			}
		}
		
		return adicionado;
	}
	
	@Override
	public ConjuntoEstado clone() {
		ConjuntoEstado clone;
		clone = new ConjuntoEstado();
		clone.array = super.clone().array;
		
		return clone;
	}
	
	public String getToString() {
		String retorno;
		retorno = "";
		
		ArrayList<String> array;
		array = new ArrayList<String>();
		
		// Adiciona os simbolos dos estados em um ArrayList
		for (int c = 0; c < this.array.size(); c++) {
			Estado estado;
			estado = this.array.get(c);
			
			array.add(estado.getSimbolo());
		}
		
		// Ordena esses simbolos
		Collections.sort(array);
		
		// Cria uma string do tipo q0,q1,q2
		for (int c = 0; c < array.size(); c++) {
			if (c > 0) {
				retorno += ",";
			}
			retorno += array.get(c);
		}
		
		return retorno;
	}
}
