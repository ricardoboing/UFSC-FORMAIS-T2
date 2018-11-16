package analisador;

import automato.AutomatoFinito;
import expressao.ExpressaoRegular;

public class Padrao {
	private AutomatoFinito automato;
	
	public Padrao(String stringExpressao) {
		ExpressaoRegular expressao;
		expressao = new ExpressaoRegular(stringExpressao);
		
		this.automato = new AutomatoFinito("", expressao);
	}
	
	public AutomatoFinito getAutomato() {
		return this.automato;
	}
}
