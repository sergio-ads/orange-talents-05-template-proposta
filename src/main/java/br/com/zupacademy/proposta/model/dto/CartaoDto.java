package br.com.zupacademy.proposta.model.dto;

import br.com.zupacademy.proposta.model.Cartao;

public class CartaoDto {
	private String numero;
	private Long limite;

	public CartaoDto(Cartao cartao) {
		this.numero = cartao.getNumero();
		this.limite = cartao.getLimite();
	}

	@Deprecated
	public CartaoDto() { }

	public String getNumero() {
		return numero;
	}

	public Long getLimite() {
		return limite;
	}
}
