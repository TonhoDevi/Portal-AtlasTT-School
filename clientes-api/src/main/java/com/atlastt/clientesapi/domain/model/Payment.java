package com.atlastt.clientesapi.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class Payment {

    private final UUID id;
    private final UUID clientId;
    private final BigDecimal amount;
    private final String description;
    private final LocalDate issueDate;
    private final LocalDate dueDate;
    private LocalDate paidDate;
    private PaymentMethod paymentMethod;

    private Payment(UUID id, UUID clientId, BigDecimal amount, String description,
                    LocalDate issueDate, LocalDate dueDate, LocalDate paidDate,
                    PaymentMethod paymentMethod) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount;
        this.description = description;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.paidDate = paidDate;
        this.paymentMethod = paymentMethod;
    }

    public static Payment create(UUID clientId, BigDecimal amount, String description,
                                 LocalDate issueDate, LocalDate dueDate) {
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(issueDate, "issueDate must not be null");
        Objects.requireNonNull(dueDate, "dueDate must not be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (dueDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("Due date must not be before issue date");
        }
        return new Payment(UUID.randomUUID(), clientId, amount, description, issueDate, dueDate, null, null);
    }

    public static Payment restore(UUID id, UUID clientId, BigDecimal amount, String description,
                                  LocalDate issueDate, LocalDate dueDate, LocalDate paidDate,
                                  PaymentMethod paymentMethod) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(clientId, "clientId must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(issueDate, "issueDate must not be null");
        Objects.requireNonNull(dueDate, "dueDate must not be null");
        return new Payment(id, clientId, amount, description, issueDate, dueDate, paidDate, paymentMethod);
    }

    public void registerPayment(LocalDate paidDate, PaymentMethod paymentMethod) {
        Objects.requireNonNull(paidDate, "paidDate must not be null");
        Objects.requireNonNull(paymentMethod, "paymentMethod must not be null");
        if (paidDate.isBefore(issueDate)) {
            throw new IllegalArgumentException("Paid date must not be before issue date");
        }
        this.paidDate = paidDate;
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus statusAt(LocalDate referenceDate) {
        Objects.requireNonNull(referenceDate, "referenceDate must not be null");
        if (paidDate != null) {
            return PaymentStatus.PAID;
        }
        return referenceDate.isAfter(dueDate) ? PaymentStatus.OVERDUE : PaymentStatus.PENDING;
    }

    public PaymentStatus status() {
        return statusAt(LocalDate.now());
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Optional<LocalDate> getPaidDate() {
        return Optional.ofNullable(paidDate);
    }

    public Optional<PaymentMethod> getPaymentMethod() {
        return Optional.ofNullable(paymentMethod);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}