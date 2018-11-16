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

public class ConjuntoObject<T> extends ConjuntoGenerico<T> {
	public ConjuntoObject() {
		super();
	}
	
	@Override
	public T add(T objeto) {
		// Caso ainda nao exista um equivalente, entao adiciona
		if (!this.contains(objeto)) {
			this.array.add(objeto);
			return objeto;
		}
		
		// Busca e retorna o objeto equivalente
		for (int c = 0; c < this.array.size(); c++) {
			T obj;
			obj = this.array.get(c);
			
			if (objeto.equals(obj)) {
				return obj;
			}
		}
		
		/* Nunca vai chegar aqui, pois sempre existira ou nao
		 * um equivalente
		 */
		return null;
	}
	@Override
	public boolean remove(T objeto) {
		// Se existir um objeto equivalente entao o remove
		if ( this.contains(objeto) ) {
			this.array.remove(objeto);
			return true;
		}
		
		return false;
	}
	@Override
	public boolean contains(T objeto) {
		boolean contem;
		contem = super.contains(objeto);
		
		// Se o objeto existir, entao foi encontrado
		if (contem) {
			return true;
		}
		
		// Busca um objeto equivalente
		for (int c = 0; c < this.array.size(); c++) {
			T obj;
			obj = this.array.get(c);
			
			// Retorna o objeto equivalente
			if (objeto.equals(obj)) {
				return true;
			}
		}
		// O objeto nao existe e nao possui um equivalente
		return false;
	}
	
	public boolean equals(ConjuntoObject<T> conjuntoComparar) {
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
	@Override
	public ConjuntoObject<T> clone() {
		ConjuntoObject<T> clone;
		clone = new ConjuntoObject<T>();
		
		for (int c = 0; c < this.array.size(); c++) {
			clone.array.add(this.array.get(c));
		}
		
		return clone;
	}
}
