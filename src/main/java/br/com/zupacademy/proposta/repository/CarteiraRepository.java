package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Carteira;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

}
