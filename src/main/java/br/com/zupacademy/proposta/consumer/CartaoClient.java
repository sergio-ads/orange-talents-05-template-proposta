package br.com.zupacademy.proposta.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zupacademy.proposta.consumer.request.CartaoBloqueioRequest;
import br.com.zupacademy.proposta.consumer.request.CartaoViagemRequest;
import br.com.zupacademy.proposta.consumer.response.CartaoBloqueioResponse;
import br.com.zupacademy.proposta.consumer.response.CartaoResponse;

@FeignClient(value = "apiCartao", url = "${apiCartao.host}")
public interface CartaoClient {

	@GetMapping
	CartaoResponse getCartao(@RequestParam String idProposta);

	@PostMapping("/{idCartao}/bloqueios")
	CartaoBloqueioResponse bloquear(@PathVariable String idCartao, @RequestBody CartaoBloqueioRequest cartaoBloqueioRequest);
	
	@PostMapping("/{idCartao}/avisos")
	void viagem(@PathVariable String idCartao, @RequestBody CartaoViagemRequest cartaoViagemRequest);

}
