package br.com.zupacademy.proposta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.proposta.model.enums.NomeCarteira;

@Entity
public class Carteira {
	@Id @GeneratedValue
	private Long id;
	@NotBlank
	private String email;
	@NotNull
	@Enumerated(EnumType.STRING)
	private NomeCarteira nomeCarteira;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Cartao cartao;
	
	@Deprecated
	public Carteira() { }
	
	public Carteira(@NotBlank String email, Cartao cartao, @NotNull NomeCarteira nomeCarteira) {
		this.email = email;
		this.cartao = cartao;
		this.nomeCarteira = nomeCarteira;
	}

	public String getEmail() {
		return email;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public NomeCarteira getNomeCarteira() {
		return nomeCarteira;
	}

	
}
