import lexico.AnalisadorLexico;

public class Main {
	public static void main(String[] args) {
		String urlCodigoFonte;
		urlCodigoFonte = "arquivos/programaFonte.jerusa";
		
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
