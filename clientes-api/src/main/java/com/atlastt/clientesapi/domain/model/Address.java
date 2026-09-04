package com.atlastt.clientesapi.domain.model;

import java.util.Objects;

public final class Address {

    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;

    private Address(String street, String number, String complement,
                    String neighborhood, String city, String state, String zipCode) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public static Address of(String street, String number, String complement,
                             String neighborhood, String city, String state, String zipCode) {
        if (isBlank(street) || isBlank(number) || isBlank(neighborhood)
                || isBlank(city) || isBlank(state) || isBlank(zipCode)) {
            throw new IllegalArgumentException("Required address fields must not be blank");
        }
        return new Address(street, number, complement, neighborhood, city, state, zipCode);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public String street() {
        return street;
    }

    public String number() {
        return number;
    }

    public String complement() {
        return complement;
    }

    public String neighborhood() {
        return neighborhood;
    }

    public String city() {
        return city;
    }

    public String state() {
        return state;
    }

    public String zipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address other)) return false;
        return street.equals(other.street) && number.equals(other.number)
                && Objects.equals(complement, other.complement)
                && neighborhood.equals(other.neighborhood) && city.equals(other.city)
                && state.equals(other.state) && zipCode.equals(other.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, complement, neighborhood, city, state, zipCode);
    }

    @Override
    public String toString() {
        return street + ", " + number + (complement != null ? " - " + complement : "")
                + ", " + neighborhood + " - " + city + "/" + state + ", " + zipCode;
    }
}