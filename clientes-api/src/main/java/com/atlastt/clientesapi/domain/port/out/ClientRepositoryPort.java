package com.atlastt.clientesapi.domain.port.out;

import com.atlastt.clientesapi.domain.model.Client;
import com.atlastt.clientesapi.domain.model.Cpf;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryPort {

    Client save(Client client);

    Optional<Client> findById(UUID id);

    Optional<Client> findByCpf(Cpf cpf);

    boolean existsByCpf(Cpf cpf);
}