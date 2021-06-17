package br.com.zupacademy.proposta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.proposta.consumer.CartaoClient;
import br.com.zupacademy.proposta.consumer.request.CartaoBloqueioRequest;
import br.com.zupacademy.proposta.consumer.request.CartaoCarteiraRequest;
import br.com.zupacademy.proposta.consumer.request.CartaoViagemRequest;
import br.com.zupacademy.proposta.model.enums.StatusBloqueioCartao;
import br.com.zupacademy.proposta.repository.BiometriaRepository;
import br.com.zupacademy.proposta.repository.CartaoRepository;
import br.com.zupacademy.proposta.repository.CarteiraRepository;
import br.com.zupacademy.proposta.repository.ViagemRepository;

@Entity
public class Cartao {

	@Id
	@NotBlank
	private String numero;
	@NotNull
	private Long limite;

	@OneToMany(cascade = CascadeType.MERGE)
	private List<Biometria> biometrias = new ArrayList<>();
	@OneToOne(cascade = CascadeType.MERGE)
	private Bloqueio bloqueio = null;
	@Enumerated(EnumType.STRING)
	private StatusBloqueioCartao bloqueado = StatusBloqueioCartao.ATIVO;
	@OneToMany(mappedBy = "cartao")
	private List<Carteira> carteiras = new ArrayList<>();
	@OneToOne(mappedBy = "cartao")
    private Proposta proposta;

	@Deprecated
	public Cartao() { }

	public Cartao(@NotBlank String numero, @NotNull Long limite) {
		this.numero = numero;
		this.limite = limite;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public String getNumero() {
		return numero;
	}

	public Long getLimite() {
		return limite;
	}

	public List<Biometria> getBiometrias() {
		return biometrias;
	}

	public Bloqueio getBloqueio() {
		return bloqueio;
	}

	public StatusBloqueioCartao getBloqueado() {
		return bloqueado;
	}

	public List<Carteira> getCarteiras() {
		return carteiras;
	}

	public void adicionaBiometria(Biometria biometria, BiometriaRepository biometriaRepository) {
		biometriaRepository.save(biometria);
		this.biometrias.add(biometria);
	}

	public void adicionaBloqueio(Bloqueio bloqueio, CartaoRepository cartaoRepository) {
		this.bloqueio = bloqueio;
		cartaoRepository.save(this);
	}

//	public void removeBloqueio(BloqueioRepository bloqueioRepository) {
//		bloqueioRepository.delete(this.bloqueio);
//		this.bloqueio = null;
//	}

	public void notificarSistemaBloqueio(CartaoClient cartaoClient, CartaoRepository cartaoRepository) {
		StatusBloqueioCartao status = cartaoClient.bloquear(this.numero, new CartaoBloqueioRequest("Propostas_API")).getResultado();		
		if(status.equals(StatusBloqueioCartao.BLOQUEADO)) {
			this.bloqueado = StatusBloqueioCartao.BLOQUEADO;
			cartaoRepository.save(this);			
		}
	}	

	public void notificarSistemaViagem(CartaoClient cartaoClient, Viagem viagem, ViagemRepository viagemRepository) {
		cartaoClient.viagem(numero, new CartaoViagemRequest(viagem.getDestino(), viagem.getDataTermino()));
    	viagemRepository.save(viagem);
	}

	public void notificarSistemaCarteira(CartaoClient cartaoClient, Carteira carteira, CarteiraRepository carteiraRepository) {
		cartaoClient.carteira(this.numero, new CartaoCarteiraRequest(carteira.getEmail(), carteira.getNomeCarteira()));
		carteiraRepository.save(carteira);
	}	

}
