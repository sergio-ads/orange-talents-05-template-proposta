package br.com.zupacademy.proposta.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Viagem {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String destino;
	@NotNull @Future
	private LocalDate dataTermino;
	@CreationTimestamp
	private LocalDateTime dataAviso;
	private String ip;
	private String userAgent;
	@ManyToOne
	private Cartao cartao;
	
	@Deprecated
	public Viagem() { }
	
	public Viagem(@NotBlank String destino, @NotNull @Future LocalDate dataTermino, String ip, String userAgent,
			Cartao cartao) {
		this.destino = destino;
		this.dataTermino = dataTermino;
		this.ip = ip;
		this.userAgent = userAgent;
		this.cartao = cartao;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public LocalDateTime getDataAviso() {
		return dataAviso;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public Cartao getCartao() {
		return cartao;
	}
	
}
