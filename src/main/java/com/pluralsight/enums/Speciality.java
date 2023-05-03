package com.pluralsight.enums;

public enum Speciality {
    CARDIOLOGIA("Cardiología"),
    DERMATOLOGIA("Dermatología"),
    GASTROENTEROLOGIA("Gastroenterología"),
    NEUROLOGIA("Neurología"),
    OFTALMOLOGIA("Oftalmología"),
    ONCOLOGIA("Oncología"),
    PEDIATRIA("Pediatría"),
    PSIQUIATRIA("Psiquiatría"),
    TRAUMATOLOGIA("Traumatología"),
    UROLOGIA("Urología");

    private final String value;

    Speciality(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
