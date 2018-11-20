package simbolo;

import java.util.ArrayList;

class LinhaTabela {
	private String lexema;
	private String token;
	private String valor;
	
	private ArrayList<Integer> indiceLinha;
	
	public LinhaTabela(String lexema, String token, String valor) {
		this.lexema = lexema;
		this.token = token;
		this.valor = valor;
		
		this.indiceLinha = new ArrayList<Integer>();
	}
	
	public void addLinha(int indice) {
		this.indiceLinha.add(indice);
	}
	public String getLexema() {
		return lexema;
	}
	public String getToken() {
		return token;
	}
	public String getValor() {
		return valor;
	}
	public ArrayList<Integer> getIndicesLinha() {
		return this.indiceLinha;
	}
	public String getIndicesLinhaToString() {
		String retorno;
		retorno = "";
		
		for (int c = 0; c < this.indiceLinha.size(); c++) {
			int indice;
			indice = this.indiceLinha.get(c);
			
			if (c != 0) {
				retorno += ", ";
			}
			
			retorno += indice;
		}
		
		return retorno;
	}
}
