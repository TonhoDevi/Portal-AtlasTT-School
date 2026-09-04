package com.atlastt.clientesapi.domain.port.out;

import com.atlastt.clientesapi.domain.model.Cpf;
import com.atlastt.clientesapi.domain.model.Guardian;

import java.util.Optional;
import java.util.UUID;

public interface GuardianRepositoryPort {

    Guardian save(Guardian guardian);

    Optional<Guardian> findById(UUID id);

    Optional<Guardian> findByCpf(Cpf cpf);
}