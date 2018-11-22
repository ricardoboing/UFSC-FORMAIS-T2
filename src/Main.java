import lexico.AnalisadorLexico;

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

public class Main {
	public static void main(String[] args) {
		String urlCodigoFonte;
		urlCodigoFonte = "arquivos/programaFonte.fonte";
		
		boolean on;
		on = false;
		
		if (args.length > 0) {
			urlCodigoFonte = args[0];
		}
		if (args.length > 1) {
			if (args[1].equals("on")) {
				on = true;
			}
		}
		
		AnalisadorLexico lex;
		lex = new AnalisadorLexico(urlCodigoFonte);
		
		int contadorErro;
		contadorErro = 0;
		
		while (lex.existeProximoToken()) {
			String token;
			try {
				token = lex.getToken();
				if (on) {
					System.out.println(token);
				}
			} catch (Exception e) {
				contadorErro++;
				System.out.println(e.getMessage());
			}
		}
		
		if (contadorErro == 0) {
			System.out.println("Nenhum erro lexico foi encontrado.");
		} else {
			System.out.println("Foram encontrado(s) "+contadorErro+" lexico(s).");
		}
		
		String logs;
		logs = lex.gerarLogs();
		
		System.out.println("\n"+logs);
	}
}
