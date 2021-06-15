package br.com.zupacademy.proposta.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.zupacademy.proposta.model.Proposta;
import br.com.zupacademy.proposta.model.enums.ResultadoAvaliacao;
import br.com.zupacademy.proposta.validator.CpfOrCnpj;
import br.com.zupacademy.proposta.validator.NaoExiste;

public class PropostaRequest {
    @NotBlank
    @CpfOrCnpj
    @NaoExiste(domainClass = Proposta.class, fieldName = "cpfOuCnpj")
    private final String cpfOuCnpj;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @NotNull
    @PositiveOrZero
    private final BigDecimal salario;

    public PropostaRequest(@NotBlank String cpfOuCnpj, @NotBlank @Email String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @PositiveOrZero BigDecimal salario) {
		this.cpfOuCnpj = cpfOuCnpj.replaceAll("\\D", "");
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public Proposta toModel() {
        return new Proposta(cpfOuCnpj, email, nome, endereco, salario, ResultadoAvaliacao.NAO_VERIFICADO);
    }

}
