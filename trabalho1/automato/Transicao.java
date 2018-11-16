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

public class Transicao {
	private Estado estadoOrigem, estadoDestino;
	private Character simboloEntrada;
	
	public Transicao() {
		this.estadoOrigem = null;
		this.estadoDestino = null;
		this.simboloEntrada = null;
	}
	// Metodos Setters
	public void setSimboloEntrada(char simboloEntrada) {
		this.simboloEntrada = simboloEntrada;
	}
	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}
	public void setEstadoDestino(Estado estadoDestino) {
		this.estadoDestino = estadoDestino;
	}
	
	// Metodos Getters
	public char getSimboloEntrada() {
		return simboloEntrada;
	}
	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}
	public Estado getEstadoDestino() {
		return estadoDestino;
	}
	
	public boolean reconhecerEntrada(char entrada) {
		return this.simboloEntrada.equals(entrada);
	}
	public boolean isEstadoDestino(Estado estadoDestino) {
		return this.estadoDestino.equals(estadoDestino);
	}
	public boolean equals(Transicao transicao) {
		if (!this.estadoOrigem.equals(transicao.estadoOrigem)) {
			return false;
		}
		if (!this.estadoDestino.equals(transicao.estadoDestino)) {
			return false;
		}
		if (!this.simboloEntrada.equals(transicao.simboloEntrada)) {
			return false;
		}
		
		return true;
	}
}
