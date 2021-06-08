package br.com.zupacademy.proposta.validacao;

public class FieldErrorDto {

    private String field;
    private String message;

    public FieldErrorDto() { }

    public FieldErrorDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}