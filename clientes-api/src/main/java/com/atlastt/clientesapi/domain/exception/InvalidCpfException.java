package com.atlastt.clientesapi.domain.exception;

public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException(String receivedValue) {
        super("Invalid CPF: " + receivedValue);
    }
}