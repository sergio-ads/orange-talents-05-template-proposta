package br.com.zupacademy.proposta.controller;

import java.net.URI;

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
import br.com.zupacademy.proposta.model.Paypal;
import br.com.zupacademy.proposta.model.Viagem;
import br.com.zupacademy.proposta.model.dto.PaypalDto;
import br.com.zupacademy.proposta.model.request.ViagemRequest;
import br.com.zupacademy.proposta.repository.CartaoRepository;
import br.com.zupacademy.proposta.repository.PaypalRepository;
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
    private PaypalRepository paypalRepository;

    @Transactional
    @PostMapping("/{idCartao}/bloquear")
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
    @PostMapping("/{idCartao}/viagem")
    public void avisoViagem(@RequestBody @Valid ViagemRequest viagemRequest, @PathVariable String idCartao, HttpServletRequest request) {
    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	    	
    	Viagem viagem = viagemRequest.toModel(request, cartao);
    	cartao.notificarSistemaViagem(cartaoClient, viagem, viagemRepository);
    }

    @Transactional
    @PostMapping("/{idCartao}/carteira/paypal")
    public ResponseEntity<?> postCarteiraPaypal(@PathVariable String idCartao, UriComponentsBuilder uriBuilder) {
    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));

    	if(cartao.getPaypal() != null)
    		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está associado a essa carteira");
    	
    	Paypal paypal = new Paypal(cartao.getProposta().getEmail(), cartao);
    	cartao.notificarSistemaCarteira(cartaoClient, paypal, paypalRepository, "paypal");
    	
    	URI uri = uriBuilder.path("/{idCartao}/carteira/paypal").buildAndExpand(cartao.getNumero()).toUri();
    	return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{idCartao}/carteira/paypal")
    public PaypalDto getCarteiraPaypal(@PathVariable String idCartao) {    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));

    	return new PaypalDto(cartao.getPaypal());
    }
	
}
