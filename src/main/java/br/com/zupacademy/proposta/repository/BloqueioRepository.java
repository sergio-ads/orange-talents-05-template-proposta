package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Bloqueio;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

}
