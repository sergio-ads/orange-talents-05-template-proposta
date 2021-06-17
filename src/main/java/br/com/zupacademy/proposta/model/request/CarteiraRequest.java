package br.com.zupacademy.proposta.model.request;

import javax.validation.constraints.NotBlank;

import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.model.Carteira;
import br.com.zupacademy.proposta.model.enums.NomeCarteira;
import br.com.zupacademy.proposta.validator.EnumString;

public class CarteiraRequest {
	@NotBlank
	private String idCartao;
	@EnumString(domainClass = NomeCarteira.class, fieldName = "nomeCarteira")
	private String nomeCarteira;
	
	public CarteiraRequest(@NotBlank String idCartao, String nomeCarteira) {
		this.idCartao = idCartao;
		this.nomeCarteira = nomeCarteira;
	}
	
	public String getIdCartao() {
		return idCartao;
	}
	public String getNomeCarteira() {
		return nomeCarteira;
	}
	
	public Carteira toModel(String email, Cartao cartao) {
        return new Carteira(email, cartao, NomeCarteira.valueOf(nomeCarteira));
    }
	
}
