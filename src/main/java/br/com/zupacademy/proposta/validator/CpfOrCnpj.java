package br.com.zupacademy.proposta.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {CpfOrCnpjValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfOrCnpj {

    String message() default "Não é um CPF ou CNPJ válido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
