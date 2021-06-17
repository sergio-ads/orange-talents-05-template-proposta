package br.com.zupacademy.proposta.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumStringValidator implements ConstraintValidator<EnumString, String> {

    private String domainAttribute;
	private Class<? extends Enum<?>> klass;
	
	@Override
	public void initialize(EnumString params) {
        domainAttribute = params.fieldName();
		klass = params.domainClass();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<String> enumValues = Stream.of(klass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
		
		context.disableDefaultConstraintViolation();
		if(!enumValues.contains(value)) {
			context.buildConstraintViolationWithTemplate("Campo " +domainAttribute +" com valor inválido, deve ser um dos seguintes: " +enumValues).addConstraintViolation();
			return false;
		}
		return true;
	}

}
