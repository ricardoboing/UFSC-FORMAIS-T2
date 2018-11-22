package lexico;

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
