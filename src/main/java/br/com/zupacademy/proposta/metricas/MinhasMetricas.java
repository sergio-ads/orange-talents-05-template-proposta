package br.com.zupacademy.proposta.metricas;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zupacademy.proposta.model.Proposta;
import br.com.zupacademy.proposta.repository.PropostaRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

@Component
public class MinhasMetricas {

	private final PropostaRepository propostaRepository;	
	private final MeterRegistry meterRegistry;
    private final Collection<Proposta> propostas = new ArrayList<>();

    public MinhasMetricas(MeterRegistry meterRegistry, PropostaRepository propostaRepository) {
        this.meterRegistry = meterRegistry;
        this.propostaRepository = propostaRepository;
        criarGaugePropostas();
    }

    public void criarGaugePropostas() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        this.meterRegistry.gauge("gauge_propostas", tags, propostas, Collection::size);
    }

    @Scheduled(fixedDelay = 1000)
    public void atualizarGaugePropostas() {
        propostas.clear();
        propostas.addAll(propostaRepository.findAll());
    }

}
