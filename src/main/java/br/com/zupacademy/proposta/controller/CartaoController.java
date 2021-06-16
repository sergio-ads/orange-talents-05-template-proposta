package br.com.zupacademy.proposta.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.proposta.consumer.CartaoClient;
import br.com.zupacademy.proposta.model.Bloqueio;
import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.repository.CartaoRepository;

@RestController
@RequestMapping(value = "/cartao")
public class CartaoController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @Transactional
    @PostMapping("/{idCartao}/bloquear")
    public void bloquear(@PathVariable String idCartao, HttpServletRequest request) {
    	
    	Cartao cartao = cartaoRepository.findById(idCartao)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Cartão não encontrado"));
    	
    	if(cartao.getBloqueio() != null)
    		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já está bloqueado");
    	
    	Bloqueio bloqueio = new Bloqueio(request);
    	cartao.adicionaBloqueio(bloqueio, cartaoRepository);    	
    	cartao.notificarSistemaLegado(cartaoClient, cartaoRepository);
    }
	
}
