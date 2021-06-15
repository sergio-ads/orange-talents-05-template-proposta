package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Biometria;

public interface BiometriaRepository extends JpaRepository<Biometria, String> {

}
