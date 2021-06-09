package br.com.zupacademy.proposta.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zupacademy.proposta.consumer.response.CartaoResponse;

@FeignClient(value = "apiCartao", url = "${apiCartao.host}")
public interface CartaoClient {

	@GetMapping
	CartaoResponse getCartao(@RequestParam String idProposta);

//	@PostMapping
//	AnaliseResponse solicitar(@RequestBody AnaliseRequest analiseRequest);

}
