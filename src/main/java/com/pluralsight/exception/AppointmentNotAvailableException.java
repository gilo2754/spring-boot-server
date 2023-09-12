package com.pluralsight.exception;

public class AppointmentNotAvailableException extends RuntimeException {

    public AppointmentNotAvailableException(String exception) {
        super(exception);
    }
}
