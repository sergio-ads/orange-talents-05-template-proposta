package br.com.zupacademy.proposta.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.zupacademy.proposta.consumer.request.AnaliseRequest;
import br.com.zupacademy.proposta.consumer.response.AnaliseResponse;

@FeignClient(value = "apiAnalise", url = "${apiAvaliacao.host}")
public interface AnaliseClient {

	@PostMapping
	AnaliseResponse solicitar(@RequestBody AnaliseRequest analiseRequest);
	
}
