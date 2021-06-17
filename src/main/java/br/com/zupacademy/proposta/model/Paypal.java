package br.com.zupacademy.proposta.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Paypal {
	@Id @GeneratedValue
	private Long id;
	@NotBlank
	private String email;
	@OneToOne(cascade = CascadeType.MERGE)
	private Cartao cartao;
	
	@Deprecated
	public Paypal() { }
	
	public Paypal(@NotBlank String email, Cartao cartao) {
		this.email = email;
		this.cartao = cartao;
	}

	public String getEmail() {
		return email;
	}

	public Cartao getCartao() {
		return cartao;
	}
	
}
