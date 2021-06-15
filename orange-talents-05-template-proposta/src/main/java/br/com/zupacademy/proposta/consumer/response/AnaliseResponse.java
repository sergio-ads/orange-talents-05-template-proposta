package br.com.zupacademy.proposta.consumer.response;

import br.com.zupacademy.proposta.consumer.enums.ResultadoAnalise;
import br.com.zupacademy.proposta.model.enums.ResultadoAvaliacao;

public class AnaliseResponse {

	private String documento;
	private String nome;
	private ResultadoAnalise resultadoSolicitacao;
	private String idProposta;
	
	public AnaliseResponse(String documento, String nome, String resultadoSolicitacao,
			String idProposta) {
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = ResultadoAnalise.valueOf(resultadoSolicitacao);
		this.idProposta = idProposta;
	}
	
	
	public String getDocumento() {
		return documento;
	}
	public String getNome() {
		return nome;
	}
	public ResultadoAvaliacao getResultadoSolicitacao() {
		return resultadoSolicitacao.converte();
	}
	public String getIdProposta() {
		return idProposta;
	}
	
}
