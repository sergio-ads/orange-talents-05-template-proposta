package br.com.zupacademy.proposta.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.proposta.model.Biometria;

public class BiometriaRequest {
	@NotBlank
	private String idCartao;
	@NotNull
	private byte[] fingerprint;	
	
	public BiometriaRequest(@NotBlank String idCartao, @NotNull byte[] fingerprint) {
		this.idCartao = idCartao;
		this.fingerprint = fingerprint;
	}

	public Biometria toModel() {
        return new Biometria(fingerprint);
    }
	
	public String getIdCartao() {
		return idCartao;
	}
	public byte[] getFingerprint() {
		return fingerprint;
	}
	
	
}
