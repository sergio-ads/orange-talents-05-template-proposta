package br.com.zupacademy.proposta.model.request;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.model.Viagem;

public class ViagemRequest {
	@NotBlank
	private String destino;
	@NotNull @Future 
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private LocalDate dataTermino;
	
	@Deprecated
	public ViagemRequest() { }
	
	public ViagemRequest(@NotBlank String destino, @NotNull @Future LocalDate dataTermino) {
		this.destino = destino;
		this.dataTermino = dataTermino;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public Viagem toModel(HttpServletRequest request, Cartao cartao) {
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("User-Agent");
        return new Viagem(destino, dataTermino, ip, userAgent, cartao);
    }

}
