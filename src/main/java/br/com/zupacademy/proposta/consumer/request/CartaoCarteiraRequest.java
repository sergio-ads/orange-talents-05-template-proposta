package br.com.zupacademy.proposta.consumer.request;

import br.com.zupacademy.proposta.model.enums.NomeCarteira;

public class CartaoCarteiraRequest {
	private String email;
	private NomeCarteira carteira;
	
	public CartaoCarteiraRequest(String email, NomeCarteira carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public NomeCarteira getCarteira() {
		return carteira;
	}
	
	
}
