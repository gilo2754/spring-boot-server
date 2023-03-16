package com.pluralsight.exception;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String exception) {
        super(exception);
    }
}
