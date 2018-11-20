package lexico;

import util.Arquivo;

public class TabelaDeTratamentoDeErro {
	private String[] programaFonte;
	private String url;
	
	public TabelaDeTratamentoDeErro(String url) {
		String programaFonte;
		programaFonte = Arquivo.ler(url);
		
		this.programaFonte = programaFonte.split("\n");
		this.url = url;
	}
	
	public String erroLexico(String lexemaInvalido, int linha) {
		String erro;
		erro = this.url+":"+linha+": \"";
		erro += this.programaFonte[linha-1]+"\"\n";
		erro += "erro: simbolo \""+lexemaInvalido+"\" invalido.\n";
		
		return erro;
	}
}
