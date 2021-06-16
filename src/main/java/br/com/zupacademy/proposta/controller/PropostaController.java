package br.com.zupacademy.proposta.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zupacademy.proposta.consumer.AnaliseClient;
import br.com.zupacademy.proposta.model.Proposta;
import br.com.zupacademy.proposta.model.dto.PropostaDto;
import br.com.zupacademy.proposta.model.request.PropostaRequest;
import br.com.zupacademy.proposta.repository.PropostaRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

@RestController
@RequestMapping(value = "/proposta")
public class PropostaController {
    private PropostaRepository propostaRepository;
    private AnaliseClient analiseClient;
	private MeterRegistry meterRegistry;

	public PropostaController(PropostaRepository propostaRepository, AnaliseClient analiseClient,
			MeterRegistry meterRegistry) {
		this.propostaRepository = propostaRepository;
		this.analiseClient = analiseClient;
		this.meterRegistry = meterRegistry;
	}

	@GetMapping
    public List<PropostaDto> list() {
    	return listWithMetrics();
    }
	
	private List<PropostaDto> listWithMetrics() {
    	Collection<Tag> tags = new ArrayList<>();
    	tags.add(Tag.of("emissora", "Mastercard"));
    	tags.add(Tag.of("banco", "Itaú"));

    	Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
    	return timerConsultarProposta.record(() -> {
    	    // Método do controller
    		List<Proposta> propostas = propostaRepository.findAll();
    		return PropostaDto.converter(propostas);
    		// Fim método do controller
    	});
	}

    @GetMapping("/{id}")
    public PropostaDto get(@PathVariable("id") String id) {
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"Proposta não encontrada"));
        return new PropostaDto(proposta);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {
    	return saveWithMetrics(propostaRequest, uriBuilder);
    }
    
    private ResponseEntity<?> saveWithMetrics(PropostaRequest propostaRequest, UriComponentsBuilder uriBuilder) {
	    Collection<Tag> tags = new ArrayList<>();
	    tags.add(Tag.of("emissora", "Mastercard"));
	    tags.add(Tag.of("banco", "Itaú"));

	    Counter contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
	    
	    // Método do controller
	    Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);        
        proposta.buscarResultadoAvaliacao(analiseClient);
        
        URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        ResponseEntity<?> buildReturn = ResponseEntity.created(uri).build();
        // Fim método do controller
	    
	    contadorDePropostasCriadas.increment();
	    return buildReturn;
	}

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") String id) {
    	Assert.state(propostaRepository.findById(id).isPresent(), "Proposta não localizada");
        propostaRepository.deleteById(id);
    }

}
