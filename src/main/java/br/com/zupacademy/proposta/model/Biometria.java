package br.com.zupacademy.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import br.com.zupacademy.proposta.compartilhado.MinhaCriptografiaSimetrica;

@Entity
public class Biometria {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
	private String id;
	@NotNull
	@Lob
	private byte[] fingerprint;
	@CreationTimestamp
	private LocalDateTime horarioCriacao;
	
	@Deprecated
	public Biometria() { }
	
	public Biometria(@NotNull byte[] fingerprint) {
		// Criptografa fingerprint
		this.fingerprint = MinhaCriptografiaSimetrica.encript(fingerprint);
	}

	public String getId() {
		return id;
	}

	public byte[] getFingerprint() {
		// Descriptografa fingerprint
		return MinhaCriptografiaSimetrica.decript(fingerprint);
	}

	public LocalDateTime getHorarioCriacao() {
		return horarioCriacao;
	}

}
