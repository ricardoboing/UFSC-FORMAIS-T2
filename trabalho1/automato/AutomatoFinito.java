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

import java.util.Iterator;

import conjunto.ConjuntoAlfabeto;
import conjunto.ConjuntoEstado;
import conjunto.ConjuntoObject;
import expressao.ExpressaoRegular;
import expressao.NoDeSimone;
import lexico.Token;
import util.Linguagem;
import util.LinguagemGerador;

public class AutomatoFinito implements Linguagem {
	public static final char EPSILON = '&';
	
	private String nome;
	private Estado estadoInicial;
	
	private ConjuntoEstado conjuntoEstado;
	private ConjuntoAlfabeto conjuntoAlfabeto;
	private ConjuntoObject<Transicao> conjuntoTransicao;
	
	private String nomePai1, nomePai2;
	private String nomeOperacaoGerador1;
	
	public AutomatoFinito() {
		this("sem_nome");
	}
	public AutomatoFinito(String nome) {
		this.nome = nome;
		this.estadoInicial = null;
		
		this.nomePai1 = "";
		this.nomePai2 = "";
		this.nomeOperacaoGerador1 = "";
		
		this.conjuntoEstado = new ConjuntoEstado();
		this.conjuntoAlfabeto = new ConjuntoAlfabeto();
		this.conjuntoTransicao = new ConjuntoObject<Transicao>();
	}
	public AutomatoFinito(LinguagemGerador linguagemGerador) {
		this("", linguagemGerador);
	}
	public AutomatoFinito(String nome, LinguagemGerador linguagemGerador) {
		this(nome);
		
		switch (linguagemGerador.getELinguagem()) {
			case GRAMATICA:
				break;
			case EXPRESSAO:
				this.gerarAutomato((ExpressaoRegular)linguagemGerador);
				break;
			default:
				break;
		}
	}
	// Construtor para facilitar a criacao de Automatos em testes JUnit
	public AutomatoFinito(String nome, String stringAutomato) {
		this(nome);
		this.gerarAutomato(stringAutomato);
	}
	
	// Metodo para gerar um Automato salvo em disco
	private void gerarAutomato(String stringAutomato) {
		stringAutomato = stringAutomato.replaceAll(" ", "");
		
		/*	Transforma stringAutomato em array de estados.
		 * 	Ex: array[0] = "S-> a | bA"; array[1] = "A -> b";
		 */
		String[] arrayNaoTerminal;
		arrayNaoTerminal = stringAutomato.split("\n");
		
		// Cria os naoTerminais, um a um, e cria suas producoes
		for (int c = 0; c < arrayNaoTerminal.length; c++) {
			String stringEstado;
			stringEstado = arrayNaoTerminal[c];
			
			char isFinal, isInicial;
			isFinal = stringEstado.charAt(0);
			isInicial = stringEstado.charAt(1);
			
			int indiceFimDoSimboloEstado;
			indiceFimDoSimboloEstado = stringEstado.indexOf("->");
			
			String simboloDoEstado;
			simboloDoEstado = stringEstado.substring(2,indiceFimDoSimboloEstado);
			
			Estado estado;
			estado = new Estado(simboloDoEstado);
			estado = this.addEstado(estado);
			
			if (isFinal == '*') {
				estado.setFinal(true);
			}
			if (isInicial == '>') {
				estado.setInicial(true);
				this.setEstadoInicial(estado);
			}
			
			String stringTransicoes;
			stringTransicoes = stringEstado.substring(indiceFimDoSimboloEstado+2);
			
			/*	Transforma stringTransicoes em array de producoes.
			 * 	Ex (S-> a | bS): array[0] = "a"; array[1] = "bS";
			 */
			String[] arrayTransicao;
			arrayTransicao = stringTransicoes.split("\\|");
			
			for (int i = 0; i < arrayTransicao.length; i++) {
				String stringTransicao;
				stringTransicao = arrayTransicao[i];
				
				if (stringTransicao.equals("")) {
					break;
				}
				
				char entradaTransicao;
				entradaTransicao = stringTransicao.charAt(0);
				
				this.conjuntoAlfabeto.add(entradaTransicao);
				
				Estado estadoTransicao;
				estadoTransicao = new Estado(stringTransicao.substring(1));
				estadoTransicao = this.addEstado(estadoTransicao);
				
				estado.addTransicao(entradaTransicao, estadoTransicao);
			}
		}
	}
	// Metodo para facilitar a criacao de Automatos em testes JUnit
	public String getStringConjuntoTransicao() {
		String stringConjuntoTransicaoCompleto;
		stringConjuntoTransicaoCompleto = "";
		
		Iterator<Estado> iteratorConjuntoEstado;
		iteratorConjuntoEstado = this.conjuntoEstado.getIterador();
		
		/* Percorre o ConjuntoProducao de todo naoTerminal do ConjuntoNaoTerminal
		 * e, para cada uma, concatena a producao em uma string.
		 * Ex: S -> aS | a
		 */
		while (iteratorConjuntoEstado.hasNext()) {
			Estado estado;
			estado = iteratorConjuntoEstado.next();
			
			Iterator<Transicao> iteratorConjuntoTransicao;
			iteratorConjuntoTransicao = estado.getConjuntoTransicao().getIterador();
			
			String stringConjuntoTransicao;
			stringConjuntoTransicao = "";
			
			/* Percorre o conjuntoProducao do NaoTerminal e concatena
			 * cada producao em uma string
			 */
			while (iteratorConjuntoTransicao.hasNext()) {
				// Concatena "|" a partir da segunda producao
				if (!stringConjuntoTransicao.equals("")) {
					stringConjuntoTransicao += "|";
				}
				
				Transicao transicao;
				transicao = iteratorConjuntoTransicao.next();
				
				// Concatena o terminal
				stringConjuntoTransicao += transicao.getSimboloEntrada();
				stringConjuntoTransicao += transicao.getEstadoDestino().getSimbolo();
			}
			
			// Concatena "\n" a partir do segundo naoTerminal
			if (!stringConjuntoTransicaoCompleto.equals("")) {
				stringConjuntoTransicaoCompleto += "\n";
			}
			
			// Concatena o naoTerminal com suas producoes na string global
			if (estado.isFinal()) {
				stringConjuntoTransicaoCompleto += "*";
			} else {
				stringConjuntoTransicaoCompleto += "_";
			}
			if (estado.isInicial()) {
				stringConjuntoTransicaoCompleto += ">";
			} else {
				stringConjuntoTransicaoCompleto += "_";
			}
			
			stringConjuntoTransicaoCompleto += estado.getSimbolo();
			stringConjuntoTransicaoCompleto += "->";
			stringConjuntoTransicaoCompleto += stringConjuntoTransicao;
		}
		
		return stringConjuntoTransicaoCompleto;
	}
	
	public void alterarSimboloDosEstados() {
		this.estadoInicial.setSimbolo("q0");
		int i;
		i = 1;
		
		// Altera o simbolo dos estados. Essa etapa nao pode ser realizada
		// durante o mapeamento de "NaoTerminal" para "Estado"
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (estado == this.estadoInicial) {
				continue;
			}
			estado.setSimbolo("q"+i);
			i++;
		}
	}
	private void gerarAutomato(ExpressaoRegular expressao) {
		NoDeSimone no;
		no = new NoDeSimone(expressao);
		no.gerarArvoreSintatica();
		
		AutomatoFinito automato;
		automato = no.gerarAutomato();
		
		this.estadoInicial = automato.estadoInicial;
		
		this.conjuntoAlfabeto = automato.conjuntoAlfabeto;
		this.conjuntoTransicao = automato.conjuntoTransicao;
		this.conjuntoEstado = automato.conjuntoEstado;
	}
	
	// Metodos Add
	public Estado addEstado(Estado estado) {
		return this.conjuntoEstado.add(estado);
	}
	public void addEntradaAlfabeto(char entradaAlfabeto) {
		this.conjuntoAlfabeto.add(entradaAlfabeto);
	}
	public Transicao addTransicao(Transicao transicao) {
		return this.conjuntoTransicao.add(transicao);
	}
	public void addConjuntoEstado(ConjuntoEstado conjuntoEstado) {
		this.conjuntoEstado.add(conjuntoEstado);
	}
	public void addConjuntoTransicao(ConjuntoObject<Transicao> conjuntoTransicao) {
		this.conjuntoTransicao.add(conjuntoTransicao);
	}
	
	// Metodos Setters
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEstadoInicial(Estado estadoInicial) {
		this.estadoInicial = estadoInicial;
		this.estadoInicial.setInicial(true);
		
		this.conjuntoEstado.add(estadoInicial);
	}
	public boolean addEstadoInicial(Estado estadoInicial) {
		if (this.possuiEstadoInicial()) {
			return false;
		}
		
		this.setEstadoInicial(estadoInicial);
		
		return true;
	}
	public void setConjuntoEstado(ConjuntoEstado conjuntoEstado) {
		this.conjuntoEstado = conjuntoEstado;
	}
	public void setConjuntoAlfabeto(ConjuntoAlfabeto conjuntoAlfabeto) {
		this.conjuntoAlfabeto = conjuntoAlfabeto;
	}
	public void setConjuntoTransicao(ConjuntoObject<Transicao> conjuntoTransicao) {
		this.conjuntoTransicao = conjuntoTransicao;
	}
	
	// Metodos Getters
	@Override
	public String getNome() {
		return nome;
	}
	@Override
	public AutomatoFinito clone() {
		AutomatoFinito automatoClone;
		automatoClone = new AutomatoFinito();
		automatoClone.conjuntoAlfabeto = this.conjuntoAlfabeto;
		
		// Clona todos os estadoOriginal do automatoOriginal para o automatoClone
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estadoOriginal;
			estadoOriginal = this.conjuntoEstado.get(c);
			
			Estado estadoClone;
			estadoClone = new Estado(estadoOriginal.getSimbolo());
			
			if (estadoOriginal.isInicial()) {
				automatoClone.setEstadoInicial(estadoClone);
			}
			if (estadoOriginal.isFinal()) {
				estadoClone.setFinal(true);
			}
			estadoClone.setToken( estadoOriginal.getToken() );
			
			automatoClone.addEstado(estadoClone);
		}
		
		// Clona as transicoes do estadoOriginal para o estadoClone
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			// Busca estadoOriginal
			Estado estadoOriginal;
			estadoOriginal = this.conjuntoEstado.get(c);
			
			// Busca estadoClone
			Estado estadoClone;
			estadoClone = automatoClone.conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicaoDoEstadoOriginal;
			conjuntoTransicaoDoEstadoOriginal = estadoOriginal.getConjuntoTransicao();
			
			/* Todas transicoes do automatoOriginal sao repassadas ao
			 * automatoClone, porem o estadoOrigem e estadoDestino das
			 * transicoes clonadas fazem referencia a estados do automatoClone
			 * 
			 */
			for (int i = 0; i < conjuntoTransicaoDoEstadoOriginal.size(); i++) {
				Transicao transicaoDoEstadoOriginal;
				transicaoDoEstadoOriginal = conjuntoTransicaoDoEstadoOriginal.get(i);
				
				Estado estadoDestinoOriginal;
				estadoDestinoOriginal = transicaoDoEstadoOriginal.getEstadoDestino();
				
				// Busca estadoDestinoClone equivalente ao estadoDestinoOriginal
				Estado estadoDestinoClone;
				estadoDestinoClone = automatoClone.getEstado(estadoDestinoOriginal.getSimbolo());
				
				// Cria transicaoClone
				estadoClone.addTransicao(transicaoDoEstadoOriginal.getSimboloEntrada(), estadoDestinoClone);
			}
		}
		
		return automatoClone;
	}
	public Estado getEstadoInicial() {
		return this.estadoInicial;
	}
	public ConjuntoEstado getConjuntoEstado() {
		return this.conjuntoEstado;
	}
	// Cria um conjunto contendo apenas os estados finais
	public ConjuntoEstado getConjuntoEstadoFinal() {
		ConjuntoEstado conjuntoEstadoFinal;
		conjuntoEstadoFinal = new ConjuntoEstado();
		
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (estado.isFinal()) {
				conjuntoEstadoFinal.add(estado);
			}
		}
		
		return conjuntoEstadoFinal;
	}
	// Cria um conjunto contendo apenas os estados nao finais
	public ConjuntoEstado getConjuntoEstadoNaoFinal() {
		ConjuntoEstado conjuntoEstadoNaoFinal;
		conjuntoEstadoNaoFinal = new ConjuntoEstado();
		
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (!estado.isFinal()) {
				conjuntoEstadoNaoFinal.add(estado);
			}
		}
		
		return conjuntoEstadoNaoFinal;
	}
	
	public ConjuntoAlfabeto getConjuntoAlfabeto() {
		return this.conjuntoAlfabeto;
	}
	public ConjuntoObject<Transicao> getConjuntoTransicao() {
		return this.conjuntoTransicao;
	}
	// Busca um estado pelo seu simbolo
	public Estado getEstado(String simbolo) {
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			if (simbolo.equals(estado.getSimbolo())) {
				return estado;
			}
		}
		
		return null;
	}
	
	public boolean possuiEstadoInicial() {
		return (this.estadoInicial != null);
	}
	// Metodo nao utilizado, mas vai permanecer
	// para evitar possiveis erros de ultima hora
	@Override
	public boolean equals(Object object) {
		return false;
	}
	
	public void setNomePai1(String nomePai) {
		this.nomePai1 = nomePai;
	}
	public void setNomePai2(String nomePai2) {
		this.nomePai2 = nomePai2;
	}
	public void setNomeOperacaoGerador(String nomeOperacaoGerador1) {
		this.nomeOperacaoGerador1 = nomeOperacaoGerador1;
	}
	
	public String getNomePai1() {
		return this.nomePai1;
	}
	public String getNomePai2() {
		return this.nomePai2;
	}
	public String getGeradorPor1() {
		return this.nomeOperacaoGerador1;
	}
	// Escreve o automato no terminal
	public void print() {
		for (int c = 0; c < this.conjuntoEstado.size(); c++) {
			Estado estado;
			estado = this.conjuntoEstado.get(c);
			
			ConjuntoObject<Transicao> conjuntoTransicao;
			conjuntoTransicao = estado.getConjuntoTransicao();
			
			if (conjuntoTransicao.size() == 0) {
				if (estado.isInicial()) {
					System.out.print(">");
				} else {
					System.out.print(" ");
				}
				if (estado.isFinal()) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				
				System.out.println(estado.getSimbolo()+": ");
			}
			
			for (int i = 0; i < conjuntoTransicao.size(); i++) {
				Transicao transicao;
				transicao = conjuntoTransicao.get(i);
				
				if (transicao.getEstadoOrigem().isInicial()) {
					System.out.print(">");
				} else {
					System.out.print(" ");
				}
				if (transicao.getEstadoOrigem().isFinal()) {
					System.out.print("*");
				} else {
					System.out.print(" ");
				}
				
				System.out.print(transicao.getEstadoOrigem().getSimbolo()+": ");
				System.out.print("("+transicao.getEstadoOrigem().getSimbolo());
				System.out.print(", "+transicao.getSimboloEntrada());
				System.out.println(") -> "+transicao.getEstadoDestino().getSimbolo());
			}
		}
	}
	
	// Verifica se uma dada entrada eh reconhecida pelo automato
	// Nao faz parte do trabalho e implementacao de ultima hora e duvidosa,
	// portanto nao foi add na interface
	public Token reconhecerLexema(String lexema) {
		// Se algum dos simbolos nao existir no alfabeto entao entrada invalida
		for (int c = 0; c < lexema.length(); c++) {
			if (!this.conjuntoAlfabeto.contains(lexema.charAt(c))) {
				return null;
			}
		}
		
		Token tokenReconhecido;
		tokenReconhecido = this.estadoInicial.reconhece(lexema);
		
		return tokenReconhecido;
	}
}
