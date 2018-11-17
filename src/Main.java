import lexico.AnalisadorLexico;

public class Main {
	public static void main(String[] args) {
		String urlCodigoFonte;
		urlCodigoFonte = "arquivos/programaFonte.jerusa";
		
		AnalisadorLexico lex;
		lex = new AnalisadorLexico(urlCodigoFonte);
		
		while (lex.proximoToken()) {
			String token;
			try {
				token = lex.getProximoToken();
				System.out.println(token);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		lex.gerarLogs();
	}
}
