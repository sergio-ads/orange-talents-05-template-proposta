package br.com.zupacademy.proposta.scheduler;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.zupacademy.proposta.consumer.CartaoClient;
import br.com.zupacademy.proposta.consumer.response.CartaoResponse;
import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.model.Proposta;
import br.com.zupacademy.proposta.repository.PropostaRepository;
import feign.FeignException;

@Configuration
@EnableAsync
@EnableScheduling
public class CartaoScheduler {
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoClient cartaoClient;
	
    
	@Scheduled(fixedDelay=60000)
	@Transactional
	public void buscarSalvarCartoes() {		
		Set<Proposta> propostas = propostaRepository.findByCartao(null);
		
		for (Proposta proposta : propostas) {			
			try {
				if(proposta.getCartao() != null) 
					continue;
				
				CartaoResponse cartaoResponse = cartaoClient.getCartao(proposta.getId());
				Cartao cartao = cartaoResponse.toModel();
				proposta.setCartao(cartao);
				propostaRepository.save(proposta);
			} catch (FeignException e) { }
		}		
		
	}

}
