package com.atlastt.clientesapi.domain.port.in;

import com.atlastt.clientesapi.domain.model.Address;
import com.atlastt.clientesapi.domain.model.Client;
import com.atlastt.clientesapi.domain.model.GuardianRelationship;

import java.time.LocalDate;

public interface RegisterClientUseCase {

    Client register(RegisterClientCommand command);

    record RegisterClientCommand(
            String fullName,
            String cpf,
            String email,
            String phone,
            Address address,
            LocalDate birthDate,
            GuardianData guardianData
    ) {
    }

    record GuardianData(
            String cpf,
            String fullName,
            String email,
            String phone,
            Address address,
            GuardianRelationship relationship
    ) {
    }
}