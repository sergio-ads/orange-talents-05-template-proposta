package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Viagem;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

}
