package com.atlastt.clientesapi.domain.model;

import java.util.Objects;
import java.util.UUID;

public class Guardian {

    private final UUID id;
    private PersonalData personalData;

    private Guardian(UUID id, PersonalData personalData) {
        this.id = id;
        this.personalData = personalData;
    }

    public static Guardian create(PersonalData personalData) {
        Objects.requireNonNull(personalData, "Personal data must not be null");
        return new Guardian(UUID.randomUUID(), personalData);
    }

    public static Guardian restore(UUID id, PersonalData personalData) {
        Objects.requireNonNull(id, "Id must not be null");
        Objects.requireNonNull(personalData, "Personal data must not be null");
        return new Guardian(id, personalData);
    }

    public void updatePersonalData(PersonalData newPersonalData) {
        Objects.requireNonNull(newPersonalData, "Personal data must not be null");
        this.personalData = newPersonalData;
    }

    public UUID id() {
        return id;
    }

    public PersonalData personalData() {
        return personalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guardian other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}