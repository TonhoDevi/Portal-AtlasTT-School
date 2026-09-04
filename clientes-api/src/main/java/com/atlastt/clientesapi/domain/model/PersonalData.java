package com.atlastt.clientesapi.domain.model;

import java.util.Objects;

public final class PersonalData {

    private final String fullName;
    private final Cpf cpf;
    private final Email email;
    private final String phone;
    private final Address address;

    private PersonalData(String fullName, Cpf cpf, Email email, String phone, Address address) {
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public static PersonalData of(String fullName, Cpf cpf, Email email, String phone, Address address) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name must not be blank");
        }
        Objects.requireNonNull(cpf, "Cpf must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(address, "Address must not be null");
        return new PersonalData(fullName, cpf, email, phone, address);
    }

    public String fullName() {
        return fullName;
    }

    public Cpf cpf() {
        return cpf;
    }

    public Email email() {
        return email;
    }

    public String phone() {
        return phone;
    }

    public Address address() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalData other)) return false;
        return fullName.equals(other.fullName) && cpf.equals(other.cpf)
                && email.equals(other.email) && Objects.equals(phone, other.phone)
                && address.equals(other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, cpf, email, phone, address);
    }

    @Override
    public String toString() {
        return fullName + " (" + cpf + ")";
    }
}