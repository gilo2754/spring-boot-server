package com.pluralsight.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String exception) {
        super(exception);
    }
}
