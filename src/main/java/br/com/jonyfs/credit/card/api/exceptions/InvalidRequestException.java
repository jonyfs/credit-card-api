package br.com.jonyfs.credit.card.api.exceptions;

import org.springframework.validation.Errors;

public class InvalidRequestException extends RuntimeException {
    private static final long serialVersionUID = -589229703599785149L;
    private Errors            errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}