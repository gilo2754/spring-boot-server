package com.pluralsight.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(String exception) {
        super(exception);
    }
}
