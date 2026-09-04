package com.atlastt.clientesapi.domain.model;

import com.atlastt.clientesapi.domain.exception.InvalidCpfException;

import java.util.Objects;

public final class Cpf {

    private final String value;

    private Cpf(String value) {
        this.value = value;
    }

    public static Cpf of(String rawValue) {
        String digits = clean(rawValue);
        if (!isValid(digits)) {
            throw new InvalidCpfException(rawValue);
        }
        return new Cpf(digits);
    }

    private static String clean(String rawValue) {
        if (rawValue == null) {
            return "";
        }
        return rawValue.replaceAll("[^0-9]", "");
    }

    private static boolean isValid(String cpf) {
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }
        int firstDigit = calculateDigit(cpf.substring(0, 9), 10);
        int secondDigit = calculateDigit(cpf.substring(0, 9) + firstDigit, 11);
        return cpf.equals(cpf.substring(0, 9) + firstDigit + secondDigit);
    }

    private static int calculateDigit(String base, int initialWeight) {
        int sum = 0;
        int weight = initialWeight;
        for (char c : base.toCharArray()) {
            sum += Character.getNumericValue(c) * weight--;
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    public String value() {
        return value;
    }

    public String formatted() {
        return value.substring(0, 3) + "." + value.substring(3, 6) + "."
                + value.substring(6, 9) + "-" + value.substring(9, 11);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return formatted();
    }
}