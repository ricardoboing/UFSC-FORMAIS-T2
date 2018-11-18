package lexico;

import java.util.ArrayList;

import automato.AutomatoFinito;
import automato.OperarAutomato;
import expressao.ExpressaoRegular;
import simbolo.TabelaDeSimbolos;
import util.Arquivo;

public class AnalisadorLexico {
	private TabelaDeSimbolos tabelaDeSimbolos;
	private AutomatoFinito automatoUniao;
	
	private String errosLexicos;
	
	private String[] buffer;
	private int contadorBuffer;
	private int contadorLinha;
	
	public AnalisadorLexico(String programaFonte) {
		programaFonte = this.lerProgramaFonte(programaFonte);
		
		this.buffer = programaFonte.split(" ");
		this.contadorBuffer = 0;
		this.contadorLinha = 1;
		this.errosLexicos = "";
		
		this.tabelaDeSimbolos = new TabelaDeSimbolos();
		
		this.loadTabelaDeSimbolos();
		this.gerarAutomato();
	}
	
	private String lerProgramaFonte(String urlCodigoFonte) {
		String[] simbolosReservados;
		simbolosReservados = new String[] {
			"<=", "==", "!=", ">=", "&&", "\\|\\|", "\n",
			"\\(", "\\)", "\\[", "\\]", "\\{", "\\}", ";", "=",
			"\\*", "\\+", "-", "/", "<", ">", "!"
		};
		
		String programaFonte;
		programaFonte = Arquivo.ler(urlCodigoFonte);
		
		for (int c = 0; c < simbolosReservados.length; c++) {
			String simboloReservado;
			simboloReservado = simbolosReservados[c];
			
			programaFonte = programaFonte.replaceAll(simboloReservado, " "+simboloReservado+" ");
		}
		
		return programaFonte;
	}
	private void loadTabelaDeSimbolos() {
		this.tabelaDeSimbolos.addSimbolo("basic", "basic", "");
		this.tabelaDeSimbolos.addSimbolo("while", "while", "");
		this.tabelaDeSimbolos.addSimbolo("break", "break", "");
		this.tabelaDeSimbolos.addSimbolo("false", "false", "");
		this.tabelaDeSimbolos.addSimbolo("true", "true", "");
		this.tabelaDeSimbolos.addSimbolo("else", "else", "");
		this.tabelaDeSimbolos.addSimbolo("then", "then", "");
		this.tabelaDeSimbolos.addSimbolo("real", "real", "");
		this.tabelaDeSimbolos.addSimbolo("do", "do", "");
		this.tabelaDeSimbolos.addSimbolo("if", "if", "");
		
		this.tabelaDeSimbolos.addSimbolo("<", "relop", "LT");
		this.tabelaDeSimbolos.addSimbolo(">", "relop", "GT");
		this.tabelaDeSimbolos.addSimbolo("==", "relop", "EQ");
		this.tabelaDeSimbolos.addSimbolo("!=", "relop", "NE");
		this.tabelaDeSimbolos.addSimbolo("<=", "relop", "LE");
		this.tabelaDeSimbolos.addSimbolo(">=", "relop", "GE");
		
		this.tabelaDeSimbolos.addSimbolo("=", "eq", "");
		this.tabelaDeSimbolos.addSimbolo("*", "op", "");
		this.tabelaDeSimbolos.addSimbolo("+", "op", "");
		this.tabelaDeSimbolos.addSimbolo("-", "op", "");
		this.tabelaDeSimbolos.addSimbolo("/", "op", "");
		
		this.tabelaDeSimbolos.addSimbolo("||", "logical", "OR");
		this.tabelaDeSimbolos.addSimbolo("&&", "logical", "AND");
		this.tabelaDeSimbolos.addSimbolo(";", "semicolon", "");
		this.tabelaDeSimbolos.addSimbolo("{", "key", "OPEN");
		this.tabelaDeSimbolos.addSimbolo("}", "key", "CLOSE");
		this.tabelaDeSimbolos.addSimbolo("(", "parentheses", "OPEN");
		this.tabelaDeSimbolos.addSimbolo(")", "parentheses", "CLOSE");
		this.tabelaDeSimbolos.addSimbolo("[", "brackets", "OPEN");
		this.tabelaDeSimbolos.addSimbolo("]", "brackets", "CLOSE");
	}
	private void gerarAutomato() {
		Token tokenID, tokenNUM;
		tokenID  = new Token("id",  ExpressaoRegular.LETTER+" ( "+ExpressaoRegular.LETTER+" | "+ExpressaoRegular.DIGIT+" )* ");
		tokenNUM = new Token("num", ExpressaoRegular.DIGIT+ExpressaoRegular.DIGIT+"* ,* "+ExpressaoRegular.DIGIT+"*");
		
		ArrayList<Token> listaDeToken;
		listaDeToken = new ArrayList<Token>();
		listaDeToken.add(tokenID);
		listaDeToken.add(tokenNUM);
		
		this.automatoUniao = listaDeToken.get(0).getPadrao();
		
		for (int c = 1; c < listaDeToken.size(); c++) {
			AutomatoFinito automatoDoToken;
			automatoDoToken = listaDeToken.get(c).getPadrao();
			
			this.automatoUniao = OperarAutomato.unir(automatoUniao, automatoDoToken);
		}
		
		this.automatoUniao = OperarAutomato.determinizar(automatoUniao);
	}
	
	public String getProximoToken() throws Exception {
		// Nao possui mais simbolos no buffer
		if (!this.proximoToken()) {
			return null;
		}
		
		String lexema;
		lexema = this.proximoLexemaBuffer();
		
		// Espaco vazio
		if (lexema.equals("")) {
			return this.getProximoToken();
		}
		// Quebra de linha
		if (lexema.equals("\n")) {
			this.contadorLinha++;
			return this.getProximoToken();
		}
		
		int indiceToken;
		indiceToken = this.tabelaDeSimbolos.getLexemaIndice(lexema);
		
		// Verifica ja existe na tabela
		if (indiceToken >= 0) {
			String token;
			token = this.tabelaDeSimbolos.getToken(indiceToken);
			
			return this.formatarToken(token, indiceToken);
		}
		
		Token token;
		token = this.automatoUniao.reconhecerLexema(lexema);
		
		// Simbolo invalido
		if (token == null) {
			this.lexemaInvalido(lexema, this.contadorLinha);
		}
		
		int indice;
		indice = this.tabelaDeSimbolos.addSimbolo(lexema, token.getNome(), token.getValor());
		
		return this.formatarToken(token.getNome(), indice);
	}
	
	public boolean proximoToken() {
		return (this.contadorBuffer < this.buffer.length);
	}
	public void gerarLogs() {
		Arquivo.escrever("errosLexicos.txt", errosLexicos);
		System.out.println("\n----------------\n");
		this.tabelaDeSimbolos.escreverTabela();
	}
	private String proximoLexemaBuffer() {
		String lexema;
		lexema = this.buffer[this.contadorBuffer];
		
		this.contadorBuffer++;
		
		return lexema;
	}
	private String formatarToken(String token, int indice) {
		return "["+token+","+indice+"]";
	}
	private void lexemaInvalido(String lexema, int contadorLinha) throws Exception {
		String erro;
		erro = "Simbolo \""+lexema+"\" invalido. Linha "+contadorLinha+".";
		
		this.errosLexicos += erro+"\n";
		
		throw new Exception(erro);
	}
}
