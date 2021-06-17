package br.com.zupacademy.proposta.controller;

import java.net.URI;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.proposta.consumer.CartaoClient;
import br.com.zupacademy.proposta.model.Bloqueio;
import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.model.Carteira;
import br.com.zupacademy.proposta.model.Viagem;
import br.com.zupacademy.proposta.model.dto.CarteiraDto;
import br.com.zupacademy.proposta.model.request.CarteiraRequest;
import br.com.zupacademy.proposta.model.request.ViagemRequest;
import br.com.zupacademy.proposta.repository.CartaoRepository;
import br.com.zupacademy.proposta.repository.CarteiraRepository;
import br.com.zupacademy.proposta.repository.ViagemRepository;

@RestController
@RequestMapping(value = "/cartao")
public class CartaoController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ViagemRepository viagemRepository;
    @Autowired
    private CartaoClient cartaoClient;
    @Autowired
    private CarteiraRepository carteiraRepository;

    @Transactional
    @PostMapping("/bloquear/{idCartao}")
    public void bloquear(@PathVariable String idCartao, HttpServletRequest request) {
    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	
    	if(cartao.getBloqueio() != null)
    		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
    	
    	Bloqueio bloqueio = new Bloqueio(request);
    	cartao.adicionaBloqueio(bloqueio, cartaoRepository);    	
    	cartao.notificarSistemaBloqueio(cartaoClient, cartaoRepository);
    }

    @Transactional
    @PostMapping("/viagem/{idCartao}")
    public void avisoViagem(@RequestBody @Valid ViagemRequest viagemRequest, @PathVariable String idCartao, HttpServletRequest request) {
    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	    	
    	Viagem viagem = viagemRequest.toModel(request, cartao);
    	cartao.notificarSistemaViagem(cartaoClient, viagem, viagemRepository);
    }

    @Transactional
    @PostMapping("/carteira")
    public ResponseEntity<?> postCarteira(@Valid CarteiraRequest carteiraRequest,  UriComponentsBuilder uriBuilder) {    	
    	Cartao cartao = cartaoRepository.findById(carteiraRequest.getIdCartao())
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	
    	if(cartao.getCarteiras().stream().anyMatch(carteira -> carteira.getNomeCarteira().name().equals(carteiraRequest.getNomeCarteira())))
    		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está associado a essa carteira");

		Carteira carteira = carteiraRequest.toModel(cartao.getProposta().getEmail(), cartao);
		cartao.notificarSistemaCarteira(cartaoClient, carteira, carteiraRepository);
    	
    	URI uri = uriBuilder.path("/carteira/{idCartao}/{nomeCarteira}").buildAndExpand(cartao.getNumero(), carteiraRequest.getNomeCarteira()).toUri();
    	return ResponseEntity.created(uri).build();
    }

    @GetMapping("/carteira/{idCartao}/{nomeCarteira}")
    public CarteiraDto getCarteira(@PathVariable String idCartao, @PathVariable String nomeCarteira) {    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	
    	Optional<Carteira> provavelCarteira = cartao.getCarteiras()
    			.stream().filter(carteira -> carteira.getNomeCarteira().name().equals(nomeCarteira)).findFirst();
    	
    	if(!provavelCarteira.isPresent())
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Carteira não encontrada");
    		
    	return new CarteiraDto(provavelCarteira.get());   	
    }
	
}
