package br.com.zupacademy.proposta.consumer.response;

import br.com.zupacademy.proposta.model.enums.StatusBloqueioCartao;

public class CartaoBloqueioResponse {
	private StatusBloqueioCartao resultado;

	@Deprecated
	public CartaoBloqueioResponse() { }
	
	public CartaoBloqueioResponse(String resultado) {
		this.resultado = StatusBloqueioCartao.valueOf(resultado);
	}

	public StatusBloqueioCartao getResultado() {
		return resultado;
	}
	
}
