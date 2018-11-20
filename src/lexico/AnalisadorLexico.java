package lexico;

import java.util.ArrayList;

import automato.AutomatoFinito;
import automato.OperarAutomato;
import expressao.ExpressaoRegular;
import simbolo.TabelaDeSimbolos;
import util.Arquivo;

public class AnalisadorLexico {
	private TabelaDeTratamentoDeErro tabelaDeTratamentoErro;
	private TabelaDeSimbolos tabelaDeSimbolos;
	private AutomatoFinito automatoUniao;
	
	private String[] buffer;
	private int contadorBuffer;
	private int contadorLinha;
	
	public AnalisadorLexico(String urlCodigoFonte) {
		String programaFonte;
		programaFonte = this.lerProgramaFonte(urlCodigoFonte);
		
		this.buffer = programaFonte.split(" ");
		this.contadorBuffer = 0;
		this.contadorLinha = 1;
		
		this.tabelaDeSimbolos = new TabelaDeSimbolos();
		tabelaDeTratamentoErro = new TabelaDeTratamentoDeErro(urlCodigoFonte);
		
		this.loadTabelaDeSimbolos();
		this.gerarAutomato();
	}
	
	/* Realiza a leitura do arquivo e, para cada simbolo reservado
	 * eh add um espaco em branco antes e depois do simbolo
	 */
	private String lerProgramaFonte(String urlCodigoFonte) {
		String[] simbolosReservados;
		simbolosReservados = new String[] {
			"<=", "==", "!=", ">=", "&&", "\\|\\|", "\n",
			"\\(", "\\)", "\\[", "\\]", "\\{", "\\}", ";",
			"=", "\\*", "\\+", "-", "/", "<", ">", "!"
		};
		
		// Le o arquivo codigo fonte
		String programaFonte;
		programaFonte = Arquivo.ler(urlCodigoFonte);
		
		/* Para cada simbolo reservado eh feito a substituicao do simbolo
		 * por ele mesmo, porem com um espaço em branco antes e depois
		 */
		for (int c = 0; c < simbolosReservados.length; c++) {
			String simboloReservado;
			simboloReservado = simbolosReservados[c];
			
			programaFonte = programaFonte.replaceAll(simboloReservado, " "+simboloReservado+" ");
		}
		
		return programaFonte;
	}
	/* Add todos os simbolos e palavras reservadas na tabela
	 * de simbolos
	 */
	private void loadTabelaDeSimbolos() {
		this.tabelaDeSimbolos.addSimbolo("basic", "basic", "-");
		this.tabelaDeSimbolos.addSimbolo("while", "while", "-");
		this.tabelaDeSimbolos.addSimbolo("break", "break", "-");
		this.tabelaDeSimbolos.addSimbolo("false", "false", "-");
		this.tabelaDeSimbolos.addSimbolo("true", "true", "-");
		this.tabelaDeSimbolos.addSimbolo("else", "else", "-");
		this.tabelaDeSimbolos.addSimbolo("then", "then", "-");
		this.tabelaDeSimbolos.addSimbolo("real", "real", "-");
		this.tabelaDeSimbolos.addSimbolo("do", "do", "-");
		this.tabelaDeSimbolos.addSimbolo("if", "if", "-");
		
		this.tabelaDeSimbolos.addSimbolo("<", "relop", "LT");
		this.tabelaDeSimbolos.addSimbolo(">", "relop", "GT");
		this.tabelaDeSimbolos.addSimbolo("==", "relop", "EQ");
		this.tabelaDeSimbolos.addSimbolo("!=", "relop", "NE");
		this.tabelaDeSimbolos.addSimbolo("<=", "relop", "LE");
		this.tabelaDeSimbolos.addSimbolo(">=", "relop", "GE");
		
		this.tabelaDeSimbolos.addSimbolo("!", "not", "-");
		this.tabelaDeSimbolos.addSimbolo("=", "assign", "-");
		this.tabelaDeSimbolos.addSimbolo("*", "op", "MULT");
		this.tabelaDeSimbolos.addSimbolo("+", "op", "ADD");
		this.tabelaDeSimbolos.addSimbolo("-", "op", "SUB");
		this.tabelaDeSimbolos.addSimbolo("/", "op", "DIV");
		
		this.tabelaDeSimbolos.addSimbolo("||", "cond", "OR");
		this.tabelaDeSimbolos.addSimbolo("&&", "cond", "AND");
		this.tabelaDeSimbolos.addSimbolo(";", "semicolon", "-");
		this.tabelaDeSimbolos.addSimbolo("{", "key", "OPEN");
		this.tabelaDeSimbolos.addSimbolo("}", "key", "CLOSE");
		this.tabelaDeSimbolos.addSimbolo("(", "parentheses", "OPEN");
		this.tabelaDeSimbolos.addSimbolo(")", "parentheses", "CLOSE");
		this.tabelaDeSimbolos.addSimbolo("[", "acess", "OPEN");
		this.tabelaDeSimbolos.addSimbolo("]", "acess", "CLOSE");
	}
	/* Para cada token eh gerado um automato que o reconhece.
	 * Um automato uniao eh criado e determinizado
	 */
	private void gerarAutomato() {
		// Os automatos de cada token sao criados internamente pela classe Token
		Token tokenID, tokenNUM;
		tokenID  = new Token("id",  ExpressaoRegular.LETTER+" ( "+ExpressaoRegular.LETTER+" | "+ExpressaoRegular.DIGIT+" )* ");
		tokenNUM = new Token("num", ExpressaoRegular.DIGIT+ExpressaoRegular.DIGIT+"* ,* "+ExpressaoRegular.DIGIT+"*");
		
		ArrayList<Token> listaDeToken;
		listaDeToken = new ArrayList<Token>();
		listaDeToken.add(tokenID);
		listaDeToken.add(tokenNUM);
		
		// Obtem o primeiro automato e faz a uniao com os demais
		this.automatoUniao = listaDeToken.get(0).getPadrao();
		
		for (int c = 1; c < listaDeToken.size(); c++) {
			AutomatoFinito automatoDoToken;
			automatoDoToken = listaDeToken.get(c).getPadrao();
			
			this.automatoUniao = OperarAutomato.unir(automatoUniao, automatoDoToken);
		}
		
		this.automatoUniao = OperarAutomato.determinizar(automatoUniao);
	}
	
	public String getToken() throws Exception {
		// Nao possui mais simbolos no buffer
		if (!this.existeProximoToken()) {
			return null;
		}
		
		String lexema;
		lexema = this.proximoLexemaBuffer();
		
		// Espaco vazio
		if (lexema.equals("")) {
			return this.getToken();
		}
		// Quebra de linha
		if (lexema.equals("\n")) {
			this.contadorLinha++;
			return this.getToken();
		}
		
		int indiceToken;
		indiceToken = this.tabelaDeSimbolos.getLexemaIndice(lexema);
		
		// Verifica ja existe na tabela
		if (indiceToken >= 0) {
			String token;
			token = this.tabelaDeSimbolos.getToken(indiceToken);
			
			this.tabelaDeSimbolos.addLinhaSimbolo(indiceToken, this.contadorLinha);
			return this.formatarToken(token, indiceToken);
		}
		
		// Tenta reconhecer um lexema
		Token token;
		token = this.automatoUniao.reconhecerLexema(lexema);
		
		// Simbolo invalido
		if (token == null) {
			String erro;
			erro = this.tabelaDeTratamentoErro.erroLexico(lexema, this.contadorLinha);
			
			throw new Exception(erro);
		}
		
		int indice;
		indice = this.tabelaDeSimbolos.addSimbolo(lexema, token.getNome(), token.getValor());
		this.tabelaDeSimbolos.addLinhaSimbolo(indice, this.contadorLinha);
		
		return this.formatarToken(token.getNome(), indice);
	}
	
	private String proximoLexemaBuffer() {
		String lexema;
		lexema = this.buffer[this.contadorBuffer];
		
		this.contadorBuffer++;
		
		return lexema;
	}
	public boolean existeProximoToken() {
		return (this.contadorBuffer < this.buffer.length);
	}
	public String gerarLogs() {
		return this.tabelaDeSimbolos.gerarLog();
	}
	private String formatarToken(String token, int indice) {
		return "["+token+","+indice+"]";
	}
}
