package com.atlastt.clientesapi.domain.model;

import com.atlastt.clientesapi.domain.exception.InvalidEmailException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {

    private static final Pattern PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String rawValue) {
        if (rawValue == null || !PATTERN.matcher(rawValue.trim()).matches()) {
            throw new InvalidEmailException(rawValue);
        }
        return new Email(rawValue.trim().toLowerCase());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}