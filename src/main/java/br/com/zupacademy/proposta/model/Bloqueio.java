package br.com.zupacademy.proposta.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Bloqueio {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    private LocalDate horario;
    @NotBlank
    private String ip;
    @NotBlank
    private String userAgent;
    
    @Deprecated
    public Bloqueio() { }
    
	public Bloqueio(HttpServletRequest request) {
		this.ip = request.getRemoteAddr();
		this.userAgent = request.getHeader("User-Agent");
	}

	public LocalDate getHorario() {
		return horario;
	}

	public String getIp() {
		return ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

}
