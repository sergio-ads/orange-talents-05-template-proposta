package br.com.zupacademy.proposta.model;

import br.com.zupacademy.proposta.validator.CpfOrCnpj;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @NotBlank
    @CpfOrCnpj
    private String cpfOuCnpj;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String cpfOuCnpj, String email, String nome, String endereco, BigDecimal salario) {
        this.cpfOuCnpj = cpfOuCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
