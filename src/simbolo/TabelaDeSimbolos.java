package simbolo;

import java.util.ArrayList;

import util.Arquivo;

/*
 *  Trabalho II: Analisador Lexico
 *  
 *  Departamento de Informatica e Estatistica – Universidade Federal de Santa Catarina (UFSC)
 *  Campus Reitor Joao David Ferreira Lima, 88.040-900 – Florianopolis – SC – Brasil
 *  
 *  Alceu Ramos Conceição Junior
 *  Bruno Gilmar Honnef
 *  Pedro Alexandre Barradas da Corte
 *  Ricardo do Nascimento Boing
 *  
 *  21 de novembro de 2018
 */

public class TabelaDeSimbolos {
	private ArrayList<LinhaTabela> arrayLinhaTabela;
	
	public TabelaDeSimbolos() {
		this.arrayLinhaTabela = new ArrayList<LinhaTabela>();
	}
	
	public int addSimbolo(String lexema, String token, String valor) {
		this.arrayLinhaTabela.add( new LinhaTabela(lexema, token, valor) );
		return this.arrayLinhaTabela.size()-1;
	}
	public void addLinhaSimbolo(int indice, int linha) {
		this.arrayLinhaTabela.get(indice).addLinha(linha);
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
	
	
	// Metodo pra desenhar bonitinha a tabela
	public String gerarLog() {
		int sizeMaxLexema, sizeMaxToken, sizeMaxValor, sizeIndiceLinha;
		sizeMaxLexema = 6;
		sizeMaxToken = 5;
		sizeMaxValor = 5;
		sizeIndiceLinha = 5;
		
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			String lexema, token, valor, indiceLinha;
			lexema = linhaTabela.getLexema();
			token = linhaTabela.getToken();
			valor = linhaTabela.getValor();
			indiceLinha = linhaTabela.getIndicesLinhaToString();
			
			if (lexema.length() > sizeMaxLexema) {
				sizeMaxLexema = lexema.length();
			}
			if (token.length() > sizeMaxToken) {
				sizeMaxToken = token.length();
			}
			if (valor.length() > sizeMaxValor) {
				sizeMaxValor = valor.length();
			}
			if (indiceLinha.length() > sizeIndiceLinha) {
				sizeIndiceLinha = indiceLinha.length();
			}
		}
		
		String tabela;
		tabela  = "";
		tabela += this.gerarLogLinha(-1, "Lexema", "Token", "Valor", "Linha", sizeMaxLexema, sizeMaxToken, sizeMaxValor, sizeIndiceLinha);
		
		for (int c = 0; c < this.arrayLinhaTabela.size(); c++) {
			LinhaTabela linhaTabela;
			linhaTabela = this.arrayLinhaTabela.get(c);
			
			String lexema, token, valor, indiceLinha;
			lexema = linhaTabela.getLexema();
			token = linhaTabela.getToken();
			valor = linhaTabela.getValor();
			indiceLinha = linhaTabela.getIndicesLinhaToString();
			
			tabela += this.gerarLogLinha(c, lexema, token, valor, indiceLinha, sizeMaxLexema, sizeMaxToken, sizeMaxValor, sizeIndiceLinha);
		}
		
		Arquivo.escrever("tabela.txt", tabela);
		
		return tabela;
	}
	// Metodo pra desenhar bonitinha uma linha da tabela
	private String gerarLogLinha(int c, String lexema, String token, String valor, String indiceLinha, int sizeLexema, int sizeToken, int sizeValor, int sizeIndiceLinha) {
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
		linha += valor+(espacoVazio.substring(0, sizeValor-valor.length()))+" | ";
		linha += indiceLinha+(espacoVazio.substring(0, sizeIndiceLinha-indiceLinha.length()))+" |";
		
		
		return linha+"\n";
	}
}
