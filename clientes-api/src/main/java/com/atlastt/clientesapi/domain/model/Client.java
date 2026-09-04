package com.atlastt.clientesapi.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
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
        return new Client(UUID.randomUUID(), personalData, birthDate, guardianLink, now, now);
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
        this.guardianLink = newGuardianLink;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePersonalData(PersonalData newPersonalData) {
        Objects.requireNonNull(newPersonalData, "personalData must not be null");
        this.personalData = newPersonalData;
        this.updatedAt = LocalDateTime.now();
    }

    public List<String> incompletenessReasons() {
        List<String> reasons = new ArrayList<>();

        if (isMinor() && guardianLink == null) {
            reasons.add("Guardian is required for minors");
        }

        return reasons;
    }

    public boolean isComplete() {
        return incompletenessReasons().isEmpty();
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