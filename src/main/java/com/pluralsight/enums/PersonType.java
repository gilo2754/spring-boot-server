package com.pluralsight.enums;

public enum PersonType {
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    OTHER("Other");

    private String value;

    PersonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
