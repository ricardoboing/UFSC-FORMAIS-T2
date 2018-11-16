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

import automato.AutomatoFinito;

public class ConjuntoAlfabeto extends Conjunto<Character> {
	public ConjuntoAlfabeto() {
		super();
	}
	
	@Override
	public Character add(Character simbolo) {
		if (simbolo == AutomatoFinito.EPSILON) {
			//return null;
		}
		
		return super.add(simbolo);
	}
	
	@Override
	public ConjuntoAlfabeto clone() {
		ConjuntoAlfabeto clone;
		clone = new ConjuntoAlfabeto();
		
		for (int c = 0; c < this.array.size(); c++) {
			clone.array.add(this.array.get(c));
		}
		
		return clone;
	}
}
