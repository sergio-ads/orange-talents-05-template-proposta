package br.com.zupacademy.proposta.consumer.request;

public class CartaoCarteiraRequest {
	private String email;
	private String carteira;
	
	public CartaoCarteiraRequest(String email, String carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}
	
	
}
