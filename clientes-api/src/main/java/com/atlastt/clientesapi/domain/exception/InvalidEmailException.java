package com.atlastt.clientesapi.domain.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String receivedValue) {
        super("Invalid email: " + receivedValue);
    }
}