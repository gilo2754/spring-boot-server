package com.pluralsight.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    DOCTOR_READ("doctor:read"),
    DOCTOR_UPDATE("doctor:update"),
    DOCTOR_CREATE("doctor:create"),
    DOCTOR_DELETE("doctor:delete"),
    PATIENT_READ("patient:read"),
    PATIENT_UPDATE("patient:update"),
    PATIENT_CREATE("patient:create"),
    PATIENT_DELETE("patient:delete");

    @Getter
    private final String permission;
}

