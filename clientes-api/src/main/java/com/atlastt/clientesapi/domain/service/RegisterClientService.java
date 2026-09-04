package com.atlastt.clientesapi.domain.service;

import com.atlastt.clientesapi.domain.model.Client;
import com.atlastt.clientesapi.domain.model.Cpf;
import com.atlastt.clientesapi.domain.model.Email;
import com.atlastt.clientesapi.domain.model.Guardian;
import com.atlastt.clientesapi.domain.model.GuardianLink;
import com.atlastt.clientesapi.domain.model.PersonalData;
import com.atlastt.clientesapi.domain.port.in.RegisterClientUseCase;
import com.atlastt.clientesapi.domain.port.out.ClientRepositoryPort;
import com.atlastt.clientesapi.domain.port.out.GuardianRepositoryPort;

import java.util.Objects;
import java.util.UUID;

public class RegisterClientService implements RegisterClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final GuardianRepositoryPort guardianRepositoryPort;

    public RegisterClientService(ClientRepositoryPort clientRepositoryPort,
                                 GuardianRepositoryPort guardianRepositoryPort) {
        this.clientRepositoryPort = Objects.requireNonNull(clientRepositoryPort);
        this.guardianRepositoryPort = Objects.requireNonNull(guardianRepositoryPort);
    }

    @Override
    public Client register(RegisterClientCommand command) {
        Objects.requireNonNull(command, "command must not be null");

        PersonalData clientPersonalData = PersonalData.of(
                command.fullName(),
                Cpf.of(command.cpf()),
                Email.of(command.email()),
                command.phone(),
                command.address()
        );

        GuardianLink guardianLink = null;
        if (command.guardianData() != null) {
            guardianLink = resolveGuardianLink(command.guardianData());
        }

        Client client = Client.create(clientPersonalData, command.birthDate(), guardianLink);

        return clientRepositoryPort.save(client);
    }

    private GuardianLink resolveGuardianLink(GuardianData guardianData) {
        Cpf guardianCpf = Cpf.of(guardianData.cpf());

        PersonalData guardianPersonalData = PersonalData.of(
                guardianData.fullName(),
                guardianCpf,
                Email.of(guardianData.email()),
                guardianData.phone(),
                guardianData.address()
        );

        UUID guardianId = guardianRepositoryPort.findByCpf(guardianCpf)
                .map(existingGuardian -> updateExistingGuardian(existingGuardian, guardianPersonalData))
                .orElseGet(() -> createNewGuardian(guardianPersonalData));

        return GuardianLink.of(guardianId, guardianData.relationship());
    }

    private UUID updateExistingGuardian(Guardian existingGuardian, PersonalData newPersonalData) {
        existingGuardian.updatePersonalData(newPersonalData);
        Guardian updated = guardianRepositoryPort.save(existingGuardian);
        return updated.id();
    }

    private UUID createNewGuardian(PersonalData personalData) {
        Guardian newGuardian = Guardian.create(personalData);
        Guardian saved = guardianRepositoryPort.save(newGuardian);
        return saved.id();
    }
}