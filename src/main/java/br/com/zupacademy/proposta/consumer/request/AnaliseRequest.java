package br.com.zupacademy.proposta.consumer.request;

import br.com.zupacademy.proposta.model.Proposta;

public class AnaliseRequest {

	private String documento;
	private String nome;
	private String idProposta;
	
	public AnaliseRequest(Proposta proposta) {
		this.documento = proposta.getCpfOuCnpj();
		this.nome = proposta.getNome();
		this.idProposta = proposta.getId();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public String getIdProposta() {
		return idProposta;
	}

}
