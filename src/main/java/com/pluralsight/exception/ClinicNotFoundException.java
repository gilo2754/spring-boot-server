package com.pluralsight.exception;

public class ClinicNotFoundException extends RuntimeException {

    public ClinicNotFoundException(String exception) {
        super(exception);
    }
}
