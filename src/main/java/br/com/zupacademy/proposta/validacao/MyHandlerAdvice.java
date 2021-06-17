package br.com.zupacademy.proposta.validacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import feign.FeignException;

@RestControllerAdvice
public class MyHandlerAdvice {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErroPadronizado> handle(FeignException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(exception.getLocalizedMessage());
        
        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(exception.status()).body(erroPadronizado);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErroPadronizado> handle(ApiErrorException apiErrorException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(apiErrorException.getReason());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(apiErrorException.getHttpStatus()).body(erroPadronizado);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroPadronizado> handle(ResponseStatusException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(exception.getReason());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(exception.getStatus()).body(erroPadronizado);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErroPadronizado handle(ConstraintViolationException exception) {
        Collection<String> mensagens = new ArrayList<>();
        exception.getConstraintViolations().forEach(ex -> mensagens.add(ex.getMessage()));

        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErroPadronizado handle(IllegalStateException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(exception.getLocalizedMessage());

        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErroPadronizado handle(IllegalArgumentException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(exception.getLocalizedMessage());

        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErroPadronizado handle(HttpMediaTypeNotSupportedException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add("Content type not supported");

        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErroPadronizado handle(HttpMessageNotReadableException exception) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add("Required request body is incorrect");

        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroPadronizado handle(MethodArgumentNotValidException exception) {
        Collection<String> mensagens = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String mensagem = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            mensagens.add(mensagem);
        });
        return new ErroPadronizado(mensagens);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErroPadronizado handle(BindException exception) {
        Collection<String> mensagens = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String mensagem = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            mensagens.add(mensagem);
        });
        return new ErroPadronizado(mensagens);
    }

}
