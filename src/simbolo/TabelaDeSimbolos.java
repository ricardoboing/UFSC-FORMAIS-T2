package simbolo;

import java.util.ArrayList;

import util.Arquivo;

public class TabelaDeSimbolos {
	private ArrayList<LinhaTabela> arrayLinhaTabela;
	
	public TabelaDeSimbolos() {
		this.arrayLinhaTabela = new ArrayList<LinhaTabela>();
	}
	
	public int addSimbolo(String lexema, String token, String valor) {
		this.arrayLinhaTabela.add( new LinhaTabela(lexema, token, valor) );
		
		return this.arrayLinhaTabela.size()-1;
	}
	
	public String getLexema(int indice) {
		return this.arrayLinhaTabela.get(indice).getLexema();
	}
	public String getToken(int indice) {
		return this.arrayLinhaTabela.get(indice).getToken();
	}
	public String getValor(int indice) {
		return this.arrayLinhaTabela.get(indice).getValor();
	}
	
	public boolean possuiLexema(String lexema) {
		if (this.getLexemaIndice(lexema) < 0) {
			return false;
		}
		return true;
	}
	public boolean possuiToken(String token) {
		if (this.getTokenIndice(token) < 0) {
			return false;
		}
		return true;
	}
	
	public int getLexemaIndice(String lexema) {
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			if (linhaTabela.getLexema().equals(lexema)) {
				return c;
			}
		}
		
		return -1;
	}
	public int getTokenIndice(String token) {
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			if (linhaTabela.getToken().equals(token)) {
				return c;
			}
		}
		
		return -2;
	}
	
	
	
	public void escreverTabela() {
		int sizeMaxLexema, sizeMaxToken, sizeMaxValor;
		sizeMaxLexema = 6;
		sizeMaxToken = 5;
		sizeMaxValor = 5;
		
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			String lexema, token, valor;
			lexema = linhaTabela.getLexema();
			token = linhaTabela.getToken();
			valor = linhaTabela.getValor();
			
			if (lexema.length() > sizeMaxLexema) {
				sizeMaxLexema = lexema.length();
			}
			if (token.length() > sizeMaxToken) {
				sizeMaxToken = token.length();
			}
			if (valor.length() > sizeMaxValor) {
				sizeMaxValor = valor.length();
			}
		}
		
		String tabela;
		tabela  = "";
		tabela += this.gerarLinha(-1, "Lexema", "Token", "Valor", sizeMaxLexema, sizeMaxToken, sizeMaxValor);
		
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			String lexema, token, valor;
			lexema = linhaTabela.getLexema();
			token = linhaTabela.getToken();
			valor = linhaTabela.getValor();
			
			tabela += this.gerarLinha(c, lexema, token, valor, sizeMaxLexema, sizeMaxToken, sizeMaxValor);
		}
		
		Arquivo.escrever("tabela.txt", tabela);
	}
	private String gerarLinha(int c, String lexema, String token, String valor, int sizeLexema, int sizeToken, int sizeValor) {
		String espacoVazio;
		espacoVazio = "                                                  ";
		
		String linha;
		linha = "";
		
		if (c >= 0) {
			linha += "| ";
			
			if (c < 10)
				linha += "0";
			if (c < 100)
				linha += "0";
			if (c < 1000)
				linha += "0";
			if (c < 10000)
				linha += "0";
			if (c < 100000)
				linha += "0";
			
			linha += c+" | ";
		} else {
			linha += "| Indice | ";
		}
		
		linha += lexema+(espacoVazio.substring(0, sizeLexema-lexema.length()))+" | ";
		linha += token+(espacoVazio.substring(0, sizeToken-token.length()))+" | ";
		linha += valor+(espacoVazio.substring(0, sizeValor-valor.length()))+" |";
		
		return linha+"\n";
	}
}
