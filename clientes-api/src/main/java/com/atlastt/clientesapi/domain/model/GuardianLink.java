package com.atlastt.clientesapi.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class GuardianLink {

    private final UUID guardianId;
    private final GuardianRelationship relationship;

    private GuardianLink(UUID guardianId, GuardianRelationship relationship) {
        this.guardianId = guardianId;
        this.relationship = relationship;
    }

    public static GuardianLink of(UUID guardianId, GuardianRelationship relationship) {
        Objects.requireNonNull(guardianId, "Guardian id must not be null");
        Objects.requireNonNull(relationship, "Relationship must not be null");
        return new GuardianLink(guardianId, relationship);
    }

    public UUID guardianId() {
        return guardianId;
    }

    public GuardianRelationship relationship() {
        return relationship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuardianLink other)) return false;
        return guardianId.equals(other.guardianId) && relationship == other.relationship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guardianId, relationship);
    }

    @Override
    public String toString() {
        return relationship + " (" + guardianId + ")";
    }
}