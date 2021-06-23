package com.bci.msuser.exception.customerrors;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
