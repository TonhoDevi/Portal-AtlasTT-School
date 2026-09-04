package com.atlastt.clientesapi.domain.exception;

import java.util.UUID;

public class GuardianRequiredException extends RuntimeException {
    public GuardianRequiredException(UUID clientId) {
        super("Client " + clientId + " is a minor and requires a guardian");
    }
}