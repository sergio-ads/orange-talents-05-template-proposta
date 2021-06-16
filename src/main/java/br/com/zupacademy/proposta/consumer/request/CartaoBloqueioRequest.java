package br.com.zupacademy.proposta.consumer.request;

public class CartaoBloqueioRequest {
	private String sistemaResponsavel;

	public CartaoBloqueioRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}
	
}
