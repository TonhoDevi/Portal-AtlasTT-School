package com.atlastt.clientesapi.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Enrollment {

    private final UUID id;
    private final UUID clientId;
    private final UUID classGroupId;
    private final LocalDate enrolledAt;
    private LocalDate withdrawnAt;

    private Enrollment(UUID id, UUID clientId, UUID classGroupId,
                       LocalDate enrolledAt, LocalDate withdrawnAt) {
        this.id = id;
        this.clientId = clientId;
        this.classGroupId = classGroupId;
        this.enrolledAt = enrolledAt;
        this.withdrawnAt = withdrawnAt;
    }

    public static Enrollment create(UUID clientId, UUID classGroupId, LocalDate enrolledAt) {
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(classGroupId, "classGroupId must not be null");
        Objects.requireNonNull(enrolledAt, "enrolledAt must not be null");
        return new Enrollment(UUID.randomUUID(), clientId, classGroupId, enrolledAt, null);
    }

    public static Enrollment restore(UUID id, UUID clientId, UUID classGroupId,
                                     LocalDate enrolledAt, LocalDate withdrawnAt) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(classGroupId, "classGroupId must not be null");
        Objects.requireNonNull(enrolledAt, "enrolledAt must not be null");
        return new Enrollment(id, clientId, classGroupId, enrolledAt, withdrawnAt);
    }

    public void withdraw(LocalDate withdrawnAt) {
        Objects.requireNonNull(withdrawnAt, "withdrawnAt must not be null");
        if (withdrawnAt.isBefore(enrolledAt)) {
            throw new IllegalArgumentException("Withdrawal date must not be before enrollment date");
        }
        if (this.withdrawnAt != null) {
            throw new IllegalStateException("Enrollment is already withdrawn");
        }
        this.withdrawnAt = withdrawnAt;
    }

    public boolean isActive() {
        return withdrawnAt == null;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getClassGroupId() {
        return classGroupId;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    public Optional<LocalDate> getWithdrawnAt() {
        return Optional.ofNullable(withdrawnAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}