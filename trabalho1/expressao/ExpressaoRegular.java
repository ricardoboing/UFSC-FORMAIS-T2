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

import util.ELinguagem;
import util.LinguagemGerador;

public class ExpressaoRegular implements LinguagemGerador {
	public static final String LETTER = "(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|y|x|z)";
	public static final String DIGIT = "(0|1|2|3|4|5|6|7|8|9)";
	
	private String nome;
	private String expressaoOriginal;
	private String expressaoConcatenacaoExplicita;
	
	public ExpressaoRegular(String expressao) {
		this("sem_nome", expressao);
	}
	public ExpressaoRegular(String nome, String expressao) {
		this.nome = nome;
		this.expressaoOriginal = expressao;
		this.expressaoConcatenacaoExplicita = expressao.replaceAll(" ", "");
		
		this.explicitarExpressao();
		this.explicitarConcatenacao();
	}
	
	public String getToStringOriginal() {
		return this.expressaoOriginal;
	}
	public String getToStringExplicita() {
		return this.expressaoConcatenacaoExplicita;
	}
	@Override
	public String getNome() {
		return this.nome;
	}
	/*	Remove parenteses desnecessarios que tornam a expressao a ser implicita.
	 * 	Ex: "((ab)c | (d))" resulta em "(ab)c | (d)"
	 */
	private void explicitarExpressao() {
		String expressaoExplicita;
		expressaoExplicita = this.explicitarMenorPrecedencia().toString();
		expressaoExplicita = expressaoExplicita.replaceAll(" ", "");
		
		if (expressaoExplicita.equals("()")) {
			this.expressaoConcatenacaoExplicita = this.expressaoConcatenacaoExplicita.substring(1, this.expressaoConcatenacaoExplicita.length()-1);
			this.explicitarExpressao();
		}
	}
	/*	Explicita as concatenacoes implicitas da expressao.
	 * 	Ex: "(ab)c | d" resulta em "(a.b).c | d"
	 */
	private void explicitarConcatenacao() {
		String expressaoExplicita;
		expressaoExplicita = "";
		
		char[] caracteresAtual = {'(', '|', '.'};
		char[] caracteresPosterior = {')', '|', '?', '*', '.', '+'};
		
		for (int c = 0; c < this.expressaoConcatenacaoExplicita.length()-1; c++) {
			expressaoExplicita += this.expressaoConcatenacaoExplicita.charAt(c);
			
			char caracterAtual, caracterPosterior;
			caracterAtual = this.expressaoConcatenacaoExplicita.charAt(c);
			caracterPosterior = this.expressaoConcatenacaoExplicita.charAt(c+1);
			
			// Adiciona uma concatenacao caso seja algo como "ab", ou seja,
			// ambos os caracteres nao sao reservados
			if (!this.containsCaracter(caracteresPosterior, caracterPosterior) &&
				!this.containsCaracter(caracteresAtual, caracterAtual)) {
				expressaoExplicita += ".";
			}
		}
		
		expressaoExplicita += this.expressaoConcatenacaoExplicita.charAt(this.expressaoConcatenacaoExplicita.length()-1);
		
		this.expressaoConcatenacaoExplicita = expressaoExplicita;
	}
	/*	Busca um "(". Caso encontrar, substitui os caracteres
	 * 	posteriores por espacos em branco, ate que se encontre
	 *  o ")" correspondente.
	 *  Ex: "((a | b) c) | (d)" resulta em "(         ) | ( )"
	 */
	public String explicitarMenorPrecedencia() {
		char[] expressaoExplicita;
		expressaoExplicita = this.expressaoConcatenacaoExplicita.toCharArray();
		
		int contadorParentesesAberto;
		contadorParentesesAberto = 0;
		
		for (int c = 0; c < expressaoExplicita.length; c++) {
			char caracter;
			caracter = expressaoExplicita[c];
			
			if (caracter == ')') {
				contadorParentesesAberto--;
			}
			if (contadorParentesesAberto > 0) {
				expressaoExplicita[c] = ' ';
			}
			if (caracter == '(') {
				contadorParentesesAberto++;
			}
		}
		
		return String.valueOf(expressaoExplicita);
	}
	/*	Verifica se um array de caracteres possui um determinado
	 * 	caracter
	 */
	private boolean containsCaracter(char[] caracteres, char caracter) {
		for (int c = 0; c < caracteres.length; c++) {
			if (caracter == caracteres[c]) {
				return true;
			}
		}
		
		return false;
	}
	@Override
	public ELinguagem getELinguagem() {
		return ELinguagem.EXPRESSAO;
	}
	
	// Verifica se uma entrada eh valida
	public static boolean entradaValida(String expressao) {
		char valorInicial;
		valorInicial = expressao.charAt(0);
		
		if (expressao.length() == 1 && valorInicial == '(') {
			return false;
		}
		
		char valorInicialInvalido[];
		valorInicialInvalido = new char[] {
			'|', ')', '.', '*', '?'
		};
		
		for (int c = 0; c < valorInicialInvalido.length; c++) {
			if (valorInicialInvalido[c] == valorInicial) {
				return false;
			}
		}
		
		if (expressao.length() == 1) {
			return true;
		}
		
		String combinacaoInvalida1[];
		combinacaoInvalida1 = new String[] {
			"(.", "(*", "(?", "(|", "()",
			".)", ".*", ".?", ".|", "..",
			"|.", "|*", "|?", "||", "|)",
			"??"
		};
		
		for (int c = 0; c < combinacaoInvalida1.length; c++) {
			if (expressao.indexOf(combinacaoInvalida1[c]) >= 0) {
				return false;
			}
		}
		
		String combinacaoInvalida2[];
		combinacaoInvalida2 = new String[] {
			"*.", "*|", "?.", "?|"
		};
		
		String doisUltimosChar;
		doisUltimosChar = expressao.substring(expressao.length()-2);
		
		for (int c = 0; c < combinacaoInvalida2.length; c++) {
			if (combinacaoInvalida2[c].equals(doisUltimosChar)) {
				return false;
			}
		}
		
		char valorFinal;
		valorFinal = expressao.charAt(expressao.length()-1);
		
		char valorFinalInvalido[];
		valorFinalInvalido = new char[] {
			'|', '(', '.'
		};
		
		for (int c = 0; c < valorFinalInvalido.length; c++) {
			if (valorFinalInvalido[c] == valorFinal) {
				return false;
			}
		}
		
		int contadorParentesesAberto;
		contadorParentesesAberto = 0;
		
		char expressaoChar[];
		expressaoChar = expressao.toCharArray();
		
		for (int c = 0; c < expressaoChar.length; c++) {
			char caracter;
			caracter = expressaoChar[c];
			
			if (caracter == ')') {
				contadorParentesesAberto--;
			}
			if (contadorParentesesAberto < 0) {
				return false;
			}
			if (caracter == '(') {
				contadorParentesesAberto++;
			}
		}
		
		if (contadorParentesesAberto > 0) {
			return false;
		}
		
		return true;
	}
}
