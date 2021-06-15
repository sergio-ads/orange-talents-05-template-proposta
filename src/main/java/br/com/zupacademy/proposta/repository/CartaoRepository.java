package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

}
