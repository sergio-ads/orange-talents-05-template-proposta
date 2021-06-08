package br.com.zupacademy.proposta.controller;

import br.com.zupacademy.proposta.model.Proposta;
import br.com.zupacademy.proposta.model.dto.PropostaDto;
import br.com.zupacademy.proposta.model.request.PropostaRequest;
import br.com.zupacademy.proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/proposta")
public class PropostaController {
    @Autowired
    private PropostaRepository propostaRepository;

    @GetMapping
    public List<PropostaDto> list() {
        List<Proposta> propostas = propostaRepository.findAll();
        return PropostaDto.converter(propostas);
    }

    @GetMapping("/{id}")
    public PropostaDto get(@PathVariable("id") String id) {
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Proposta não encontrada"));
        return new PropostaDto(proposta);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {
        Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);

        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
