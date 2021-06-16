package br.com.zupacademy.proposta.consumer.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CartaoViagemRequest {
	private String destino;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate validoAte;
	
	public CartaoViagemRequest(String destino, LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}
	
	public String getDestino() {
		return destino;
	}
	public LocalDate getValidoAte() {
		return validoAte;
	}

}
