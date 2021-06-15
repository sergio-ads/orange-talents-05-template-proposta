package br.com.zupacademy.proposta.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.zupacademy.proposta.model.Biometria;

public class BiometriaDto {
	private byte[] fingerprint;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = Shape.STRING)
	private LocalDateTime horarioCriacao;
	
	public BiometriaDto(Biometria biometria) {
		this.fingerprint = biometria.getFingerprint();
		this.horarioCriacao = biometria.getHorarioCriacao();
	}
	
	public byte[] getFingerprint() {
		return fingerprint;
	}
	
	public LocalDateTime getHorarioCriacao() {
		return horarioCriacao;
	}
	
}
