package com.atlastt.clientesapi.domain.model;

import com.atlastt.clientesapi.domain.exception.GuardianRequiredException;

import java.time.LocalDate;
import java.time.Period;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Client {

    private static final int LEGAL_AGE = 18;

    private final UUID id;
    private PersonalData personalData;
    private LocalDate birthDate;
    private GuardianLink guardianLink;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Client(UUID id,
                   PersonalData personalData,
                   LocalDate birthDate,
                   GuardianLink guardianLink,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.personalData = personalData;
        this.birthDate = birthDate;
        this.guardianLink = guardianLink;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Client create(PersonalData personalData, LocalDate birthDate, GuardianLink guardianLink) {
        Objects.requireNonNull(personalData, "personalData must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");

        LocalDateTime now = LocalDateTime.now();
        Client client = new Client(UUID.randomUUID(), personalData, birthDate, guardianLink, now, now);
        client.validateGuardianRequirement();
        return client;
    }

    public static Client restore(UUID id,
                                 PersonalData personalData,
                                 LocalDate birthDate,
                                 GuardianLink guardianLink,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(personalData, "personalData must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");

        return new Client(id, personalData, birthDate, guardianLink, createdAt, updatedAt);
    }

    public void updateGuardianLink(GuardianLink newGuardianLink) {
        GuardianLink previous = this.guardianLink;
        this.guardianLink = newGuardianLink;
        try {
            validateGuardianRequirement();
        } catch (GuardianRequiredException e) {
            this.guardianLink = previous;
            throw e;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePersonalData(PersonalData newPersonalData) {
        Objects.requireNonNull(newPersonalData, "personalData must not be null");
        this.personalData = newPersonalData;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateGuardianRequirement() {
        if (isMinor() && guardianLink == null) {
            throw new GuardianRequiredException(id);
        }
    }

    public boolean isMinor() {
        return age() < LEGAL_AGE;
    }

    public int ageAt(LocalDate referenceDate) {
        Objects.requireNonNull(referenceDate, "referenceDate must not be null");
        return Period.between(birthDate, referenceDate).getYears();
    }

    public int age() {
        return ageAt(LocalDate.now());
    }

    public UUID getId() {
        return id;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Optional<GuardianLink> getGuardianLink() {
        return Optional.ofNullable(guardianLink);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}