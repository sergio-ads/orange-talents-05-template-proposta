package br.com.zupacademy.proposta.model.dto;

import br.com.zupacademy.proposta.model.Paypal;

public class PaypalDto {
	private String email;
	private CartaoDto cartao;
	
	@Deprecated
	public PaypalDto() { }
	
	public PaypalDto(Paypal paypal) {
		this.email = paypal.getEmail();
		this.cartao = new CartaoDto(paypal.getCartao());
	}

	public String getEmail() {
		return email;
	}

	public CartaoDto getCartao() {
		return cartao;
	}
	
}
