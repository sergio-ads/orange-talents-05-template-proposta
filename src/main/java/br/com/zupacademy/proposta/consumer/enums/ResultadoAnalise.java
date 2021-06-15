package br.com.zupacademy.proposta.consumer.enums;

import br.com.zupacademy.proposta.model.enums.ResultadoAvaliacao;

public enum ResultadoAnalise {

	COM_RESTRICAO {
		@Override
		public ResultadoAvaliacao converte() {
			return ResultadoAvaliacao.NAO_ELEGIVEL;
		}
	}, SEM_RESTRICAO {
		@Override
		public ResultadoAvaliacao converte() {
			return ResultadoAvaliacao.ELEGIVEL;
		}
	};
	
	public abstract ResultadoAvaliacao converte();
}
