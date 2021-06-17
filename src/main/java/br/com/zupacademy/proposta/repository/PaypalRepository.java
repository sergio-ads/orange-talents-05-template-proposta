package br.com.zupacademy.proposta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zupacademy.proposta.model.Paypal;

public interface PaypalRepository extends JpaRepository<Paypal, Long> {

}
