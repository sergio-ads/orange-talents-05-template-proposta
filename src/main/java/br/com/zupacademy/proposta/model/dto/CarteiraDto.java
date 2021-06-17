package br.com.zupacademy.proposta.model.dto;

import br.com.zupacademy.proposta.model.Carteira;
import br.com.zupacademy.proposta.model.enums.NomeCarteira;

public class CarteiraDto {
	private String email;
	private NomeCarteira nomeCarteira;
	private CartaoDto cartao;	
	
	public CarteiraDto(Carteira carteira) {
		this.email = carteira.getEmail();
		this.nomeCarteira = carteira.getNomeCarteira();
		this.cartao = new CartaoDto(carteira.getCartao());
	}
	
	public String getEmail() {
		return email;
	}
	public NomeCarteira getNomeCarteira() {
		return nomeCarteira;
	}
	public CartaoDto getCartao() {
		return cartao;
	}	

}
