package simbolo;

class LinhaTabela {
	private String lexema;
	private String token;
	private String valor;
	
	public LinhaTabela(String lexema, String token, String valor) {
		this.lexema = lexema;
		this.token = token;
		this.valor = valor;
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
}
