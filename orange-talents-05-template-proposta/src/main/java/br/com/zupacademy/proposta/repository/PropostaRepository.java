package br.com.zupacademy.proposta.repository;

import br.com.zupacademy.proposta.model.Cartao;
import br.com.zupacademy.proposta.model.Proposta;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, String> {

	void deleteByCpfOuCnpj(String cpfOuCnpj);

	Optional<Proposta> findByCpfOuCnpj(String cpfOuCnpj);

	Set<Proposta> findByCartao(Cartao cartao);

}
