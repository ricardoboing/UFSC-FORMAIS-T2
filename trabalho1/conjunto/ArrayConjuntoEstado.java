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

import automato.Estado;
import automato.Transicao;

public class ArrayConjuntoEstado {
	private ArrayList<ConjuntoEstado> arrayConjuntoEstado;
	
	public ArrayConjuntoEstado() {
		this.arrayConjuntoEstado = new ArrayList<ConjuntoEstado>();
	}
	
	public boolean add(ConjuntoEstado conjuntoEstado) {
		return this.arrayConjuntoEstado.add(conjuntoEstado);
	}
	public ConjuntoEstado remove(int i) {
		return this.arrayConjuntoEstado.remove(i);
	}
	public boolean remove(ConjuntoEstado conjuntoEstado) {
		return this.arrayConjuntoEstado.remove(conjuntoEstado);
	}
	
	// Verifica se os estados pertencem a um mesmo conjunto
	public boolean pertenceMesmoConjuntoEstado(Estado estado1, Estado estado2) {
		ConjuntoEstado conjuntoEstado1;
		conjuntoEstado1 = this.getConjuntoEstado(estado1);
		
		if (conjuntoEstado1 == null) {
			return false;
		}
		
		if (conjuntoEstado1.contains(estado2)) {
			return true;
		}
		
		return false;
	}
	// Retorna o conjunto ao qual o estado pertence
	public ConjuntoEstado getConjuntoEstado(Estado estado) {
		for (int c = 0; c < this.arrayConjuntoEstado.size(); c++) {
			ConjuntoEstado conjuntoEstado;
			conjuntoEstado = this.arrayConjuntoEstado.get(c);
			
			if (conjuntoEstado.contains(estado)) {
				return conjuntoEstado;
			}
		}
		
		return null;
	}
	public boolean conjuntoTransicaoEquivalente(Estado estado1, Estado estado2) {
		ConjuntoObject<Transicao> conjuntoTransicaoEstado1, conjuntoTransicaoEstado2;
		conjuntoTransicaoEstado1 = estado1.getConjuntoTransicao();
		conjuntoTransicaoEstado2 = estado2.getConjuntoTransicao();
		
		// Verifica se um estado possui mais transicoes do que o outro
		if (conjuntoTransicaoEstado1.size() != conjuntoTransicaoEstado2.size()) {
			return false;
		}
		
		// Verifica se todas transicoes levam para um estadoDestino pertencente ao mesmo ConjuntoEstado
		for (int c = 0; c < conjuntoTransicaoEstado1.size(); c++) {
			Transicao transicaoDoEstado1;
			transicaoDoEstado1 = conjuntoTransicaoEstado1.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstado2PorEntrada;
			conjuntoTransicaoDoEstado2PorEntrada = estado2.getConjuntoTransicao(transicaoDoEstado1.getSimboloEntrada());
			
			// Considera-se que o automato seja deterministico (possui apenas uma transicao por entrada)
			Estado estadoDestinoDoEstado1, estadoDestinoDoEstado2;
			estadoDestinoDoEstado1 = transicaoDoEstado1.getEstadoDestino();
			estadoDestinoDoEstado2 = null;
			if (conjuntoTransicaoDoEstado2PorEntrada.size() > 0) {
				estadoDestinoDoEstado2 = conjuntoTransicaoDoEstado2PorEntrada.get(0).getEstadoDestino();
			}
			if (estadoDestinoDoEstado1 == null && estadoDestinoDoEstado2 == null) {
				continue;
			}
			if (estadoDestinoDoEstado1 == null || estadoDestinoDoEstado2 == null) {
				return false;
			}
			
			ConjuntoEstado conjuntoEstadoDoEstadoDestinoDoEstado1;
			conjuntoEstadoDoEstadoDestinoDoEstado1 = this.getConjuntoEstado(transicaoDoEstado1.getEstadoDestino());
			
			/* Se alguma transicao do estado1 levar para um estado diferente
			 * da transicao do estado 2, entao os estados nao sao equivalentes
			 */
			if (!conjuntoEstadoDoEstadoDestinoDoEstado1.contains(estadoDestinoDoEstado2)) {
				return false;
			}
		}
		
		return true;
	}
	public int size() {
		return this.arrayConjuntoEstado.size();
	}
	public ConjuntoEstado get(int i) {
		return this.arrayConjuntoEstado.get(i);
	}
	
	public static boolean conjuntoEstadoPossuiEstadoInicial(ConjuntoEstado conjuntoEstado) {
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			if (estado.isInicial()) {
				return true;
			}
		}
		
		return false;
	}
	public static boolean conjuntoEstadoPossuiEstadoFinal(ConjuntoEstado conjuntoEstado) {
		for (int c = 0; c < conjuntoEstado.size(); c++) {
			Estado estado;
			estado = conjuntoEstado.get(c);
			
			if (estado.isFinal()) {
				return true;
			}
		}
		
		return false;
	}
}