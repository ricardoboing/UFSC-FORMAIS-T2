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

public class Conjunto<T> extends ConjuntoGenerico<T> {
	public Conjunto() {
		super();
	}
	
	public boolean equals(Conjunto<T> conjuntoComparar) {
		/* Para que os conjuntos sejam equivalentes eh preciso
		 * que todos objetos de um estejam no outro (mesmo numero
		 * de elementos)
		 */
		if (this.size() != conjuntoComparar.size()) {
			return false;
		}
		/* Verifica a existencia de algum objeto nao pertencente
		 * simultaneamente aos dois conjuntos (esteja contido em
		 * um dos conjuntos mas nao esteja no outro)
		 */
		for (int c = 0; c < this.size(); c++) {
			T objeto;
			objeto = this.array.get(c);
			
			if (!conjuntoComparar.contains(objeto)) {
				return false;
			}
		}
		
		return true;
	}
}
