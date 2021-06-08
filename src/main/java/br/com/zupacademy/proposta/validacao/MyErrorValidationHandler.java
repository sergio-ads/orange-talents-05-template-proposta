package br.com.zupacademy.proposta.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class MyErrorValidationHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ValidationErrorsDto> handle(ResponseStatusException exception) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto();
        validationErrorsDto.addGlobalError(exception.getReason());

        return ResponseEntity.status(exception.getStatus()).body(validationErrorsDto);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ValidationErrorsDto handle(IllegalStateException exception) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto();
        validationErrorsDto.addGlobalError(exception.getLocalizedMessage());

        return validationErrorsDto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ValidationErrorsDto handle(HttpMediaTypeNotSupportedException exception) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto();
        validationErrorsDto.addGlobalError(exception.getLocalizedMessage());

        return validationErrorsDto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ValidationErrorsDto handle(HttpMessageNotReadableException exception) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto();
        validationErrorsDto.addGlobalError("Formato de payload incorreto");

        return validationErrorsDto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsDto handle(MethodArgumentNotValidException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationError(globalErrors, fieldErrors);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ValidationErrorsDto handle(BindException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationError(globalErrors, fieldErrors);
    }

    private ValidationErrorsDto buildValidationError(List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        ValidationErrorsDto validationErrorsDto = new ValidationErrorsDto();
        globalErrors.forEach(error -> validationErrorsDto.addGlobalError(getErrorMessage(error)));

        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(error);
            validationErrorsDto.addFieldError(error.getField(), errorMessage);
        });

        return validationErrorsDto;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
